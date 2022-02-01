package guru.springframework.msscbeerservice.services.inventory.services;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "beer-inventory-service")
public interface BeerInventoryFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BeerInventoryRestTemplate.BASE)
    ResponseEntity<List<BeerInventoryDto>> getOnHandBeerQuantity(@PathVariable UUID beerId);
}
