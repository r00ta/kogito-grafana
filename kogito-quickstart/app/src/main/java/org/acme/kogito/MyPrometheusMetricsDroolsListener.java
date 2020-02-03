package org.acme.kogito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.prometheus.client.Histogram;
import org.drools.core.event.rule.impl.AfterActivationFiredEventImpl;
import org.drools.core.event.rule.impl.BeforeActivationFiredEventImpl;
import org.kie.addons.monitoring.rule.PrometheusMetrics;
import org.kie.addons.monitoring.rule.PrometheusMetricsDroolsListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;

public class MyPrometheusMetricsDroolsListener extends PrometheusMetricsDroolsListener {

    private String identifier;

    private Set<String> ruleSet = new HashSet<>();

    private static MyPrometheusMetricsDroolsListener single_instance = null;

    // static method to create instance of Singleton class
    public static MyPrometheusMetricsDroolsListener getInstance(String identifier)
    {
        if (single_instance == null)
            single_instance = new MyPrometheusMetricsDroolsListener(identifier);

        return single_instance;
    }

    public MyPrometheusMetricsDroolsListener(String identifier) {
        super(identifier);
        this.identifier = identifier;
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        AfterActivationFiredEventImpl afterImpl = this.getAfterImpl(event);
        BeforeActivationFiredEventImpl beforeImpl = this.getBeforeImpl(afterImpl.getBeforeMatchFiredEvent());
        long startTime = beforeImpl.getTimestamp();
        long elapsed = System.nanoTime() - startTime;
        String ruleName = event.getMatch().getRule().getName();
        ((Histogram.Child) PrometheusMetrics.getDroolsEvaluationTimeHistogram().labels(new String[]{this.identifier, ruleName})).observe((double)elapsed);
        ruleSet.add(ruleName);
    }

    public Set<String> getActivatedRules(){
        return ruleSet;
    };
}
