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

    private GrafanaDashboard currentDashboard;

    private int numOfPanel = 0;

    @PostConstruct
    public void setUp() throws JsonProcessingException {
        activatedRules = new HashSet<String>();
        httpHelper = new HttpHelper(BASEHOST);
        GrafanaDashboard dashboard = new GrafanaDashboard(0, true);
        dashboard.meta = new GrafanaDashboardMeta(null, null, "DRL Dashboard", null, "browser", 16, 0);
        System.out.println(mapper.writeValueAsString(dashboard));
        GrafanaNewDashboardResponse response = createNewDashboard(dashboard);
        currentDashboard = dashboard;
        System.out.println(mapper.writeValueAsString(response));
    }

    @Override
    public void createDefaultDashboard() {
        httpHelper.doPost("todo", null);
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
                    numOfPanel++;
                    activatedRules.add(rule);
                    GrafanaPanel panel = new GrafanaPanel(
                            new Random().nextInt(),
                            rule + " fire count",
                            "graph"
                    );
                    panel.gridPos = new GrafanaGridPos(12 * ( (numOfPanel - 1) % 2), 8 * ((numOfPanel - 1) / 2), 12, 8);
                    panel.targets.add(new GrafanaTarget(String.format("drl_match_fired_nanosecond_count{rule_name=\"%s\"}", rule), "time_series", 1, rule));
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
