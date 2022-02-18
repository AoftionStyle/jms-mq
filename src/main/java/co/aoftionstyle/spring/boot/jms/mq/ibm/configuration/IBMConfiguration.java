package co.aoftionstyle.spring.boot.jms.mq.ibm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class IBMConfiguration {
    
    // @Bean(name = "blendConfigurator")
    // @ConfigurationProperties(prefix = "queue")
    // public MQConfigurationProperties blendConfigurator() {
    //     return new MQConfigurationProperties();
    // }

    @Value("${app.queue.queueManager}")
    private String queueManager;

    @Value("${app.queue.channel}")
    private String channel;
    
    @Value("${app.queue.connName}")
    private String connName;

    @Value("${app.queue.user}")
    private String user;

    @Value("${app.queue.password}")
    private String password;

    @Value("${app.queue.destination}")
    private String destination;
}
