package webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.model.WebshopOrder;
import webshop.service.WebshopOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/api")
public class WebshopOrderController {

    @Autowired
    WebshopOrderService webshopOrderService;

    @PostMapping("/create-order")
    public ResponseEntity<WebshopOrder> createWebshopOrder(@Valid  @RequestBody WebshopOrder webshopOrder) {
        return new ResponseEntity<>(webshopOrderService.createUpdate(webshopOrder), HttpStatus.CREATED) ;
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<WebshopOrder> updateWebshopOrder(@Valid @RequestBody WebshopOrder webshopOrder, @PathVariable Long id) {
        if (webshopOrderService.read(id) != null) {
            webshopOrder.setId(id);
            return new ResponseEntity<>(webshopOrderService.createUpdate(webshopOrder), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-order/{id}")
    public ResponseEntity<WebshopOrder> readWebshopOrder(@PathVariable Long id) {
        WebshopOrder webshopOrder = webshopOrderService.read(id);
        if (webshopOrder != null) {
            return new ResponseEntity<>(webshopOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<WebshopOrder> deleteWebshopOrder(@PathVariable Long id) {
        webshopOrderService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/finalize-order/{id}")
    public ResponseEntity<WebshopOrder> finalizeWebshopOrder(@PathVariable Long id) {
        return new ResponseEntity<>(webshopOrderService.finalize(id), HttpStatus.OK);
    }
}
