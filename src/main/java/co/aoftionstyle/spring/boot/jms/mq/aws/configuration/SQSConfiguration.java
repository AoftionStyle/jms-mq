package co.aoftionstyle.spring.boot.jms.mq.aws.configuration;

import com.amazonaws.regions.Regions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.sqs")
public class SQSConfiguration {
    private String accessKey;
    private String secretKey;
    private String region = Regions.AP_SOUTHEAST_1.getName();
    private String endpoint;
    private String queueName;
    private String sessionSize = "0-10";
}
