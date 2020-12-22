package cn.bobdeng.dummydao;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
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
}
