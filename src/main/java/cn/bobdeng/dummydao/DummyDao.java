package cn.bobdeng.dummydao;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class DummyDao<T, PK> {
    private List<String> jsonList = new ArrayList<>();
    private final Class<T> clz;
    private final String primaryKey;
    private final IdGenerator<PK> idGenerator;

    public DummyDao(Class<T> clz, String primaryKey, IdGenerator<PK> idGenerator) {
        this.clz = clz;
        this.primaryKey = primaryKey;
        this.idGenerator = idGenerator;
    }

    public List<T> all() {
        return this.jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .collect(Collectors.toList());
    }

    public T insert(T entity) {
        jsonList.add(new Gson().toJson(entity));
        return entity;
    }

    private void updateById(T newObject, String idName) {
        this.jsonList = jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .map(entity -> updateWhenSame(entity, newObject, idName))
                .map(entity -> new Gson().toJson(entity))
                .collect(Collectors.toList());
    }

    private Object updateWhenSame(T entity, T newObject, String idName) {
        if (notSame(entity, idName, newObject)) {
            return entity;
        }
        return newObject;
    }

    private boolean notSame(T entity, String idName, T newObject) {
        return !Objects.equals(getField(idName, entity), getField(idName, newObject));
    }

    private PK getField(String field, T object) {
        try {
            Method method = clz.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
            return (PK) method.invoke(object);
        } catch (Exception e) {
            throw new MethodException(e);
        }
    }


    public Optional<T> findById(String idField, Object value) {
        return jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .filter(entity -> Objects.equals(value, getField(idField, entity)))
                .findFirst();
    }

    public Optional<T> findById(Object value) {
        return findById(primaryKey, value);
    }


    public T save(T newObject) {
        boolean exist = findById(primaryKey, getField(primaryKey, newObject)).isPresent();
        if (exist) {
            updateById(newObject, primaryKey);
            return null;
        }
        T insertObject = cloneObject(newObject);
        generateIdIfNotExist(insertObject);
        return insert(insertObject);
    }

    private void generateIdIfNotExist(T insertObject) {
        if (!idGenerator.hasId(getField(primaryKey, insertObject))) {
            Object newKey = idGenerator.next(all().stream().map(t -> getField(primaryKey, t)).collect(Collectors.toList()));
            setField(primaryKey, insertObject, newKey);
        }
    }

    private T cloneObject(T newObject) {
        return new Gson().fromJson(new Gson().toJson(newObject), clz);
    }

    private void setField(String field, Object object, Object newValue) {
        try {
            String methodName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
            Field primaryKeyField = object.getClass().getDeclaredField(field);
            Method method = clz.getMethod(methodName, primaryKeyField.getType());
            method.invoke(object, newValue);
        } catch (Exception e) {
            throw new MethodException(e);
        }
    }

    public void delete(T object) {
        this.deleteById(getField(primaryKey, object));
    }

    public void deleteById(PK id) {
        this.jsonList = jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .filter(entity -> !Objects.equals(id, getField(primaryKey, entity)))
                .map(entity -> new Gson().toJson(entity))
                .collect(Collectors.toList());
    }
}
