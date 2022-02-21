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
import org.springframework.jms.core.JmsTemplate;

import co.aoftionstyle.spring.boot.jms.mq.ibm.configuration.MQConfiguration;

@EnableJms
@Configuration
public class MQConnection {
    @Autowired
    MQConfiguration configurator;

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
    public ConnectionFactory mqConnectionFactory() throws JMSException{
        MQConfigurationProperties properties = configurator;
        List<MQConnectionFactoryCustomizer> factoryCustomizers = getfactoryCustomizers().getIfAvailable();
        return new MQConnectionFactoryFactory(properties, factoryCustomizers).createConnectionFactory(MQConnectionFactory.class);
    }

    /*
     * Optionally you can use cached connection factory if performance is a big concern.
     */
    @Bean
    public ConnectionFactory mqCachingConnectionFactory() throws JMSException{
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(mqConnectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }

    @Bean
    @Primary
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(@Qualifier("mqCachingConnectionFactory") ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /*
     * Used for Sending Messages.
     */
    @Bean(name = "MQJmsTemplate")
    public JmsTemplate mqJmsTemplate() throws JMSException{
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(mqCachingConnectionFactory());
        return template;
    }

}
