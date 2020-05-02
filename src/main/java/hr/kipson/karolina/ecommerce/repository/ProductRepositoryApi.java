package hr.kipson.karolina.ecommerce.repository;

import hr.kipson.karolina.ecommerce.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryApi extends JpaRepository<Product,  Long> {

    Optional<Product> findByProductId(Long productId);

    List<Product> findAllByOrderByProductId();
    boolean existsByProductId(Long productId);

    void deleteByProductId(Long productId);
}
