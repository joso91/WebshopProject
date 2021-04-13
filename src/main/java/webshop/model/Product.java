package webshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    @Size(min = 10, max = 10)
    @Column(unique = true)
    String code;
    String name;
    @Min(value = 0)
    @Column(name = "price_hrk")
    Double priceHRK;
    String description;
    @Column(name = "is_available")
    Boolean available;

    public Product() {
    }

    public Product(String code, String name, Double priceHRK, String description, Boolean available) {
        this.code = code;
        this.name = name;
        this.priceHRK = priceHRK;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceHRK() {
        return priceHRK;
    }

    public void setPriceHRK(Double priceHRK) {
        this.priceHRK = priceHRK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
