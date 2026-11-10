package app.view;

import app.products.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ViewProduct {

    private final ProductRepository productRepository;

    public ViewProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void start() {
    }
}
