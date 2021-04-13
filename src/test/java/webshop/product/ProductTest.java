package webshop.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.model.Product;
import webshop.service.ProductService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    ProductService productService;

    @Test
    public void createProduct() {
        Product product = new Product("abcde_0001", "article-1", 10.01, "description1", true);
        Product savedProduct = productService.createUpdate(product);
        assertNotNull(savedProduct);
    }

    @Test
    public void updateProduct() {
        Product product = new Product("abcde_0002", "article-2", 10.02, "description2", true);
        Product savedProduct = productService.createUpdate(product);
        savedProduct.setAvailable(false);
        Product updatedProduct = productService.createUpdate(savedProduct);
        assertEquals(updatedProduct.getAvailable(), false);
    }

    @Test
    public void readProduct() {
        Product product = new Product("abcde_0003", "article-3", 10.03, "description3", true);
        Long id = productService.createUpdate(product).getId();
        Product savedProduct = productService.read(id);
        assertNotNull(savedProduct);
    }

    @Test
    public void deleteProduct() {
        Product product = new Product("abcde_0004", "article-4", 10.04, "description4", true);
        Long id = productService.createUpdate(product).getId();
        productService.delete(id);
        Product deletedProduct = productService.read(id);
        assertNull(deletedProduct);
    }
}
