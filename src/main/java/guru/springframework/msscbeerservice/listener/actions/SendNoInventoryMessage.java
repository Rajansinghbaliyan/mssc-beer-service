package guru.springframework.msscbeerservice.listener.actions;

import guru.sfg.common.events.NoInventoryEvent;
import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import org.springframework.jms.core.JmsTemplate;

public class SendNoInventoryMessage {
    public static void action(JmsTemplate jmsTemplate, NoInventoryEvent event) {
        jmsTemplate.convertAndSend(JmsConfig.VALIDATION_BUT_NO_INVENTORY_QUEUE, event);
    }
}
