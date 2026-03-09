package otav.br.infrastructure.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagingUtil {

    public static Map<String, String> parseConnectionUri(String connectionUri) {
        Map<String, String> configMap = new HashMap<>();

        List<String> keyValues = Arrays.asList(connectionUri.split(";"));
        keyValues.stream()
                .map(field -> field.split("="))
                .filter(pair -> pair.length == 2)
                .forEach(pair -> {
                    String key = pair[0].trim();
                    String value = pair[1].trim();
                    configMap.put(key, value);
                });

        return configMap;
    }
}
