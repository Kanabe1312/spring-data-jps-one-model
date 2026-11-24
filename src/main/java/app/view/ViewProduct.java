package app.view;

import app.products.model.Category;
import app.products.model.Product;
import app.products.repository.ProductRepository;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
        sortare();
        //valideaza();
        //metodeDerivate();
        //metodeDerivateSingur();
        //paginare();
       //paginare6B1();
        //paginare6B2();
      // paginare6B3();
      // paginare6B4();
        // paginare6B5();
       // paginare6B6();
       // queryInStock();
        //queryStockValue();
        //querySkuPrefix();7
        //modificareSave();
        //modificareDeactivate();
        paginare6B7();
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

    private void valideaza(){
        //gresit
        Product gresit = new Product(" ","abc",Category.COFFEE,new BigDecimal("0.00"),-1,true,LocalDate.of(2026,1,10));
        try {
            productRepository.saveAndFlush(gresit);
        }catch (ConstraintViolationException e){
            e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage()).sorted()
                    .forEach(System.out::println);

        }

        //Corect+SKU Duplicat
        Product duplicat = new Product("Espresso Duplicat","COF-0001",Category.COFFEE,new BigDecimal("15.00"),50,true,LocalDate.of(2026,1,10));
        try {
            productRepository.save(duplicat);
        }catch (DataIntegrityViolationException e){
            System.out.println("SKU already used: COF-0001 ");
        }
        System.out.println("Produse: " + productRepository.count());

    }

    private void metodeDerivate() {

        productRepository.findBySku("TEA-0001").ifPresent(product ->
                        System.out.println(product.getName()));

        System.out.println(productRepository.findBySku("XXX-0000").isPresent());


        System.out.println(productRepository.existsBySku("COF-0002"));

        productRepository.findByCategory(Category.PASTRY).forEach(product -> System.out.println(product.getName()));

        System.out.println(productRepository.countByCategory(Category.COFFEE));
    }


    private void metodeDerivateSingur() {

        System.out.println("\n--- Name contains TEA ---");
        productRepository.findByNameContainingIgnoreCase("TEA").forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Price between 10 and 13 ---");
        productRepository.findByPriceBetween(new BigDecimal("10.00"), new BigDecimal("13.00"))
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Stock less than 5 ---");
        productRepository.findByStockLessThan(5).forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Active products ---");
        System.out.println(productRepository.findByActiveTrue().size());

        System.out.println("\n--- EQUIPMENT and active ---");
        productRepository.findByCategoryAndActive(Category.EQUIPMENT, true).forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- COFFEE or TEA ---");
        productRepository.findByCategoryIn(List.of(Category.COFFEE, Category.TEA))
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Added after 2026-05-01 ---");
        productRepository.findByAddedOnAfter(LocalDate.of(2026, 5, 1))
                .forEach(p -> System.out.println(p.getName()));
    }


    private void sortare(){
        System.out.println("\n--- Sorted after name ---");
        productRepository.findAll(Sort.by("name")).forEach(System.out::println);

        System.out.println("\n --- Sorted after Stock desc ---");
        productRepository.findAll(Sort.by(Sort.Direction.DESC,"stock")).forEach(
                product -> System.out.println(product.getName() + "-Stock: " + product.getStock())
        );

        System.out.println("\n--- Coffe sorted by price DESC ---");

        productRepository.findByCategoryOrderByPriceDesc(Category.COFFEE).forEach(product -> System.out.println(product.getName()
        + " - " + product.getPrice()));

    }

    private void paginare(){
        Page<Product> pagina=productRepository.findAll(PageRequest.of(1,4,Sort.by("name")));

        pagina.get().forEach(System.out::println);
        System.out.println(pagina.getTotalElements()+" "+ pagina.getTotalPages()+" pagini");


        System.out.println("\n--- Top 3 products by price ---");
        productRepository.findTop3ByOrderByPriceDesc().forEach(System.out::println);

        System.out.println("\n--- All products by name ---");
        productRepository.findAll(Sort.by("name")).forEach(System.out::println);


        pagina.getContent().forEach(System.out::println);
        System.out.println(pagina.getTotalElements() + " produse"+ pagina.getTotalPages() + " pagini");

        System.out.println("\n--- Active products: page 1 ---");
        Page<Product> active = productRepository.findByActiveTrue(PageRequest.of(0, 3, Sort.by("price")));

        active.getContent().forEach(System.out::println);


    }

    private void paginare6B1(){
        System.out.println("\n --- Page 2, size 4  ---");
        Page<Product> pagina = productRepository.findAll(PageRequest.of(1,4,Sort.by("name")));

        pagina.getContent().forEach(product -> System.out.println(product.getName()));

        System.out.println("Pagina: " + pagina.getNumber());
        System.out.println("Elemente: "+ pagina.getNumberOfElements());
        System.out.println("Prima: " + pagina.isFirst());
        System.out.println("Ultima: " + pagina.isLast());
        System.out.println("Urmatoarea: " + pagina.hasNext());
    }

    private void paginare6B2(){
        System.out.println("\n--- Page 5 by name ---");
        Page<Product> pagina5 = productRepository.findAll(PageRequest.of(4,4,Sort.by("name")));
        pagina5.getContent().forEach(product -> System.out.println(product.getName()));

        System.out.println("Are continut: " + pagina5.hasContent());
        System.out.println("Total produse: " + pagina5.getTotalElements());
        System.out.println("Total pagini: " + pagina5.getTotalPages());
    }

    private void paginare6B3(){
        System.out.println("\n--- Coffe: page 1 ---");
        Page<Product> cafea1 = productRepository.findByCategory(Category.COFFEE , PageRequest.of(
                0,2,Sort.by(Sort.Direction.DESC,"price")));


        cafea1.getContent().forEach(product -> System.out.println(product.getName()));

        System.out.println(cafea1.getTotalElements() + "produse, "+ cafea1.getTotalPages() + "pagini");

        System.out.println("\n--- Coffe: page 2 ---");
        Page<Product> cafea2 = productRepository.findByCategory(Category.COFFEE , cafea1.nextPageable());

        cafea2.getContent().forEach(product -> System.out.println(product.getName()));
        System.out.println(cafea2.getTotalElements() + "produse, "+ cafea2.getTotalPages() + "pagini");
    }

    private void paginare6B4(){
        System.out.println("\n--- All active products ---");
        Page<Product> pagina = productRepository.findByActiveTrue(PageRequest.of(0,3,Sort.by("price")));

        System.out.println("Produse: " +pagina.getTotalElements());
        System.out.println("Total paginin: "+  pagina.getTotalPages());

        while (true){
            System.out.println("\nPagina" + (pagina.getNumber()+1) + ": ");
            pagina.getContent().forEach(product -> System.out.println(product.getName()));

            if (!pagina.hasNext()){
                break;
            }

            pagina = productRepository.findByActiveTrue(pagina.nextPageable());
        }


    }

    private void paginare6B5(){
        System.out.println("\n--- Products sorted by stock and name ---");

        Page<Product> pagina = productRepository.findAll(PageRequest.of(0,5,Sort.by("stock")
                .and(Sort.by("name"))));
        pagina.getContent().forEach(product -> System.out.println(product.getName() + "- Stock: " + product.getStock()));
    }

    private void paginare6B6() {
        System.out.println("\n--- Products with price > 12.00 ---");

        Slice<Product> felie = productRepository.findByPriceGreaterThan(
                new BigDecimal("12.00"),
                PageRequest.of(0, 2)
        );

        System.out.println("Prima felie: ");
        felie.getContent().forEach(product ->
                System.out.println(product.getName())
        );

        System.out.println("Are urmatoarea felie: " + felie.hasNext());

        if (felie.hasNext()) {
            Slice<Product> urmatoareaFelie = productRepository.findByPriceGreaterThan(new BigDecimal("12.00"),
                    felie.nextPageable());

            System.out.println("A doua felie: ");
            urmatoareaFelie.getContent().forEach(product -> System.out.println(product.getName()));
        }
    }

    private void paginare6B7(){
        System.out.println("\n--- Page.map() ---");

        Page<Product> pagina = productRepository.findAll(PageRequest.of(0,4,Sort.by(Sort.Direction.DESC,"price")));

        Page<String> texte = pagina.map(product -> product.getName() + " - " + product.getPrice());

        texte.getContent().forEach(System.out::println);

        System.out.println("total pagini: " + texte.getTotalPages());

    }

    //EX7

    private void queryInStock(){
        System.out.println("\n--- In stock up to 12.00 ---");

        List<Product> produse = productRepository.findInStockUpTo(new BigDecimal("12.00"));

        produse.forEach(p -> System.out.println(p.getName()));
    }

    private void queryStockValue() {
        System.out.println("\n--- Total stock value ---");

        BigDecimal valoare = productRepository.stockValue();

        System.out.println("Stock value: " + valoare);
    }

    private void querySkuPrefix() {
        System.out.println("\n--- Products by SKU prefix ---");

        List<Product> produse = productRepository.findBySkuPrefix("PAS");

        produse.forEach(product -> System.out.println(product.getName()));
    }

    //EX8
    private void modificareSave() {
        System.out.println("\n--- Modify Espresso ---");

        Product espresso = productRepository.findBySku("COF-0001").orElseThrow();
        espresso.setPrice(new BigDecimal("10.00"));

        productRepository.save(espresso);
        Product espressoModificat = productRepository.findBySku("COF-0001").orElseThrow();

        System.out.println(espressoModificat.getPrice());
        System.out.println("Produse: " + productRepository.count());
    }

    private void modificareDeactivate() {
        System.out.println("\n--- Deactivate out of stock ---");

        int rezultat = productRepository.deactivateOutOfStock();

        System.out.println("Dezactivate: " + rezultat);

        System.out.println("Produse active:");
        productRepository.findByActiveTrue().forEach(product ->System.out.println(product.getName()));
    }





}
