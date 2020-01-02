package hr.kipson.karolina.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private long id;
    @Size(min = 2, max = 20, message = "The exercise name should be between 2 and 20 characters long")
    private String name;
    private String description;
    private BigDecimal price;
    private int unitInStock;
    private String manufacturer;



    public Product() {

    }

    public Product( String name, String description, BigDecimal price,  int unitInStock, String manufacturer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.unitInStock = unitInStock;
        this.manufacturer = manufacturer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
