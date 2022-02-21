package co.aoftionstyle.spring.boot.jms.mq.aws.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.aoftionstyle.spring.boot.jms.mq.aws.service.SQSReceiver;
import co.aoftionstyle.spring.boot.jms.mq.aws.service.SQSSender;
import co.aoftionstyle.spring.boot.jms.mq.process.JmsMQProcession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SQSProcess extends JmsMQProcession<String[]> {
    @Autowired
    SQSSender sender;

    @Autowired
    SQSReceiver receiver;
    
    @Override
    public void onProcess(String[] args) {
        log.info("onProcess...");
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy hh:mm:ss");
        // String dateString = format.format(new Date());

        int secPerMillis = 1000;
        int minute = 60*secPerMillis;

        for(int i = 0; i < 10; i++) {
            try {
                String dateString = format.format(new Date());
                String message = "aoftion:" + dateString;
                log.info("loop {}, sender sent = {}",i, message);
                sender.send(message);
                // String msg = receiver.receive();
                // log.info("msg : {}", msg);
                // TimeUnit.SECONDS.sleep(60);
                
                int mins = (int) (minute * 0.5);
                Thread.sleep(mins);
                log.info("received = {}", receiver.receive());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
