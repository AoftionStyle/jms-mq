package co.aoftionstyle.spring.boot.jms.mq.ibm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.MQConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class MQReceiver extends MessageService<String> {
    @Autowired
    MQConfiguration configuration;

    @Autowired
    @Qualifier("MQJmsTemplate")
    JmsTemplate jmsTemplate;

    public String receive() {
        Object text = jmsTemplate.receiveAndConvert(configuration.getDestination());
        return text.toString();
    }

}
