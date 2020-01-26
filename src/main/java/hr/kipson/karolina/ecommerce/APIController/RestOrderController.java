package hr.kipson.karolina.ecommerce.APIController;

import hr.kipson.karolina.ecommerce.model.Item;
import hr.kipson.karolina.ecommerce.model.Order;
import hr.kipson.karolina.ecommerce.repository.ItemRepository;
import hr.kipson.karolina.ecommerce.repository.OrderRepository;
import hr.kipson.karolina.ecommerce.repository.ProductRepository;
import hr.kipson.karolina.ecommerce.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/order", produces="application/json")
@Secured("ROLE_USER")
public class RestOrderController {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    public RestOrderController(OrderRepository orderRepository, ProductRepository productRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public Iterable<Order> findAll(){
       return SecurityUtils.isAdmin() ? orderRepository.findAll() : orderRepository.findAllByOwner(SecurityUtils.getUsername());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(params = "owner")
    public Iterable<Order> findAll(String owner){
        return orderRepository.findAllByOwner(owner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOne(@PathVariable Long id) {
        boolean isAdmin = SecurityUtils.isAdmin();
        Optional<Order> order;

        if(isAdmin) {
            order = orderRepository.findById(id);
        } else {
            var username = SecurityUtils.getUsername();
            order = orderRepository.findByOrderIdAndOwner(id, username);
        }
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public Order save(@Valid @RequestBody Order order) {

        order.setOwner(SecurityUtils.getUsername());
        order.setDate(LocalDate.now());
        order.setItems(order.getItems());

       // order.setTotalOrderPrice(order.getTotalOrderPrice());
        return orderRepository.save(order);
    }


    @PutMapping(value = "/{id}", consumes="application/json")
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order updatedOrder){
        boolean isAdmin = SecurityUtils.isAdmin();
        Optional<Order> order;
        if(isAdmin){
           order = orderRepository.findById(id);
        } else{
            String username = SecurityUtils.getUsername();
           order = orderRepository.findByOrderIdAndOwner(id, username);
        }

        order.ifPresent(value -> {
            value.setItems(updatedOrder.getItems());
            value.setTotalOrderPrice(updatedOrder.getTotalOrderPrice());


            orderRepository.save(value);
        });

        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        boolean exists = SecurityUtils.isAdmin() ? orderRepository.existsByOrderId(id) : orderRepository.existsByOrderIdAndOwner(id, SecurityUtils.getUsername());
        if(exists){
            orderRepository.deleteById(id);
        }
    }
}
