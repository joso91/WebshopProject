package webshop.webshopOrderFinalize;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebshopOrderFinalizeTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8090;
    private static WireMockServer server = new WireMockServer(PORT);

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    WebshopOrderService webshopOrderService;

    @BeforeClass
    public static void setup() {
        server.start();
        WireMock.configureFor(HOST, PORT);
        stubFor(get(urlEqualTo("http://api.hnb.hr/tecajn/v2?valuta=EUR")).willReturn(aResponse().withStatus(200)));
    }

    @Test
    public void finalizeWebshopOrder() {
        Customer customer = customerService.createUpdate(new Customer("John20", "Doe20", "john20.doe20@gmail.com"));
        Product product1 = productService.createUpdate(new Product("abcde_0021", "article-21", 10.21, "description21", true));
        Product product2 = productService.createUpdate(new Product("abcde_0022", "article-22", 10.22, "description22", false));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(product1.getId(), 20));
        orderItemList.add(new OrderItem(product2.getId(), 20));
        WebshopOrder webshopOrder = new WebshopOrder(customer.getId(), orderItemList);
        Long id = webshopOrderService.createUpdate(webshopOrder).getId();
        WebshopOrder finalizedOrder = webshopOrderService.finalize(id);
        assertThat(finalizedOrder != null && finalizedOrder.getTotalPriceHRK().equals(204.2)  && finalizedOrder.getTotalPriceEUR() > 0 &&
                finalizedOrder.getStatus() == Status.SUBMITTED).isEqualTo(true);
    }

    @AfterClass
    public static void teardown() {
        if (server != null && server.isRunning()) {
            server.shutdownServer();
        }
    }
}
