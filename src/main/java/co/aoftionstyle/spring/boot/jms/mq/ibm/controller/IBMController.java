package co.aoftionstyle.spring.boot.jms.mq.ibm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import co.aoftionstyle.spring.boot.jms.mq.ibm.process.JmsMQProcess;

@Controller
public class IBMController implements CommandLineRunner {

    @Autowired
    JmsMQProcess process;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
       process.onProcess(args);
    }
    
}
