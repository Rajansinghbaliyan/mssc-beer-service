package guru.springframework.msscbeerservice.sender;

import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BreweryStarter {

    private final JmsTemplate jmsTemplate;

//    @Scheduled(fixedRate = 3000)
    public void startBrewring(){
        BeerInventoryDto dto = BeerInventoryDto.builder()
                .beerId(UUID.randomUUID())
                .build();

        jmsTemplate.convertAndSend(JmsConfig.BREWERY_QUEUE,dto);
    }

}
