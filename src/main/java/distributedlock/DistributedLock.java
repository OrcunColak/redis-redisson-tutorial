package distributedlock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

class DistributedLock {

    private static final String LOCK_KEY = "lock_key";

    public static void main(String[] args) throws InterruptedException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RLock lock = redissonClient.getLock(LOCK_KEY);
        boolean lockAcquired = lock.tryLock(5, TimeUnit.SECONDS);
        if (lockAcquired) {
            try {
                System.out.println("Run business logic");
            } finally {
                lock.unlock();
            }
        }
    }
}
