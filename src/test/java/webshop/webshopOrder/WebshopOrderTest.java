package webshop.webshopOrder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.model.*;
import webshop.service.CustomerService;
import webshop.service.ProductService;
import webshop.service.WebshopOrderService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebshopOrderTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    WebshopOrderService webshopOrderService;

    @Test
    public void createWebshopOrder() {
        Customer customer = customerService.createUpdate(new Customer("John11", "Doe11", "john11.doe11@gmail.com"));
        Product product = productService.createUpdate(new Product("abcde_0011", "article-11", 10.11, "description11", true));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(product.getId(), 1));
        WebshopOrder webshopOrder = new WebshopOrder(customer.getId(), orderItemList);
        WebshopOrder savedWebshopOrder = webshopOrderService.createUpdate(webshopOrder);
        Customer updatedCustomer = customerService.read(customer.getId());
        assertThat(savedWebshopOrder != null && !savedWebshopOrder.getOrderItemList().isEmpty() && !updatedCustomer.getWebshopOrderList().isEmpty()).isEqualTo(true);
    }

    @Test
    public void updateWebshopOrder() {
        Customer customer = customerService.createUpdate(new Customer("John12", "Doe12", "john12.doe12@gmail.com"));
        Product product = productService.createUpdate(new Product("abcde_0012", "article-12", 10.12, "description12", true));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(product.getId(), 1));
        WebshopOrder webshopOrder = new WebshopOrder(customer.getId(), orderItemList);
        WebshopOrder savedWebshopOrder = webshopOrderService.createUpdate(webshopOrder);
        savedWebshopOrder.getOrderItemList().get(0).setQuantity(2);
        WebshopOrder updatedWebshopOrder = webshopOrderService.createUpdate(savedWebshopOrder);
        assertThat(updatedWebshopOrder.getOrderItemList().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    public void readWebshopOrder() {
        Customer customer = customerService.createUpdate(new Customer("John13", "Doe13", "john13.doe13@gmail.com"));
        Product product = productService.createUpdate(new Product("abcde_0013", "article-13", 10.13, "description13", true));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(product.getId(), 1));
        WebshopOrder webshopOrder = new WebshopOrder(customer.getId(), orderItemList);
        Long id = webshopOrderService.createUpdate(webshopOrder).getId();
        WebshopOrder savedWebshopOrder = webshopOrderService.read(id);
        assertThat(savedWebshopOrder).isNotEqualTo(null);
    }

    @Test
    public void deleteWebshopOrder() {
        Customer customer = customerService.createUpdate(new Customer("John14", "Doe14", "john14.doe14@gmail.com"));
        Product product = productService.createUpdate(new Product("abcde_0014", "article-14", 10.14, "description14", true));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(product.getId(), 1));
        WebshopOrder webshopOrder = new WebshopOrder(customer.getId(), orderItemList);
        Long id = webshopOrderService.createUpdate(webshopOrder).getId();
        webshopOrderService.delete(id);
        WebshopOrder deletedwebshopOrder = webshopOrderService.read(id);
        assertThat(deletedwebshopOrder).isEqualTo(null);
    }
}
