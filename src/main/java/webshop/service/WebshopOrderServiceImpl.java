package webshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.model.*;
import webshop.repository.WebshopOrderDAO;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class WebshopOrderServiceImpl implements WebshopOrderService {

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    WebshopOrderDAO webshopOrderDAO;

    @Override
    public WebshopOrder createUpdate(WebshopOrder webshopOrder) {
        Customer customer = customerService.read(webshopOrder.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("Customer with id = " + webshopOrder.getCustomerId() + " doesn't exist.");
        }
        checkExpandWebshopOrder(webshopOrder);
        customer.getWebshopOrderList().removeIf(e -> e.getId().equals(webshopOrder.getId()));
        customer.getWebshopOrderList().add(webshopOrder);
        Customer updatedCustomer = customerService.createUpdate(customer);
        WebshopOrder createdWebshopOrder = findCreatedWebshopOrder(updatedCustomer.getWebshopOrderList());
        return checkExpandWebshopOrder(createdWebshopOrder);
    }

    @Override
    public WebshopOrder read(Long id) {
        WebshopOrder webshopOrder = webshopOrderDAO.findById(id).orElse(null);
        if (webshopOrder != null) {
            WebshopOrder webshopOrderExpand = checkExpandWebshopOrder(webshopOrder);
            return webshopOrderExpand;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        webshopOrderDAO.deleteById(id);
    }

    @Override
    public WebshopOrder finalize(Long id) {
        WebshopOrder webshopOrder = read(id);
        if (webshopOrder == null) {
            throw new RuntimeException("WebshopOrder with id = " + id + " doesn't exist.");
        } else if (webshopOrder.getStatus() == Status.SUBMITTED) {
            throw new RuntimeException("WebshopOrder with id = " + id + " is already submitted.");
        }
        Double hnbExchangeRate = findExchangeRate();
        Double totalPriceHRK = webshopOrder.getOrderItemList().stream().
                mapToDouble(e -> e.getQuantity() * (e.getProduct().getAvailable() ? e.getProduct().getPriceHRK() : 0)).sum();
        Double totalPriceEUR = totalPriceHRK / hnbExchangeRate;
        totalPriceHRK = Math.round(totalPriceHRK * 100) / 100.0;
        totalPriceEUR = Math.round(totalPriceEUR * 100) / 100.0;
        webshopOrder.setTotalPriceHRK(totalPriceHRK);
        webshopOrder.setTotalPriceEUR(totalPriceEUR);
        webshopOrder.setStatus(Status.SUBMITTED);
        return createUpdate(webshopOrder);
    }

    private WebshopOrder findCreatedWebshopOrder(List<WebshopOrder> webshopOrderList) {
        Long id = webshopOrderList.stream().mapToLong(e -> e.getId()).max().getAsLong();
        return webshopOrderList.stream().filter(e -> e.getId().equals(id)).findAny().get();
    }

    private WebshopOrder checkExpandWebshopOrder(WebshopOrder webshopOrder) {
        for (OrderItem orderItem : webshopOrder.getOrderItemList()) {
            Product product = productService.read(orderItem.getProductId());
            if (product == null) {
                throw new RuntimeException("Product with id = " + orderItem.getProductId() + " doesn't exist.");
            }
            orderItem.setProduct(product);
        }
        return webshopOrder;
    }

    private Double findExchangeRate() {
        try {
            URL url = new URL("http://api.hnb.hr/tecajn/v2?valuta=EUR");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            HNBExchangeRate[] hnbArray = mapper.readValue(responseStream, HNBExchangeRate[].class);
            Double hnbExchangeRate = Double.parseDouble(hnbArray[0].getSrednjiTecaj().replace(",", "."));
            return hnbExchangeRate;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}