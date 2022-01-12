package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPageList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerService {
    BeerPageList findAll(Pageable pageable);

    BeerPageList findAll(Pageable pageable, Boolean showInventory);

    BeerDto getById(UUID beerId,Boolean showInventory);

    BeerDto findBeerByUpc(String upc);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
