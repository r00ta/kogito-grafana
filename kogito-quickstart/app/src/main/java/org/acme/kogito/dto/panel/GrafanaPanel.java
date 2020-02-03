package org.acme.kogito.dto.panel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaPanel {

    @JsonProperty("type")
    public String type;

    @JsonProperty("title")
    public String title;

    @JsonProperty("gridPos")
    public GrafanaGridPos gridPos;

    @JsonProperty("id")
    public int id;

    @JsonProperty("mode")
    public String mode;

    @JsonProperty("content")
    public String content;

    @JsonProperty("lines")
    public boolean lines;

    @JsonProperty("targets")
    public List<GrafanaTarget> targets = new ArrayList<GrafanaTarget>();
}