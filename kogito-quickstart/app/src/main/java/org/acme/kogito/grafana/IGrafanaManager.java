package org.acme.kogito.grafana;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.acme.kogito.dto.GrafanaDashboard;
import org.acme.kogito.responses.GrafanaNewDashboardResponse;

public interface IGrafanaManager {
    void createDefaultDashboard();

    GrafanaNewDashboardResponse createNewDashboard(GrafanaDashboard req) throws JsonProcessingException;

    String getCurrentConfiguration();
}
