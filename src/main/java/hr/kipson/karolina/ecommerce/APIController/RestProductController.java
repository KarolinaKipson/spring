package hr.kipson.karolina.ecommerce.APIController;

import hr.kipson.karolina.ecommerce.model.Product;
import hr.kipson.karolina.ecommerce.repository.ProductRepositoryApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/product",produces="application/json" )
public class RestProductController {
    private final ProductRepositoryApi productRepository;

    public RestProductController(ProductRepositoryApi productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id) {

        Optional<Product> product;

            product = productRepository.findByProductId(id);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public Product save(@Valid @RequestBody Product product) {

        product.setManufacturer(product.getManufacturer());
        product.setDescription(product.getDescription());
        product.setUnitInStock(product.getUnitInStock());
        product.setName(product.getName());
        product.setPrice(product.getPrice());

        return productRepository.save(product);
    }

    @PutMapping(value = "/{id}", consumes="application/json")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product updatedProduct){

        Optional<Product> product;

            product = productRepository.findByProductId(id);

        product.ifPresent(value -> {
            value.setManufacturer(updatedProduct.getManufacturer());
            value.setDescription(updatedProduct.getDescription());
            value.setPrice(updatedProduct.getPrice());
            value.setName(updatedProduct.getName());
            value.setUnitInStock(updatedProduct.getUnitInStock());

            productRepository.save(value);
        });

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        boolean exists = productRepository.existsByProductId(id);
        if(exists){
            productRepository.deleteById(id);
        }
    }

}
