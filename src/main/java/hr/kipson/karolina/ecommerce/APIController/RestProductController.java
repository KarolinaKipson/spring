package hr.kipson.karolina.ecommerce.APIController;

import hr.kipson.karolina.ecommerce.model.Product;
import hr.kipson.karolina.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/products")
public class RestProductController {
    private final ProductRepository productRepository;

    public RestProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull Iterable<Product> getProducts() {
        return productRepository.findAll();
    }
}
