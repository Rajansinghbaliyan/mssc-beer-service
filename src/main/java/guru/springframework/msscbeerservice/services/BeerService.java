package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPageList;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    BeerPageList findAll(Pageable pageable);

    List<BeerDto> findAllBeer();

    BeerPageList findAll(Pageable pageable, Boolean showInventory);

    BeerDto getById(UUID beerId, Boolean showInventory);

    BeerDto findBeerByUpc(String upc);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
