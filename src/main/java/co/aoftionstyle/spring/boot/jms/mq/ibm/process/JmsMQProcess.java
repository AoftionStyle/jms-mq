package co.aoftionstyle.spring.boot.jms.mq.ibm.process;

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

        sender.send("aoftion");
        for(int i = 0; i < 1000; i++) {
            String msg = receiver.receive();
            log.info("msg : {}", msg);
        }
    }

}
