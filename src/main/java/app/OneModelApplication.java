package app;

import app.view.ViewProduct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OneModelApplication implements CommandLineRunner {

    private final ViewProduct viewProduct;

    public OneModelApplication(ViewProduct viewProduct) {
        this.viewProduct = viewProduct;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneModelApplication.class, args);
    }

    @Override
    public void run(String... args) {
        viewProduct.start();
    }
}
