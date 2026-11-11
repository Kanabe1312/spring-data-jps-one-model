package app.view;

import app.products.model.Category;
import app.products.model.Product;
import app.products.repository.ProductRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ViewProduct {

    private final ProductRepository productRepository;

    public ViewProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public void start() {

//      Product p=  new Product("Espresso22", "COF-0004", Category.COFFEE, new BigDecimal("9.50"), 120, true, LocalDate.of(2026, 1, 10));
//
//
//      productRepository.save(p);

        productRepository.findAll().stream().forEach(System.out::println);
        populeaza();
        citeste();
    }


    public void populeaza(){

        productRepository.deleteAll();

        productRepository.saveAll(List.of(
                        new Product("Espresso", "COF-0001", Category.COFFEE, new BigDecimal("9.50"), 120, true, LocalDate.of(2026, 1, 10)),
                        new Product("Cappuccino", "COF-0002", Category.COFFEE, new BigDecimal("13.00"), 80, true, LocalDate.of(2026, 1, 10)),
                        new Product("Flat White", "COF-0003", Category.COFFEE, new BigDecimal("14.50"), 0, true, LocalDate.of(2026, 3, 2)),
                        new Product("Green Tea", "TEA-0001", Category.TEA, new BigDecimal("11.00"), 40, true, LocalDate.of(2026, 2, 15)),
                        new Product("Black Tea", "TEA-0002", Category.TEA, new BigDecimal("10.00"), 4, true, LocalDate.of(2026, 2, 15)),
                        new Product("Croissant", "PAS-0001", Category.PASTRY, new BigDecimal("8.00"), 25, true, LocalDate.of(2026, 4, 1)),
                        new Product("Cheesecake", "PAS-0002", Category.PASTRY, new BigDecimal("18.50"), 3, true, LocalDate.of(2026, 5, 20)),
                        new Product("Brownie", "PAS-0003", Category.PASTRY, new BigDecimal("12.00"), 0, true, LocalDate.of(2026, 5, 20)),
                        new Product("French Press", "EQP-0001", Category.EQUIPMENT, new BigDecimal("129.99"), 7, false, LocalDate.of(2026, 6, 11)),
                        new Product("Coffee Grinder", "EQP-0002", Category.EQUIPMENT, new BigDecimal("249.00"), 2, true, LocalDate.of(2026, 9, 30))
        ));

        System.out.println("Produse: " + productRepository.count());
    }


    private void citeste(){
        List<Product> produse = productRepository.findAll();
        produse.forEach(System.out::println);

        UUID primulId = produse.get(0).getId();
        Optional<Product>gasit = productRepository.findById(primulId);

        gasit.ifPresent(product -> System.out.println("Gasit: " + product.getName()));

       UUID idInexistent = UUID.fromString( "00000000-0000-0000-0000-000000000000");

       Optional<Product> inexistent = productRepository.findById(idInexistent);
        System.out.println("Exista: " + productRepository.existsById(idInexistent));

        if (inexistent.isEmpty()){
            System.out.println("produs inexistent");
        }
    }
}
