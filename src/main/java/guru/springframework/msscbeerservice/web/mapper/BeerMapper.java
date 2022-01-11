package guru.springframework.msscbeerservice.web.mapper;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(value = BeerMapperDecorator.class)
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToDtoWithOutInventory(Beer beer);
}
