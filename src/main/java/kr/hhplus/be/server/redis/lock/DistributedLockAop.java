package kr.hhplus.be.server.redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * @DistributedLock 선언 시 수행되는 Aop class
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(kr.hhplus.be.server.redis.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        Object key = CustomSpringElParser.getDynamicValue(
                signature.getParameterNames(),
                joinPoint.getArgs(),
                distributedLock.key()
        );

        RLock rLock;
        if (key instanceof List<?>) {
            List<Integer> keyList = ((List<?>) key).stream()
                    .map(v -> Integer.parseInt(v.toString())) // 안전 변환
                    .toList();
            List<RLock> rLockList = keyList.stream().map(v -> {
                return redissonClient.getLock(REDISSON_LOCK_PREFIX + v);
            }).toList();
            rLock = redissonClient.getMultiLock(rLockList.toArray(new RLock[0]));  // (1)
        }
        else {
            String StringKey = REDISSON_LOCK_PREFIX + CustomSpringElParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
            rLock = redissonClient.getLock(StringKey);  // (1)
        }



        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());  // (2)
            if (!available) {
                return false;
            }

            return aopForTransaction.proceed(joinPoint);  // (3)
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            try {
                rLock.unlock();   // (4)
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock {} {}",
                        kv("serviceName", method.getName()),
                        kv("key", key)
                );
            }
        }
    }
}
