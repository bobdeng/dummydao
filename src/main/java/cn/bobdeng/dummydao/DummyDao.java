package cn.bobdeng.dummydao;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DummyDao<T> {
    private List<String> jsonList = new ArrayList<>();
    private Class<T> clz;

    public DummyDao(Class<T> clz) {
        this.clz = clz;
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

    public void updateById(T newObject, String idName) {
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
        return !Objects.equals(getField(idName,entity), getField(idName,newObject));
    }

    private Object getField(String field, T object) {
        try {
            Method method = clz.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
            return method.invoke(object);
        } catch (Exception e) {
            throw new MethodException(e);
        }
    }

    public void deleteById(String idName, Object newObject) {
        this.jsonList = jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .filter(entity -> !Objects.equals(newObject,getField(idName,entity)))
                .map(entity -> new Gson().toJson(entity))
                .collect(Collectors.toList());
    }
}
