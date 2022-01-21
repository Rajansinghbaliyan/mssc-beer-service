package guru.springframework.msscbeerservice.listener.actions;

import guru.sfg.common.events.ValidationSuccessfulEvent;
import guru.springframework.msscbeerservice.config.jms.JmsConfig;
import org.springframework.jms.core.JmsTemplate;

public class SendValidationSuccessfulMessage {
    public static void action(JmsTemplate jmsTemplate, ValidationSuccessfulEvent event) {
        jmsTemplate.convertAndSend(JmsConfig.VALIDATION_SUCCESS_QUEUE, event);
    }
}
