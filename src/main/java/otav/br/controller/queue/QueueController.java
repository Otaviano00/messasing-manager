package otav.br.controller.queue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSException;
import lombok.AllArgsConstructor;
import otav.br.messaging.queue.Message;
import otav.br.messaging.queue.QueueConsumer;
import otav.br.resource.queue.dto.ConnectionRequest;
import otav.br.resource.queue.dto.MessageDTO;
import otav.br.service.queue.QueueService;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class QueueController {

    private QueueService queueService;

    public List<MessageDTO> receiveMessages(ConnectionRequest connectionRequest, Integer amount, Integer timeout, Boolean isBrowse) throws JMSException {
        QueueConsumer consumer = queueService.createQueueConsumer(connectionRequest);

        List<Message> messages = consumer.consume(amount, timeout, isBrowse);

        consumer.close();

        return messages.stream().map(message ->
            new MessageDTO(
                    message.getMetadata(),
                    message.getPayload()
            )
        ).toList();
    }

}
