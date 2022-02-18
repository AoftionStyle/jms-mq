package co.aoftionstyle.spring.boot.jms.mq.ibm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.IBMConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class MessageReceiver extends MessageService<String> {
    @Autowired
    IBMConfiguration configuration;

    @Autowired
    JmsTemplate jmsTemplate;

    public String receive() {
        Object text = jmsTemplate.receiveAndConvert(configuration.getDestination());
        return text.toString();
    }

}
