package app.products.repository;

import app.products.model.Category;
import app.products.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    //ex4
    Optional<Product> findBySku(String sku);
    boolean existsBySku (String sku);
    List<Product> findByCategory(Category category);
    long countByCategory(Category category);
    //ex5
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product> findByStockLessThan(int stock);
    List<Product> findByActiveTrue();
    List<Product> findByCategoryAndActive(Category category, boolean active);
    List<Product> findByCategoryIn(List<Category> categories);
    List<Product> findByAddedOnAfter(LocalDate date);
    //Ex6
    List<Product> findByCategoryOrderByPriceDesc(Category category);
    List<Product> findTop3ByOrderByPriceDesc();

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);

    Slice<Product> findByPriceGreaterThan(BigDecimal price, Pageable pagable);

    //EX7
    @Query("select p from Product p " + "where p.stock > 0 and p.price <= :maxPrice order by p.price")
    List<Product> findInStockUpTo(@Param("maxPrice") BigDecimal maxPrice);

    @Query("select sum(p.price * p.stock) from Product p")
    BigDecimal stockValue();

    @Query(
            value = "SELECT * FROM products " +
                    "WHERE sku LIKE CONCAT(:prefix, '-%') " +
                    "ORDER BY sku",
            nativeQuery = true
    )
    List<Product> findBySkuPrefix(@Param("prefix") String prefix);

    //EX8
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Product p SET p.active = false WHERE p.stock = 0")
    int deactivateOutOfStock();
}
