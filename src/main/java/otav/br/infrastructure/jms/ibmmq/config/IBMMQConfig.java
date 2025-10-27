package otav.br.infrastructure.jms.ibmmq.config;

import otav.br.infrastructure.jms.JmsConfig;

public record IBMMQConfig(
     String hostName,
     int port,
     String queueManager,
     String channel,
     String appName,
     int transportType,
     String userId,
     String password
) implements JmsConfig {

    public IBMMQConfig mapToConfig(String connectionUri) {
        return this;
    }

}
