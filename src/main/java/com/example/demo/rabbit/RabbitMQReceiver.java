package com.example.demo.rabbit;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
@RequiredArgsConstructor
public class RabbitMQReceiver {

    private final BookService bookService;
    private final Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());

    @RabbitHandler
    public void toReserve(Book book) {
        bookService.reserveBook(book);
        logger.info("BookDto listener invoked - Book reserved with id : " + book.getId());
    }
}
