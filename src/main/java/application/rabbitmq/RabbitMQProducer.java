package application.rabbitmq;

import application.dto.feign.FeignMessage;
import application.json.RabbitConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing_key.name}")
    private String routingKey;

    private final RabbitTemplate template;

    public void sendTaskMessage(FeignMessage message){
        log.info("About to send a message to RabbitMQ via amqp");
        template.convertAndSend(exchange, routingKey, message);
    }

}
