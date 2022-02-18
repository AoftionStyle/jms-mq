package co.aoftionstyle.spring.boot.jms.mq.ibm.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.aoftionstyle.spring.boot.jms.mq.ibm.service.MessageReceiver;
import co.aoftionstyle.spring.boot.jms.mq.ibm.service.MessageSender;
import co.aoftionstyle.spring.boot.jms.mq.process.JmsMQProcession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JmsMQProcess extends JmsMQProcession<String[]> {
    @Autowired
    MessageSender sender;

    @Autowired
    MessageReceiver receiver;
    
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
                log.info("sender sent = {}", message);
                sender.send(message);
                // String msg = receiver.receive();
                // log.info("msg : {}", msg);
                // TimeUnit.SECONDS.sleep(60);
                
                Thread.sleep(2*minute);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
