package co.aoftionstyle.spring.boot.jms.mq.ibm.connection;

import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.mq.spring.boot.MQConnectionFactoryCustomizer;
import com.ibm.mq.spring.boot.MQConnectionFactoryFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.IBMConfiguration;

@Configuration
@EnableJms
public class IBMConnection {

    @Autowired
    @Qualifier("IBMConfiguration") 
    IBMConfiguration configurator;
    
    @Bean
    public MQConfigurationProperties configureProperties() {
        MQConfigurationProperties properties = new MQConfigurationProperties();
        properties.setQueueManager(configurator.getQueueManager());
        properties.setChannel(configurator.getChannel());
        properties.setConnName(configurator.getConnName());
        properties.setUser(configurator.getUser());
        properties.setPassword(configurator.getPassword());
        return properties;
    }

    @Bean
    public ObjectProvider<List<MQConnectionFactoryCustomizer>> getfactoryCustomizers() {
        ObjectProvider<List<MQConnectionFactoryCustomizer>> factoryCustomizers = new ObjectProvider<List<MQConnectionFactoryCustomizer>>() {
            @Override
            public List<MQConnectionFactoryCustomizer> getObject() throws BeansException {
                return null;
            }

            @Override
            public List<MQConnectionFactoryCustomizer> getObject(Object... args) throws BeansException {
                return null;
            }

            @Override
            public List<MQConnectionFactoryCustomizer> getIfAvailable() throws BeansException {
                return null;
            }

            @Override
            public List<MQConnectionFactoryCustomizer> getIfUnique() throws BeansException {
                return null;
            }
        };
        return factoryCustomizers;
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() throws JMSException{
        // QueueProperties localProperties = localConfigurationProperties();
        // MQConnectionFactory connectionFactory = new MQConnectionFactory();
        // log.info("local : {}", localProperties.getDestination());
        // connectionFactory.setQueueManager(localProperties.getQueueManager());
        // connectionFactory.setChannel(localProperties.getChannel());
        // connectionFactory.setConnectionNameList(localProperties.getConnName());
        // connectionFactory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
        // connectionFactory.
        // List<MQConnectionFactoryCustomizer> factoryCustomizers;
        // MQConnectionFactory a = new MQConnectionFactoryFactory(configurationProperties(), getfactoryCustomizers().getIfAvailable()).createConnectionFactory(MQConnectionFactory.class);

        MQConfigurationProperties properties = configureProperties();
        List<MQConnectionFactoryCustomizer> factoryCustomizers = getfactoryCustomizers().getIfAvailable();
        return new MQConnectionFactoryFactory(properties, factoryCustomizers).createConnectionFactory(MQConnectionFactory.class);
    }

    /*
     * Optionally you can use cached connection factory if performance is a big concern.
     */
    @Bean
    public ConnectionFactory cachingConnectionFactory() throws JMSException{
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }

    @Bean
    @Primary
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(@Qualifier("cachingConnectionFactory") ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    // @Bean
    // public TaskExecutor taskExecutor() {
    //     return new DefaultManagedTaskExecutor();
    // }

    // /*
    //  * Message listener container, used for invoking messageReceiver.onMessage on message reception.
    //  */
    // @Bean
    // public DefaultJmsListenerContainerFactory container() throws JMSException{
    //     // QueueProperties localProperties = localConfigurationProperties();
    //     DefaultJmsListenerContainerFactory container = new DefaultJmsListenerContainerFactory();
    //     // DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    //     container.setConnectionFactory(connectionFactory());
    //     container.setTaskExecutor(taskExecutor());
    //     // container.setDestinationName(localProperties.getDestination());
    //     // container.setMessageListener(messageReceiver);
    //     return container;
    // }

}
