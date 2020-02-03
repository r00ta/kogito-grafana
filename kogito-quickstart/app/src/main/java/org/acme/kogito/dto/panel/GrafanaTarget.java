package org.acme.kogito.dto.panel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaTarget {
    @JsonProperty("expr")
    public String expr;

    @JsonProperty("format")
    public String format;

    @JsonProperty("intervalFactor")
    public int intervalFactor = 2;

    @JsonProperty("refId")
    public String refId;

    public GrafanaTarget(String expr, String format, int intervalFactor, String refId){
        this.expr = expr;
        this.format = format;
        this.intervalFactor = intervalFactor;
        this.refId = refId;
    }
}