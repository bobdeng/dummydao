package cn.bobdeng.dummydao;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DummyDaoTest {
    @Test
    public void test_insert() {
        DummyDao<TestEntityWithId> dummyDao = new DummyDao<>(TestEntityWithId.class);
        dummyDao.insert(new TestEntityWithId(10));
        assertThat(dummyDao.all(), is(Collections.singletonList(new TestEntityWithId(10))));
    }

}
