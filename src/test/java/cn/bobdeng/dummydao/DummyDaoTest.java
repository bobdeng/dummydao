package cn.bobdeng.dummydao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DummyDaoTest {
    @Test
    public void should_insert_into_list_when_insert() {
        DummyDao<TestEntityWithId, Integer> dummyDao = new DummyDao<>(TestEntityWithId.class, "id", new AutoIntegerIdGenerator());

        dummyDao.insert(new TestEntityWithId(10));

        assertThat(dummyDao.all(), is(Collections.singletonList(new TestEntityWithId(10))));
    }

    @Test
    public void should_update_content_by_id() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        TestEntityOther entity1 = dummyDao.insert(new TestEntityOther(1, "hello1"));
        dummyDao.insert(new TestEntityOther(2, "hello2"));

        dummyDao.save(new TestEntityOther(2, "world"));

        assertThat(dummyDao.all(), is(Arrays.asList(entity1, new TestEntityOther(2, "world"))));
    }

    @Test(expected = MethodException.class)
    public void should_throw_when_no_getter() {
        DummyDao<TestEntityNoGetter, Integer> dummyDao = new DummyDao<>(TestEntityNoGetter.class, "id", new AutoIntegerIdGenerator());
        dummyDao.insert(new TestEntityNoGetter(1, "hello1"));
        dummyDao.save(new TestEntityNoGetter(1, "world"));
    }

    @Test
    public void should_remove_when_delete() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        dummyDao.insert(new TestEntityOther(1, "hello1"));
        dummyDao.delete(new TestEntityOther(1, "123"));
        assertThat(dummyDao.all().isEmpty(), is(true));
    }

    @Test
    public void should_remove_when_delete_by_id() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        dummyDao.insert(new TestEntityOther(1, "hello1"));
        dummyDao.deleteById(1);
        assertThat(dummyDao.all().isEmpty(), is(true));
    }

    @Test
    public void should_return_find_by_id() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        dummyDao.insert(new TestEntityOther(1, "hello1"));
        assertThat(dummyDao.findById(1), is(Optional.of(new TestEntityOther(1, "hello1"))));
    }

    @Test
    public void should_update_by_primary_key() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        dummyDao.insert(new TestEntityOther(1, "hello"));
        dummyDao.save(new TestEntityOther(1, "world"));
        assertThat(dummyDao.findById(1), is(Optional.of(new TestEntityOther(1, "world"))));
    }

    @Test
    public void should_generate_id_when_new() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        TestEntityOther saved = dummyDao.save(new TestEntityOther(0, "world"));
        assertThat(saved, is(new TestEntityOther(1, "world")));
    }

    @Test
    public void should_keep_id_when_hasId() {
        DummyDao<TestEntityOther, Integer> dummyDao = new DummyDao<>(TestEntityOther.class, "id", new AutoIntegerIdGenerator());
        TestEntityOther saved = dummyDao.save(new TestEntityOther(10, "world"));
        assertThat(saved, is(new TestEntityOther(10, "world")));
    }

    @Test
    public void save_entity_with_null_integer_id() throws Exception {
        DummyDao<TestEntityWithIntegerId, Integer> dummyDao = new DummyDao<>(TestEntityWithIntegerId.class, "id", new AutoIntegerIdGenerator());
        TestEntityWithIntegerId saved = dummyDao.save(new TestEntityWithIntegerId(null, "world"));
        assertThat(saved, is(new TestEntityWithIntegerId(1, "world")));
    }

}
