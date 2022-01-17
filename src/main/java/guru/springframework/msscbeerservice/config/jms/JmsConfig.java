package guru.springframework.msscbeerservice.config.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    public static final String BREW_BEER_QUEUE = "brew-beer";
    public static final String BEER_INVENTORY_QUEUE = "beer-inventory";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("_type");
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper);

        return converter;
    }
}
