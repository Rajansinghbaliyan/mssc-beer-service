package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public class BeerInventoryEvent extends BeerEvent {
    public BeerInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
