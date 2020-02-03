package org.acme.kogito.grafana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.scheduler.Scheduled;
import org.acme.kogito.RuleEventListenerConfig;
import org.acme.kogito.api.Grafana;
import org.acme.kogito.dto.GrafanaDashboard;
import org.acme.kogito.dto.GrafanaDashboardMeta;
import org.acme.kogito.dto.panel.GrafanaGridPos;
import org.acme.kogito.dto.panel.GrafanaPanel;
import org.acme.kogito.dto.panel.GrafanaTarget;
import org.acme.kogito.responses.GrafanaNewDashboardResponse;
import org.acme.kogito.util.HttpHelper;
import java.util.Random;

@Singleton
public class GrafanaManager implements IGrafanaManager {

    @Inject
    RuleEventListenerConfig listener;

    private Set<String> activatedRules;

    private HttpHelper httpHelper;

    private static final String BASEHOST = "http://grafana:3000";

    private ObjectMapper mapper = new ObjectMapper();

    private String dashboardId;
    private String dashboardUid;
    private String dashboardUrl;

    private GrafanaDashboard currentDashboard;

    @PostConstruct
    public void setUp() throws JsonProcessingException {
        activatedRules = new HashSet<String>();
        httpHelper = new HttpHelper(BASEHOST);
        GrafanaDashboard dashboard = new GrafanaDashboard();
        dashboard.folderId = 0;
        dashboard.overwrite = true;
        dashboard.meta = new GrafanaDashboardMeta();
        dashboard.meta.id = null;
        dashboard.meta.uid = null;
        dashboard.meta.title = "DRL Dashboard";
        dashboard.meta.tags = null;
        dashboard.meta.timezone = "browser";
        dashboard.meta.schemaVersion = 16;
        dashboard.meta.version = 0;
        System.out.println(mapper.writeValueAsString(dashboard));
        GrafanaNewDashboardResponse response = createNewDashboard(dashboard);
        dashboardId = response.id;
        dashboardUid = response.uid;
        dashboardUrl = response.url;
        currentDashboard = dashboard;
        System.out.println(mapper.writeValueAsString(response));
    }

    @Override
    public void createDefaultDashboard() {
        httpHelper.doPost("suca", null);
    }

    @Override
    public GrafanaNewDashboardResponse createNewDashboard(GrafanaDashboard req) throws JsonProcessingException {
        String param = mapper.writeValueAsString(req);
        currentDashboard = req;
        return mapper.readValue(httpHelper.doPost("/api/dashboards/db", param), GrafanaNewDashboardResponse.class);
    }

    @Override
    public String getCurrentConfiguration() {
        return httpHelper.doGet("/api/dashboards/home");
    }


    @Scheduled(every="10s")
    void updateGrafana() throws JsonProcessingException {
        Set<String> retrievedSet = listener.getActivatedRules();
        if (!retrievedSet.equals(activatedRules)){
            for(String rule : retrievedSet){
                if (!activatedRules.contains(rule)){
                    activatedRules.add(rule);
                    GrafanaPanel panel = new GrafanaPanel();
                    panel.id = new Random().nextInt();
                    panel.title = rule + " fire count";
                    panel.gridPos = new GrafanaGridPos(8 * (activatedRules.size() - 1), 12 * (activatedRules.size() - 1), 12, 8);
                    panel.targets.add(new GrafanaTarget(String.format("drl_match_fired_nanosecond_count{rule_name=\"%s\"}", rule), "time_series", 1, rule));
                    panel.type = "graph";
                    panel.lines = true;
                    System.out.println(currentDashboard.folderId);
                    System.out.println(currentDashboard.meta.panels);
                    currentDashboard.meta.panels.add(panel);
                }
            }
            createNewDashboard(currentDashboard);
        };
    }
}
