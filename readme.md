使用一个内存的List<String>来保存对象的Json字符串，从而实现一个内存的dummy DAO对象，可以用来作为单元测试的测试替身。
# 引入
```
<repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
</repositories>
  
  <dependency>
            <groupId>com.github.bobdeng</groupId>
            <artifactId>dummydao</artifactId>
            <version>1.0</version>
 </dependency>
```
# 示例
  初始化一个DAO，用DummyDAO构造一个测试替身DeviceRepositoryImpl
```
  DummyDao<Device, Integer> dummyDao = new DummyDao<>(Device.class, "id", new AutoIntegerIdGenerator());
  deviceService = new DeviceServiceImpl(new DeviceRepositoryImpl(dummyDao));
```
```
DeviceRepositoryImpl.java
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DummyDao<Device, Integer> dummyDao;

    public DeviceRepositoryImpl(DummyDao<Device, Integer> dummyDao) {
        this.dummyDao = dummyDao;
    }

    @Override
    public Device save(Device entity) {
        return dummyDao.save(entity);
    }

    @Override
    public Optional<Device> findByMac(String mac) {
        return dummyDao.all().stream()
                .filter(device -> device.isSame(mac))
                .findFirst();
    }
}  
``` 
