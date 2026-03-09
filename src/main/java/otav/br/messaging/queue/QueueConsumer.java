package otav.br.messaging.queue;

import jakarta.jms.JMSException;

import java.util.List;

public interface QueueConsumer {

    List<Message> consume(Integer amount, Integer timeout, Boolean isBrowse);
    void close();

}
