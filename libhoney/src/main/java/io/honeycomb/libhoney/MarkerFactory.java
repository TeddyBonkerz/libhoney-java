package io.honeycomb.libhoney;

import java.net.URI;
import java.util.Map;

public class MarkerFactory {
    private final HoneyClient client;
    private final URI apiHost;
    private final String writeKey;
    private final String dataset;
    private final Map<String, Object> fields;

    MarkerFactory(HoneyClient client, URI apiHost, String writeKey, String dataset, Map<String, Object> fields) {
        this.client = client;
        this.apiHost = apiHost;
        this.writeKey = writeKey;
        this.dataset = dataset;
        this.fields = fields;
    }

    MarkerFactory(HoneyClient honeyClient, Options options) {
        this(honeyClient, options.getApiHost(), options.getWriteKey(), options.getDataset(), options.getGlobalFields());
    }

    public Marker createMarker() {
        return new Marker(client, apiHost, writeKey, dataset, fields);
    }

    public void send(final Map<String, ?> fields) {
        createMarker().addFields(fields).send();
    }
}
