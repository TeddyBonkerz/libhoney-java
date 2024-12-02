package io.honeycomb.libhoney.examples;

import io.honeycomb.libhoney.EventFactory;
import io.honeycomb.libhoney.HoneyClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.honeycomb.libhoney.LibHoney.create;
import static io.honeycomb.libhoney.LibHoney.options;

public class UsingEventFactory {
    public static HoneyClient initializeGlobalClient() {
        int globalSampleRate = 10;
        Map<String, Object> globalDataMap = new HashMap<>();
        globalDataMap.put("cpuCores", Runtime.getRuntime().availableProcessors());
        globalDataMap.put("appProperty", System.getProperty("app.property"));

        return create(options()
            .setWriteKey("EQJ7cfhlof7O5P4DiP6KGN")
            .setDataset("MyCodeExerciseDataset")
            .setGlobalFields(globalDataMap)
            .setSampleRate(globalSampleRate)
            .build()
        );
    }

    public static void main(String... args) throws URISyntaxException {
        try (HoneyClient honeyClient = initializeGlobalClient()) {
            UserService service = new UserService(honeyClient);
            service.sendEvent("nile");
            service.sendEvent("Drap");
            service.sendEvent("kewl");
            service.sendMarker(honeyClient);
        }
    }

    static class UserService {
        private final EventFactory localBuilder;

        UserService(HoneyClient libHoney) {
            int serviceLevelSampleRate = 2;
            localBuilder = libHoney.buildEventFactory()
                .addField("serviceName", "userService")
                .setSampleRate(serviceLevelSampleRate)
                .build();
        }

        void sendEvent(String username) {
            localBuilder
                .createEvent()
                .addField("userName", username)
                .addField("userId", UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .send();
        }

        void sendMarker(HoneyClient honeyClient) throws URISyntaxException {
//            honeyClient.createMarker()
//                .addField("start_time", System.currentTimeMillis() - 1L)
//                .addField("end_time", System.currentTimeMillis())
//                .addField("message", "hey")
//                .addField("type", "deploy")
//                .addField("url", "https://marker.com")
            localBuilder
                .createEvent()
                .setApiHost(new URI("https://api.honeycomb.io/1/markers"))
                .addField("start_time", System.currentTimeMillis() - 1L)
                .addField("end_time", System.currentTimeMillis())
                .addField("message", "hey")
                .addField("type", "deploy")
                .addField("url", "https://marker.com")
                .send();
        }
    }
}
