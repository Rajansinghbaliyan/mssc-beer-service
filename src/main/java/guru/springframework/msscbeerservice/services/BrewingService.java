package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class BrewingService {

    private final BeerService beerService;
    private final BeerMapper mapper;

    @Scheduled(fixedRate = 3000)
    public void brewing() {
        List<BeerDto> beerDtos = beerService.findAllBeer();

        beerDtos.stream()
                .filter(BeerDto::shouldStartBrew)
                .forEach(BrewBeerEvent::new);
    }
}
