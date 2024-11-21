package CODEDBTA.GenerationsBank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


//Currently called "Stock", this will be later changed to users or some other applicable term for our bank project
@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private Long quantity;

    @Column
    private String item;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}