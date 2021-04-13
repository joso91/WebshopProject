package webshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webshop.model.WebshopOrder;

@Repository
public interface WebshopOrderDAO extends CrudRepository<WebshopOrder, Long> {
}
