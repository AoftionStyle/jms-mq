package co.aoftionstyle.spring.boot.jms.mq.ibm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.IBMConfiguration;
import co.aoftionstyle.spring.boot.jms.mq.service.MessageService;

@Component
public class MessageSender extends MessageService<String> {

    @Autowired
    IBMConfiguration configuration;

    @Autowired
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