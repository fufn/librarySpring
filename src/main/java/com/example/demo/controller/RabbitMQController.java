package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.rabbit.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class RabbitMQController {

    private final RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/sender")
    public String producer(@RequestBody BookDto bookDto){
        rabbitMQSender.sendBookDto(bookDto);
        return "Message sent to the RabbitMQ Queue Successfully";
    }

}
