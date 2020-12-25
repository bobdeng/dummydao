package cn.bobdeng.dummydao;

import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UUIDGeneratorImplTest {
    @Test
    public void test_next(){
        assertNotNull(new UUIDGeneratorImpl().next(null));
    }
    @Test
    public void has_value(){
        assertFalse(new UUIDGeneratorImpl().hasId(null));
        assertFalse(new UUIDGeneratorImpl().hasId(""));
        assertTrue(new UUIDGeneratorImpl().hasId(UUID.randomUUID().toString()));
    }
}
