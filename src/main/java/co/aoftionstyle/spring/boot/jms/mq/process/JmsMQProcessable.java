package co.aoftionstyle.spring.boot.jms.mq.process;

public interface JmsMQProcessable<E> {
    void onProcess(E e);
}
