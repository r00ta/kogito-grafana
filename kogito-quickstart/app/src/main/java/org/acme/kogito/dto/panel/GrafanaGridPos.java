package org.acme.kogito.dto.panel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaGridPos {
    @JsonProperty("x")
    public int x;

    @JsonProperty("y")
    public int y;

    @JsonProperty("w")
    public int w;

    @JsonProperty("h")
    public int h;

    public GrafanaGridPos(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
