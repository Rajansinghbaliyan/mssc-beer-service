package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPageList;
import guru.springframework.msscbeerservice.web.model.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPageList findAll(Pageable pageable) {
        Page<Beer> beerPage = beerRepository.findAll(pageable);
        return new BeerPageList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                beerPage.getPageable(),
                beerPage.getTotalElements());
    }

    @Override
    public List<BeerDto> findAllBeer() {
        log.info("Running Brewing Service");
        return StreamSupport.stream(beerRepository.findAll().spliterator(), true)
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public BeerPageList findAll(Pageable pageable, Boolean showInventory) {
        Page<Beer> beerPage = beerRepository.findAll(pageable);
        return new BeerPageList(beerPage.getContent()
                .stream()
                .map(showInventory ? beerMapper::beerToBeerDto : beerMapper::beerToDtoWithOutInventory)
                .collect(Collectors.toList()),
                beerPage.getPageable(),
                beerPage.getTotalElements());
    }

    @Override
    public BeerDto getById(UUID beerId, Boolean showInventory) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        Function<Beer, BeerDto> mapper = showInventory ? beerMapper::beerToBeerDto : beerMapper::beerToDtoWithOutInventory;
        return mapper.apply(beer);
    }

    @Override
    public BeerDto findBeerByUpc(String upc) {
        return beerMapper.beerToBeerDto(
                beerRepository.findBeerByUpc(upc).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
