package co.aoftionstyle.spring.boot.jms.mq.service;

public interface MessageServicable<E> {
    public void send(E e);
    public E receive();
}
