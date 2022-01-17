package guru.springframework.msscbeerservice.services.inventory.services;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class BeerInventoryRestTemplate implements BeerInventoryService {
    private final RestTemplate restTemplate;
    private final String HOST;
    private String BASE;

    public BeerInventoryRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${beer_inventory_host}") String HOST) {
        this.restTemplate = restTemplateBuilder.build();
        this.HOST = HOST;
    }

    private String getUrl(UUID uuid) {
        return HOST + "/api/v1/beer/" + uuid.toString() + "/inventory";
    }

    @Override
    public Integer getOnHandQuantityByBeerId(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> response = restTemplate
                .exchange(getUrl(beerId), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                        });

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
        ResponseEntity<BeerInventoryDto> response = restTemplate
                .postForEntity(getUrl(dto.getBeerId()), dto, BeerInventoryDto.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            return response.getBody();
        } else
            return null;
    }
}
