package otav.br.resource.queue.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import otav.br.infrastructure.enums.BrokerType;
import otav.br.infrastructure.enums.ConnectionType;

@Data
public class ConnectionRequest {
    @NotNull
    private BrokerType brokerType;
    @NotNull
    private ConnectionType connectionType;
    @NotEmpty
    private String connectionUri;
    @NotEmpty
    private String queueName;
}
