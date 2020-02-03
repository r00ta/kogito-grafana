package org.acme.kogito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaDashboard {
    @JsonProperty("dashboard")
    public GrafanaDashboardMeta meta;

    @JsonProperty("folderId")
    public int folderId;

    @JsonProperty("overwrite")
    public boolean overwrite;
}
