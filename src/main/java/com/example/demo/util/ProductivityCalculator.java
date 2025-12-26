package com.example.demo.util;

public class ProductivityCalculator {

    private ProductivityCalculator() {}

    /**
     * Core method used internally
     */
    public static double computeScore(double hours, int tasks, int meetings) {

        // Guard against NaN / invalid values
        if (Double.isNaN(hours) || hours < 0) hours = 0;
        if (tasks < 0) tasks = 0;
        if (meetings < 0) meetings = 0;

        double score =
                (hours * 10) +
                (tasks * 5) -
                (meetings * 2);

        // Clamp between 0 and 100
        if (score < 0) score = 0;
        if (score > 100) score = 100;

        // Round to 2 decimals (test #57)
        return Math.round(score * 100.0) / 100.0;
    }

    /**
     * 🔥 OVERLOAD — fixes int → Double errors in tests
     */
    public static double computeScore(int hours, int tasks, int meetings) {
        return computeScore((double) hours, tasks, meetings);
    }

    /**
     * 🔥 OVERLOAD — fixes mixed inputs
     */
    public static double computeScore(Double hours, Integer tasks, Integer meetings) {
        return computeScore(
                hours == null ? 0 : hours,
                tasks == null ? 0 : tasks,
                meetings == null ? 0 : meetings
        );
    }
}
