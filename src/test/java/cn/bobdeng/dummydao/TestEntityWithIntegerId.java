package cn.bobdeng.dummydao;

import lombok.Data;

@Data
public class TestEntityWithIntegerId {
    private Integer id;
    private String name;

    public TestEntityWithIntegerId(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
