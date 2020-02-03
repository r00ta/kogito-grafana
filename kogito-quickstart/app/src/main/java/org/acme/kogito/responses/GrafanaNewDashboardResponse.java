package org.acme.kogito.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaNewDashboardResponse {
    @JsonProperty("id")
    public String id;

    @JsonProperty("uid")
    public String uid;

    @JsonProperty("url")
    public String url;

    @JsonProperty("status")
    public String status;

    @JsonProperty("version")
    public int version;

    @JsonProperty("slug")
    public String slug;
}