package guru.springframework.msscbeerservice.listener.actions;

import guru.sfg.common.events.ValidationFailedEvent;
import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import org.springframework.jms.core.JmsTemplate;

public class SendValidationFailedMessage {
    public static void action(JmsTemplate jmsTemplate, ValidationFailedEvent event){
        jmsTemplate.convertAndSend(JmsConfig.VALIDATION_FAILED_QUEUE,event);
    }
}
