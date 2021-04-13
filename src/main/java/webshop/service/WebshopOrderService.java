package webshop.service;

import webshop.model.WebshopOrder;

public interface WebshopOrderService {

    public WebshopOrder createUpdate(WebshopOrder webshopOrder);

    public WebshopOrder read(Long id);

    public void delete(Long id);

    public WebshopOrder finalize(Long id);
}
