package co.aoftionstyle.spring.boot.jms.mq.service;

public class MessageService<E> implements MessageServicable<E> {

    @Override
    public void send(E e) {
    }

    @Override
    public E receive() {
        return null;
    }
    
}
