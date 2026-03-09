package otav.br.messaging.queue.jms;

import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.*;
import lombok.AllArgsConstructor;
import otav.br.messaging.queue.Message;
import otav.br.messaging.queue.QueueConsumer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class JmsConsumerHolder implements QueueConsumer {

    private JMSContext context;
    private JMSConsumer consumer;

    @Override
    public List<Message>  consume(Integer amount, Integer timeout, Boolean isBrowse) {
        List<Message> messages = new ArrayList<>();
        try {
            for (int i = 0; i < amount; i++) {
                var jmsMessage = consumer.receive(timeout);
                if (jmsMessage == null) break;

                messages.add(new Message(
                        getMetadata(jmsMessage),
                        getBodyAsString(jmsMessage)
                ));
            }
            consumer.receive(timeout);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }

    @Override
    public void close() {
        if (context == null) return;

        try {
            context.close();
            consumer.close();
            context = null;
            consumer = null;
        } catch (Exception e) {
            Log.errorf(e, "Erro ao fechar o consumidor JMS: %s", e.getMessage());
            throw e;
        }
    }

    private String getBodyAsString(jakarta.jms.Message jmsMessage) throws JMSException, UnsupportedEncodingException {
        if (jmsMessage instanceof TextMessage textMessage) {
            return textMessage.getText();
        } else if (jmsMessage instanceof BytesMessage bytesMessage) {
            byte[] data = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(data);
            String codePage = bytesMessage.getStringProperty(CommonConstants.JMS_IBM_CHARACTER_SET);
            return new String(data, codePage);
        } else {
            return "Unsupported message type: " + jmsMessage.getClass().getName();
        }
    }

    private Map<String, String> getMetadata(jakarta.jms.Message jmsMessage) throws JMSException {
        var metadata = new HashMap<String, String>();
        var propertyNames = jmsMessage.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = propertyNames.nextElement().toString();
            String propertyValue = jmsMessage.getStringProperty(propertyName);
            metadata.put(propertyName, propertyValue);
        }
        return metadata;
    }

}
