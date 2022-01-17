package guru.springframework.msscbeerservice.listener;

import guru.sfg.common.Events.BeerInventoryEvent;
import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.event.BeerEvent;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final JmsTemplate jmsTemplate;
    private final BeerRepository beerRepository;

    @JmsListener(destination = JmsConfig.BREW_BEER_QUEUE)
    public void listener(BeerEvent event) {
        BeerDto beerDto = event.getBeerDto();
        try {
            Beer beer = beerRepository.findById(beerDto.getId()).orElseThrow(NotFoundException::new);

            jmsTemplate.convertAndSend(
                    JmsConfig.BEER_INVENTORY_QUEUE,
                    BeerInventoryEvent.builder()
                            .beerInventoryDto(BeerInventoryDto.builder()
                                    .beerId(beerDto.getId())
                                    .quantityOnHand(beer.getQuantityToBrew())
                                    .build()
                            )
                            .build()
            );
        } catch (NotFoundException exception) {
            log.error("Error in get beer BeerID:" + beerDto.getId());
        }
    }
}
