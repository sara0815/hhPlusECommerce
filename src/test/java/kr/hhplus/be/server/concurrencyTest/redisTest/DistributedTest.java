package kr.hhplus.be.server.concurrencyTest.redisTest;

import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class DistributedTest {

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    OrderJpaRepository orderJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    OrderProductJpaRepository orderProductJpaRepository;

    private final int numberOfThread = 100;
    private final long stock = 100;

    @BeforeEach
    void setUp() {
        productJpaRepository.deleteAll();
        Product product = new Product("테스트상품", 1000, stock);
        Product product2 = new Product("테스트상품1", 1000, stock);
        productJpaRepository.save(product);
        productJpaRepository.save(product2);
        for (int i = 1; i <= numberOfThread; i++) {
            User user = new User(10000);
            userJpaRepository.save(user);
        }
    }

    @AfterEach
    void cleanUp() {
        orderJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        productJpaRepository.deleteAll();


    }

    @Test
    @Commit
    public void 주문_재고_차감_동시성_테스트() {
        List<User> userList = userJpaRepository.findAll();
        List<Product> productList = productJpaRepository.findAll();
        List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderProduct> orderProductList2 = new ArrayList<>();
        long productId = productList.get(0).getId();
        long productId2 = productList.get(1).getId();
        OrderProduct orderProduct = new OrderProduct(productId, 1);
        OrderProduct orderProduct2 = new OrderProduct(productId2, 1);
        orderProductList.add(orderProduct);
        orderProductList2.add(orderProduct);
        orderProductList2.add(orderProduct2);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch latch = new CountDownLatch(numberOfThread);

        for (int i = 0; i < numberOfThread; i ++) {
            final int idx = i;
            executorService.submit(() -> {
                try {
                    if (idx % 2 == 1) {
                        orderFacade.order(userList.get(idx).getId(), orderProductList, null);
                    }
                    else {
                        orderFacade.order(userList.get(idx).getId(), orderProductList2, null);
                    }
                }
                finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Order> orderListResult = orderJpaRepository.findAll();
        assertThat(orderListResult.size()).isEqualTo(100);

        Product productResult = productJpaRepository.findById(productId).orElseThrow();
        assertThat(productResult.getStock()).isEqualTo(stock - numberOfThread);
    }
}
