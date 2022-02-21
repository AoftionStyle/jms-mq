package co.aoftionstyle.spring.boot.jms.mq.aws.connection;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import co.aoftionstyle.spring.boot.jms.mq.aws.configuration.SQSConfiguration;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableJms
@Configuration
public class SQSConnection {
    @Autowired
    SQSConfiguration configurator;

    public SQSConnectionFactory sqsConnectionFactory() {
        AWSCredentials credentials = new BasicAWSCredentials(configurator.getAccessKey(), configurator.getSecretKey());
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        // providerConfiguration.setNumberOfMessagesToPrefetch(0);

        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(configurator.getRegion())
                .build();
        log.info(credentialsProvider.getCredentials());
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(providerConfiguration, sqsClient);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory sqsCachingConnectionFactory() throws JMSException{
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(sqsConnectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory sqsJmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsCachingConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency(configurator.getSessionSize());
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    @Qualifier("SQSJmsTemplate")
    public JmsTemplate sqsJmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(sqsCachingConnectionFactory());
        return jmsTemplate;
    }
}
