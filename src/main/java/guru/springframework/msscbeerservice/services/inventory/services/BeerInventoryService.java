package guru.springframework.msscbeerservice.services.inventory.services;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnHandQuantityByBeerId(UUID beerId);
}
