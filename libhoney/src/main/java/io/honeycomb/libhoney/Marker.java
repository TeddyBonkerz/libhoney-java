package io.honeycomb.libhoney;

import lombok.Data;

import java.net.URI;
import java.util.Map;

@Data
public class Marker {
    private final HoneyClient client;
    private URI apiHost;
    private String writeKey;
    private String dataset;
    private final Map<String, Object> fields;

    Marker(final HoneyClient client,
           final URI apiHost,
           final String writeKey,
           final String dataset,
           final Map<String, Object> fields) {
        this.client = client;
        this.apiHost = apiHost;
        this.writeKey = writeKey;
        this.dataset = dataset;
        this.fields = fields;
    }

    Marker addField(String fieldKey, Object fieldValue) {
        this.fields.put(fieldKey, fieldValue);
        return this;
    }

    Marker addFields(final Map<String, ?> fields) {
        this.fields.putAll(fields);
        return this;
    }

    void send() {
        this.client.sendMarker(this);
    }
}
