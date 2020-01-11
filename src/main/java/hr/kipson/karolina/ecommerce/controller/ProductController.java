package hr.kipson.karolina.ecommerce.controller;

import hr.kipson.karolina.ecommerce.model.Product;
import hr.kipson.karolina.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/emusicstore")
public class ProductController {

    private Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String home(){
        return "index";
    }

    @GetMapping("/add")
    @Secured("ROLE_ADMIN")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "newProduct";
    }

    @PostMapping("/add")
    @Secured("ROLE_ADMIN")
    public String createNewProduct(@Valid Product product, Errors errors, Model model){
        if(errors.hasErrors()){
            log.info("There were errors in the submitted form");
            return "newProduct";
        }
        productRepository.save(product);

        return "redirect:/emusicstore/productList";
    }


    @GetMapping("/productList")
    public String getProducts(Model model){
        Iterable<Product> iterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        model.addAttribute("products", products);

        return "productList";
    }

    @GetMapping("/productdetail/{productId}")
    public String productDetails(@PathVariable Long productId, Model model) throws IOException {
        Product product = productRepository.findOne(productId);
        model.addAttribute("product", product);
        return "productDetail";
    }

    @GetMapping("/productedit/{productId}")
    @Secured("ROLE_ADMIN")
    public String productEdit(@PathVariable Long productId, Model model) throws IOException {
        Product product = productRepository.findOne(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }


    @PostMapping(value ="/")
    @Secured("ROLE_ADMIN")
    public String productEditSave(@Valid Product product, Errors errors, Model model){
        if(errors.hasErrors()){
            log.info("There were errors in the submitted form");
            return "editProduct" ;
        }
        productRepository.update(product);

        return "redirect:/emusicstore/productList";
    }

    @GetMapping("/deleteproduct/{productId}")
    @Secured("ROLE_ADMIN")
    public String handleDeleteProduct(@PathVariable long productId, Model model) {
        productRepository.delete(productId);
        Iterable<Product> iterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        model.addAttribute("products", products);

        return "redirect:/emusicstore/productList";

    }


}
