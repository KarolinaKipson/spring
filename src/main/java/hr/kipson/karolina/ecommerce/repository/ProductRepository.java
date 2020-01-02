package hr.kipson.karolina.ecommerce.repository;

import hr.kipson.karolina.ecommerce.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert productInserter;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.productInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }


    public Iterable<Product> findAll() {
        return jdbc.query("select id, name, description, price, unitInStock, manufacturer from product", this::mapRowToProduct);
    }

    public Product findOne(Long id) {
        return jdbc.queryForObject("select id, name, description, price, unitInStock, manufacturer from product where id = ?", this::mapRowToProduct, id);
    }

    public int update(Product product) {
        return jdbc.update("update product set name =?, description = ?, price = ?, unitInStock = ?, manufacturer = ? where id = ?",product.getName(), product.getDescription(), product.getPrice(), product.getUnitInStock(), product.getManufacturer(), product.getId() );
    }

    public Product save(Product product) {
        product.setId(saveProductDetails(product));
        return product;
    }


    public void delete(Long id){

        jdbc.update("delete from product where id= ?", id);
    }


    private long saveProductDetails(Product product) {
        Map<String, Object> values = new HashMap<>();

        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("unitInStock", product.getUnitInStock());
        values.put("manufacturer", product.getManufacturer());


        return productInserter.executeAndReturnKey(values).longValue();
    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setDescription(rs.getString("description"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setUnitInStock(rs.getInt("unitInStock"));
        product.setManufacturer(rs.getString("manufacturer"));


        return product;
    }
}
