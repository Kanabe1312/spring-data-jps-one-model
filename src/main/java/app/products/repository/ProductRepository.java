package app.products.repository;

import app.products.model.Category;
import app.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
