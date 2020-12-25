package cn.bobdeng.dummydao;

import java.util.List;
import java.util.UUID;

public class UUIDGeneratorImpl implements IdGenerator<String> {
    @Override
    public String next(List<String> existIds) {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean hasId(String value) {
        return value != null && !"".equals(value);
    }
}
