package otav.br.infrastructure.queue.jms.ibmmq.config;

import io.quarkus.logging.Log;
import lombok.Getter;
import otav.br.infrastructure.queue.jms.JmsConfig;
import otav.br.infrastructure.util.MessagingUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Getter
public class IBMMQConfig implements JmsConfig {

    private String hostName;
    private int port;
    private String queueManager;
    private String channel;
    private int transportType = 1;
    private String appName = "MessagingManageApp";
    private String username;
    private String password;

    public IBMMQConfig(String connectionUri) {
        Map<String, String> configMap = MessagingUtil.parseConnectionUri(connectionUri);

        configMap.keySet().forEach(key -> {
            Arrays.stream(IBMMQConfig.class.getDeclaredFields())
                    .filter(field -> field.getName().equals(key))
                    .findFirst()
                    .ifPresent(field -> {
                        try {
                            field.setAccessible(true);
                            if (field.getType() == int.class) {
                                field.set(this, Integer.parseInt(configMap.get(key)));
                            } else {
                                field.set(this, configMap.get(key));
                            }
                        } catch (IllegalAccessException e) {
                            Log.errorf(e, "Error setting field value for key: %s", key);
                        }
                    });
        });

        validation();
    }

    private void validation() {
        boolean isValid = Objects.nonNull(hostName) &&
                port > 0 &&
                Objects.nonNull(queueManager) &&
                Objects.nonNull(channel);

        if (!isValid) {
            throw new IllegalArgumentException("Invalid IBM MQ configuration. Required fields: hostName, port, queueManager, channel.");
        }
    }

}
