package org.acme.kogito.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.kogito.dto.panel.GrafanaPanel;
import org.acme.kogito.dto.templating.GrafanaTemplating;
import org.acme.kogito.dto.time.GrafanaTime;
import org.acme.kogito.dto.time.GrafanaTimePicker;

public class GrafanaDashboardMeta {

    @JsonProperty("id")
    public String id;

    @JsonProperty("uid")
    public String uid;

    @JsonProperty("title")
    public String title;

    @JsonProperty("tags")
    public List<String> tags;

    @JsonProperty("style")
    public String style = "dark";

    @JsonProperty("timezone")
    public String timezone;

    @JsonProperty("editable")
    public boolean editable = true;

    @JsonProperty("hideControls")
    public boolean hideControls;

    @JsonProperty("graphTooltip")
    public boolean graphTooltip;

    @JsonProperty("panels")
    public List<GrafanaPanel> panels = new ArrayList<>();

    @JsonProperty("time")
    public GrafanaTime time;

    @JsonProperty("timepicker")
    public GrafanaTimePicker timepicker;

    @JsonProperty("templating")
    public GrafanaTemplating templating;

    @JsonProperty("refresh")
    public String refresh;

    @JsonProperty("schemaVersion")
    public int schemaVersion = 18;

    @JsonProperty("version")
    public int version;

    @JsonProperty("links")
    public List<String> links = new ArrayList<>(); //todo

    public GrafanaDashboardMeta(String id, String uid, String title, List<String> tags, String timezone, int schemaVersion, int version){
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.tags = tags;
        this.timezone = timezone;
        this.schemaVersion = schemaVersion;
        this.version = version;
    }
}