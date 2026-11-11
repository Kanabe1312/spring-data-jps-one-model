package app.products.repository;

import app.products.model.Category;
import app.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findBySku(String sku);
    boolean existsBySku (String sku);
    List<Product> findByCategory(Category category);
    long countByCategory(Category category);
}
