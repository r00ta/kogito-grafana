package org.acme.kogito.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.acme.kogito.dto.GrafanaConfiguration;
import org.acme.kogito.dto.GrafanaDashboard;
import org.acme.kogito.grafana.IGrafanaManager;
import org.acme.kogito.responses.GrafanaNewDashboardResponse;

@Path("/grafana")
public class Grafana {

    @Inject
    IGrafanaManager grafanaConfigurator;

    @GET
    @Path("/getCurrentConfiguration")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentConfiguration(){
        return grafanaConfigurator.getCurrentConfiguration();
    }


    @POST
    @Path("/newDashboard")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCurrentConfiguration(GrafanaDashboard dashboard){
        try {
            return Response.ok(grafanaConfigurator.createNewDashboard(dashboard)).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("/startGrafana")
    @Produces(MediaType.TEXT_PLAIN)
    public String startGrafana(){
        grafanaConfigurator.createDefaultDashboard();
        return "Suca";
    }
}
