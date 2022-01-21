package guru.springframework.msscbeerservice.services.inventory.services;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnHandQuantityByBeerId(UUID beerId);

    BeerInventoryDto createNewInventory(BeerInventoryDto dto);
}
