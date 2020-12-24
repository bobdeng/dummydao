package cn.bobdeng.dummydao;

import java.util.List;

public interface IdGenerator<T> {
    T next(List<T> existIds);

    boolean hasId(T value);
}
