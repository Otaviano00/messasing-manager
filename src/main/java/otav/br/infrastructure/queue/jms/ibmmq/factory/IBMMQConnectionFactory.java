package otav.br.infrastructure.queue.jms.ibmmq.factory;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSException;
import otav.br.infrastructure.queue.jms.ibmmq.config.IBMMQConfig;

@ApplicationScoped
public class IBMMQConnectionFactory {

    public MQQueueConnectionFactory createConnectionFactory(IBMMQConfig config) throws JMSException {
        MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        connectionFactory.setHostName(config.getHostName());
        connectionFactory.setPort(config.getPort());
        connectionFactory.setQueueManager(config.getQueueManager());
        connectionFactory.setChannel(config.getChannel());
        connectionFactory.setTransportType(config.getTransportType()); // 1 para TCP/IP

        if (config.getUsername() != null && config.getPassword() != null) {
            connectionFactory.setStringProperty(CommonConstants.USERID, config.getUsername());
            connectionFactory.setStringProperty(CommonConstants.PASSWORD, config.getPassword());
        }

        return connectionFactory;
    }

}
