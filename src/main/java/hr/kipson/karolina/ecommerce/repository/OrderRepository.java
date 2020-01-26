package hr.kipson.karolina.ecommerce.repository;

import hr.kipson.karolina.ecommerce.model.Item;
import hr.kipson.karolina.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderId(Long orderId);
    List<Order> findAllByOrderIdAndDate(Long orderId, Date date);
    List<Order> findAllByOwnerAndAndOrderId(String owner, Long orderId);
    List<Order> findAllByOwner(String owner);

    Optional<Order> findByOrderIdAndOwner(Long orderId, String owner);
    boolean existsByOrderIdAndOwner(Long orderId, String owner);
    boolean existsByOrderId(Long orderId);
    Optional<Order> findByOrderIdAndDate(Long orderId, Date date);
    boolean existsByOrderIdAndDate(Long orderId, Date date);


}
