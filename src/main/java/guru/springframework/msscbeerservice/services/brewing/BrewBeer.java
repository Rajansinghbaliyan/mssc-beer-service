package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import guru.springframework.msscbeerservice.event.BeerEvent;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrewBeer {

    private final BeerService beerService;
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 4000)
    public void brewingBeer() {
        beerService.findAllBeer()
                .parallelStream()
                .filter(BeerDto::shouldStartBrew)
                .forEach(
                        beerDto -> jmsTemplate.convertAndSend(JmsConfig.BREW_BEER_QUEUE, new BeerEvent(beerDto))
                );

    }
}
