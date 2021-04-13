package webshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "webshop_order")
public class WebshopOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status = Status.DRAFT;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "total_price_hrk")
    private Double totalPriceHRK = 0.0;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "total_price_eur")
    private Double totalPriceEUR = 0.0;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList = new ArrayList<>();

    public WebshopOrder() {
    }

    public WebshopOrder(Long customerId, List<OrderItem> orderItemList) {
        this.customerId = customerId;
        this.orderItemList = orderItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getTotalPriceHRK() {
        return totalPriceHRK;
    }

    public void setTotalPriceHRK(Double totalPriceHRK) {
        this.totalPriceHRK = totalPriceHRK;
    }

    public Double getTotalPriceEUR() {
        return totalPriceEUR;
    }

    public void setTotalPriceEUR(Double totalPriceEUR) {
        this.totalPriceEUR = totalPriceEUR;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
