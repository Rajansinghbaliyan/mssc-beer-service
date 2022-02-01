package guru.springframework.msscbeerservice.services.inventory.services;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Profile("local-discovery")
public class BeerInventoryFeignService implements BeerInventoryService {

    private final BeerInventoryFeignClient client;

    @Override
    public Integer getOnHandQuantityByBeerId(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> response = client.getOnHandBeerQuantity(beerId);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody()
                    .stream()
                    .mapToInt(BeerInventoryDto::getQuantityOnHand)
                    .sum();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public BeerInventoryDto createNewInventory(BeerInventoryDto dto) {
        return null;
    }
}
