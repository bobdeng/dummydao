package cn.bobdeng.dummydao;

import lombok.Data;

@Data
public class TestEntityOther {
    private int id;
    private String name;

    public TestEntityOther(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
