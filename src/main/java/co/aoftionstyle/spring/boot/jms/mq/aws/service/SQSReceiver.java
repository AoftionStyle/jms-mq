package co.aoftionstyle.spring.boot.jms.mq.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.aws.configuration.SQSConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class SQSReceiver extends MessageService<String> {
    @Autowired
    SQSConfiguration configurator;

    @Autowired
    @Qualifier("SQSJmsTemplate")
    JmsTemplate jmsTemplate;

    public String receive() {
        Object text = jmsTemplate.receiveAndConvert(configurator.getQueueName());
        return text.toString();
    }
}
