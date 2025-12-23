package com.example.demo.util;

public class ProductivityCalculator {

    public static double computeScore(double hours, int tasks, int meetings) {
        double score = (hours * 10) + (tasks * 5) + (meetings * 2);
        return Math.max(0, Math.min(100, score));
    }
}
