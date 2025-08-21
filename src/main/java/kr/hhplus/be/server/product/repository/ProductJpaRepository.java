package kr.hhplus.be.server.product.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    public Optional<Product> findByIdWithLock(long id);

    @Query(nativeQuery = true, value= """
            SELECT p.*
                    FROM product p
                    JOIN (
                          SELECT p.id
                          FROM product AS p
                          JOIN order_product AS op ON p.id = op.product_id
                          WHERE op.create_at > DATE_SUB(NOW(), INTERVAL 3 DAY)
                          GROUP BY p.id
                          ORDER BY SUM(op.count) DESC
                          LIMIT 5
                    ) AS best ON p.id = best.id
            """)
    List<Product> getBestProductList();

    List<Product> findAllByIdIn(Set<Long> ids);
}
