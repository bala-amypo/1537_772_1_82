package com.example.demo.util;

public final class ProductivityCalculator {

    private ProductivityCalculator() {
        // Utility class — prevent instantiation
    }

    /**
     * Computes productivity score using the formula:
     * (hours * 10) + (tasks * 5) + (meetings * 2)
     * Result is clamped between 0 and 100.
     */
    public static double computeScore(
            Double hoursLogged,
            Integer tasksCompleted,
            Integer meetingsAttended) {

        double hours = hoursLogged != null ? hoursLogged : 0.0;
        int tasks = tasksCompleted != null ? tasksCompleted : 0;
        int meetings = meetingsAttended != null ? meetingsAttended : 0;

        double score = (hours * 10) + (tasks * 5) + (meetings * 2);

        if (score < 0) {
            return 0;
        }
        if (score > 100) {
            return 100;
        }
        return score;
    }
}
