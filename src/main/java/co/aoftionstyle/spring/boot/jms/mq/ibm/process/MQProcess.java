package co.aoftionstyle.spring.boot.jms.mq.ibm.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.aoftionstyle.spring.boot.jms.mq.ibm.service.MQReceiver;
import co.aoftionstyle.spring.boot.jms.mq.ibm.service.MQSender;
import co.aoftionstyle.spring.boot.jms.mq.process.JmsMQProcession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MQProcess extends JmsMQProcession<String[]> {
    @Autowired
    MQSender sender;

    @Autowired
    MQReceiver receiver;
    
    @Override
    public void onProcess(String[] args) {
        log.info("onProcess...");
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy hh:mm:ss");

        int secPerMillis = 1000;
        int minute = 60*secPerMillis;

        while(true) {
            try {
                String dateString = format.format(new Date());
                String message = "aoftion:" + dateString;
                sender.send(message);
                
                int mins = (int) (minute * 0.5);
                Thread.sleep(mins);
                log.info("received = {}", receiver.receive());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
