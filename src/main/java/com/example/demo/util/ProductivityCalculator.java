package com.example.demo.util;

public final class ProductivityCalculator {

    private ProductivityCalculator() {}

    public static double computeScore(
            Double hoursLogged,
            Integer tasksCompleted,
            Integer meetingsAttended) {

        if (hoursLogged == null || tasksCompleted == null || meetingsAttended == null) {
            return 0.0;
        }

        if (Double.isNaN(hoursLogged)
                || hoursLogged < 0
                || tasksCompleted < 0
                || meetingsAttended < 0) {
            return 0.0;
        }

        double score =
                (hoursLogged * 10)
              + (tasksCompleted * 5)
              - (meetingsAttended * 2);

        if (score < 0) score = 0;
        if (score > 100) score = 100;

        return Math.round(score * 100.0) / 100.0;
    }
}
