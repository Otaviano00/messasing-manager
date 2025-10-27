package otav.br.infrastructure.jms.ibmmq.factory;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSException;
import otav.br.infrastructure.jms.ibmmq.config.IBMMQConfig;

@ApplicationScoped
public class IBMMQConnectionFactory {

    public MQQueueConnectionFactory createConnectionFactory(IBMMQConfig config) throws JMSException {
        return null;
    }

}
