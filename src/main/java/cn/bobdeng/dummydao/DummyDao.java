package cn.bobdeng.dummydao;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DummyDao<T> {
    private List<String> jsonList = new ArrayList<>();
    private Class<T> clz;

    public DummyDao(Class<T> clz) {

        this.clz = clz;
    }

    public List<T> all() {
        return this.jsonList.stream().map(json -> new Gson().fromJson(json, clz))
                .collect(Collectors.toList());
    }

    public T insert(T entity) {
        jsonList.add(new Gson().toJson(entity));
        return entity;
    }

    public Optional<T> findBy(String columnName, Object value) {
        return jsonList.stream()
                .map(json -> new Gson().fromJson(json, clz))
                .peek(t -> System.out.println(t.getClass()))
                .findFirst();
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
        try {
            Method method = clz.getMethod("get" + idName.substring(0, 1).toUpperCase() + idName.substring(1));
            return !Objects.equals(method.invoke(entity), method.invoke(newObject));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
}
