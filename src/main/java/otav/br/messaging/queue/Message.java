package otav.br.messaging.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Message {
    private Map<String, String> metadata;
    private String payload;
}
