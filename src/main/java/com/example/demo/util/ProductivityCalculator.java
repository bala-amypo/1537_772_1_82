package com.example.demo.util;

public class ProductivityCalculator {

    private ProductivityCalculator() {}

    public static double computeScore(double hours, int tasks, int meetings) {

        hours = Math.max(hours, 0);
        tasks = Math.max(tasks, 0);
        meetings = Math.max(meetings, 0);

        double score = (hours * 10) + (tasks * 5) + (meetings * 2);

        return Math.max(0.0, Math.min(100.0, score));
    }
}
