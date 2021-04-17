package cn.bobdeng.dummydao;

import java.util.List;

public class AutoIntegerIdGenerator implements IdGenerator<Integer> {
    @Override
    public Integer next(List<Integer> existIds) {
        return existIds.stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }

    @Override
    public boolean hasId(Integer value) {
        return value != null && value != 0;
    }
}
