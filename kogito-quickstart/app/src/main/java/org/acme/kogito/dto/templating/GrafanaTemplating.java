package org.acme.kogito.dto.templating;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaTemplating {
    @JsonProperty("enable")
    public boolean enable;

    @JsonProperty("list")
    public List<GrafanaTemplate> list;
}