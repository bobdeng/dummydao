package cn.bobdeng.dummydao;

import lombok.Data;

@Data
public class TestEntityWithId {
    private int id;

    public TestEntityWithId(int id) {
        this.id = id;
    }
}
