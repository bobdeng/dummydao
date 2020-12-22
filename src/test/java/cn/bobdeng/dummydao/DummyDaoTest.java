package cn.bobdeng.dummydao;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DummyDaoTest {
    @Test
    public void should_insert_into_list_when_insert() {
        DummyDao<TestEntityWithId> dummyDao = new DummyDao<>(TestEntityWithId.class);

        dummyDao.insert(new TestEntityWithId(10));

        assertThat(dummyDao.all(), is(Collections.singletonList(new TestEntityWithId(10))));
    }

    @Test
    public void should_update_content_when_update() {
        DummyDao<TestEntityOther> dummyDao = new DummyDao<>(TestEntityOther.class);
        dummyDao.insert(new TestEntityOther(1, "hello"));

        dummyDao.updateById(new TestEntityOther(1, "world"), "id");

        assertThat(dummyDao.all(), is(Collections.singletonList(new TestEntityOther(1, "world"))));
    }

    @Test
    public void should_update_content_by_id() {
        DummyDao<TestEntityOther> dummyDao = new DummyDao<>(TestEntityOther.class);
        TestEntityOther entity1 = dummyDao.insert(new TestEntityOther(1, "hello1"));
        dummyDao.insert(new TestEntityOther(2, "hello2"));

        dummyDao.updateById(new TestEntityOther(2, "world"), "id");

        assertThat(dummyDao.all(), is(Arrays.asList(entity1, new TestEntityOther(2, "world"))));
    }

    @Test(expected = MethodException.class)
    public void should_throw_when_no_getter() {
        DummyDao<TestEntityNoGetter> dummyDao = new DummyDao<>(TestEntityNoGetter.class);
        dummyDao.insert(new TestEntityNoGetter(1, "hello1"));
        dummyDao.updateById(new TestEntityNoGetter(1, "world"), "id");
    }
    @Test
    public void should_remove_when_delete(){
        DummyDao<TestEntityOther> dummyDao = new DummyDao<>(TestEntityOther.class);
        dummyDao.insert(new TestEntityOther(1, "hello1"));
        dummyDao.deleteById("id",1);
        assertThat(dummyDao.all().isEmpty(),is(true));
    }


}
