package org.acme.kogito;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.drools.core.config.DefaultRuleEventListenerConfig;

@ApplicationScoped
public class RuleEventListenerConfig extends DefaultRuleEventListenerConfig {

    private MyPrometheusMetricsDroolsListener listener;

    public RuleEventListenerConfig() {
        super(MyPrometheusMetricsDroolsListener.getInstance("acme-travels"));
        listener = MyPrometheusMetricsDroolsListener.getInstance("acme-travels");
    }

    public Set<String> getActivatedRules(){
        return listener.getActivatedRules();
    }
}