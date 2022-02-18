package co.aoftionstyle.spring.boot.jms.mq.ibm.configuration;

import com.ibm.mq.spring.boot.MQConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
public class IBMConfiguration extends MQConfigurationProperties {
    @Getter
    @Setter
    private String destination;
}
