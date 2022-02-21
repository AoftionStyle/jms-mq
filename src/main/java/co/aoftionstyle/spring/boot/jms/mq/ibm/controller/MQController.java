package co.aoftionstyle.spring.boot.jms.mq.ibm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import co.aoftionstyle.spring.boot.jms.mq.ibm.process.MQProcess;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class MQController implements CommandLineRunner {
    @Autowired
    MQProcess process;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        log.info("MQController...");
        // process.onProcess(args);
    }

}
