package co.aoftionstyle.spring.boot.jms.mq.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.aws.configuration.SQSConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class SQSSender extends MessageService<String> {
    @Autowired
    SQSConfiguration configurator;

    @Autowired
    @Qualifier("SQSJmsTemplate")
    JmsTemplate jmsTemplate;

    public void send(String message) {
        jmsTemplate.convertAndSend(configurator.getQueueName(), message);
    }
}
