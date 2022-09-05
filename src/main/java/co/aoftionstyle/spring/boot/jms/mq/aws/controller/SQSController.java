package co.aoftionstyle.spring.boot.jms.mq.aws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import co.aoftionstyle.spring.boot.jms.mq.aws.process.SQSProcess;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class SQSController implements CommandLineRunner {
    @Autowired
    SQSProcess process;

    @Override
    public void run(String... args) throws Exception {
        log.info("SQSController...");
        process.onProcess(args);
    }

}
