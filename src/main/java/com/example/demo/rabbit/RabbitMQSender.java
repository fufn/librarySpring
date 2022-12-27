package com.example.demo.rabbit;

import com.example.demo.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;
    private final Queue queue;

    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void sendBookDto(BookDto bookDto) {
        rabbitTemplate.convertAndSend(queue.getName(), bookDto);
        logger.info("Sending Message to the Queue : " + bookDto.toString());
    }

}
