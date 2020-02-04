package org.acme.kogito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.acme.kogito.api.Grafana;

public class GrafanaDashboard {
    @JsonProperty("dashboard")
    public GrafanaDashboardMeta meta;

    @JsonProperty("folderId")
    public int folderId;

    @JsonProperty("overwrite")
    public boolean overwrite;

    public GrafanaDashboard(int folderId, boolean overwrite){
        this.folderId = folderId;
        this.overwrite = overwrite;
    }
}
