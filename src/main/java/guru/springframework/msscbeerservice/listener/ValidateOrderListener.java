package guru.springframework.msscbeerservice.listener;

import guru.sfg.common.events.NoInventoryEvent;
import guru.sfg.common.events.ValidateOrderEvent;
import guru.sfg.common.events.ValidationFailedEvent;
import guru.sfg.common.events.ValidationSuccessfulEvent;
import guru.sfg.common.models.BeerOrderDto;
import guru.sfg.common.models.BeerOrderLineDto;
import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import guru.springframework.msscbeerservice.listener.actions.SendNoInventoryMessage;
import guru.springframework.msscbeerservice.listener.actions.SendValidationFailedMessage;
import guru.springframework.msscbeerservice.listener.actions.SendValidationSuccessfulMessage;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.model.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateOrderListener {

    private final BeerService service;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_QUEUE)
    public void listener(ValidateOrderEvent event) {
        log.info("Validating the Beer Order id: " + event.getBeerOrderDto().getId());
        BeerOrderDto beerOrderDto = event.getBeerOrderDto();

        try {
            List<UUID> beersQuantityNotAvailable = beerOrderDto.getBeerOrderLines().parallelStream()
                    .filter(beerOrderLineDto ->
                            Optional.ofNullable(service.getById(beerOrderLineDto.getBeerId(), true))
                                    .map(beerDto -> beerDto.getQuantityOnHand() < beerOrderLineDto.getOrderQuantity())
                                    .orElseThrow(NotFoundException::new))
                    .map(BeerOrderLineDto::getBeerId)
                    .collect(Collectors.toList());

            if (beersQuantityNotAvailable.isEmpty()) {
                SendValidationSuccessfulMessage.action(
                        jmsTemplate,
                        ValidationSuccessfulEvent.builder()
                                .beerOrderDto(beerOrderDto)
                                .build()
                );
            } else {
                SendNoInventoryMessage.action(
                        jmsTemplate,
                        NoInventoryEvent.builder()
                                .beerOrderDto(beerOrderDto)
                                .beersWithLessInventory(beersQuantityNotAvailable)
                                .build()
                );
            }

        } catch (NotFoundException e) {
            log.error(e.getMessage());
            SendValidationFailedMessage.action(
                    jmsTemplate,
                    ValidationFailedEvent.builder()
                            .beerOrderDto(beerOrderDto)
                            .build()
            );
        }

    }
}
