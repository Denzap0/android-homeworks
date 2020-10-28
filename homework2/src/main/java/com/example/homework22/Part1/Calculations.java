package com.example.homework22.Part1;

import java.util.ArrayList;

public class Calculations {

    public static double sumArithmetic(ArrayList<Integer> values) {
        return (double) sum(values) / values.size();
    }

    public static int sum(ArrayList<Integer> values) {

        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        return sum;
    }

    public static double func(ArrayList<Integer> values) {
        double sum = 0;
        for (int i = 0; i < values.size() / 2; i++) {
            sum += values.get(i);
        }
        double residual = values.get(values.size() / 2);
        for (int i = values.size() / 2 + 1; i < values.size(); i++) {
            residual -= values.get(i);
        }
        return sum / residual;
    }
}
