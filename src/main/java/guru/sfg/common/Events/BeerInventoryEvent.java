package guru.sfg.common.Events;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryEvent {
    private BeerInventoryDto beerInventoryDto;
}
