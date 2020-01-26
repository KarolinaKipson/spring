package hr.kipson.karolina.ecommerce.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long orderId;


    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @Valid
    private List<Item> items;

    @Column(name = "total_order_price")
    private double totalOrderPrice;


    private String owner;

    @JsonIgnore
    private LocalDate date;

    public Order() {
    }

    public Order(List<Item> items, double totalOrderPrice, String owner) {
        this.items = items;
        this.totalOrderPrice = totalOrderPrice;
        this.owner = owner;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order(LocalDate date) {
       this.date = LocalDate.now();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }


    @Transactional
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<Item> items = getItems();
        for (Item item : items) {
            sum += item.getTotalPrice();
        }
        return sum;
    }


    public int getNumberOfItems() {
        return this.items.size();
    }
    public void setTotalOrderPrice(double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }


}
