package co.aoftionstyle.spring.boot.jms.mq.ibm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.MQConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class MQSender extends MessageService<String> {
    @Autowired
    MQConfiguration configuration;

    @Autowired
    @Qualifier("MQJmsTemplate")
    JmsTemplate jmsTemplate;

    public void send(String message) {
        // jmsTemplate.send(new MessageCreator(){
        //     @Override
        //     public Message createMessage(Session session) throws JMSException{
        //         ObjectMessage objectMessage = session.createObjectMessage(message);
        //         return objectMessage;
        //     }

        //     @Override
        //     public Message createMessage(Session session) throws JMSException {
        //         // TODO Auto-generated method stub
        //         return null;
        //     }
        // });
        jmsTemplate.convertAndSend(configuration.getDestination(), message);
    }

}