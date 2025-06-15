package org.researchandreview.projecttsbackend.util;

import java.lang.Math;

public class PerformanceManager {
    private final double alpha;

    public PerformanceManager(double alpha) {
        this.alpha = alpha;
    }

    public PerformanceManager() {
        this(1.5);
    }

    public double calcurateNewPerformance(double oldPerformance, double calcPerformance) {
        double performenaceDelta = calcPerformance - oldPerformance;
        double newPerformance = oldPerformance + performenaceDelta * Math.tanh( calcPerformance * this.alpha );
        return newPerformance;
    }
}
