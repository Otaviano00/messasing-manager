package otav.br.service.queue;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import lombok.AllArgsConstructor;
import otav.br.infrastructure.queue.jms.ibmmq.config.IBMMQConfig;
import otav.br.infrastructure.queue.jms.ibmmq.factory.IBMMQConnectionFactory;
import otav.br.messaging.queue.QueueConsumer;
import otav.br.messaging.queue.jms.JmsConsumerHolder;
import otav.br.resource.queue.dto.ConnectionRequest;

@ApplicationScoped
@AllArgsConstructor
public class QueueService {

    private IBMMQConnectionFactory ibmMqConnectionFactory;

    public QueueConsumer createQueueConsumer(ConnectionRequest connectionRequest) throws JMSException {

        switch (connectionRequest.getBrokerType()) {
            case IBMMQ:
                return createIBMMQConsumer(connectionRequest);
            default:
                throw new IllegalArgumentException("Tipo de broker desconhecido: " + connectionRequest.getBrokerType());
        }
    }

    private QueueConsumer createIBMMQConsumer(ConnectionRequest connectionRequest) throws JMSException {
        var config = new IBMMQConfig(connectionRequest.getConnectionUri());
        try {
            JMSContext context = ibmMqConnectionFactory.createConnectionFactory(config).createContext();
            Queue queue = context.createQueue(connectionRequest.getQueueName());
            JMSConsumer consumer = context.createConsumer(queue);
            return new JmsConsumerHolder(context, consumer);

        } catch (Exception e) {
            Log.errorf(e, "Erro ao criar consumidor JMS: %s", e.getMessage());
            throw e;
        }
    }
}
