package com.movsisyan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@NoArgsConstructor
public class Student {
    private String name;
    private String surName;
    private int course;
    private String address;
    private ArrayList<Integer> grades;

    public Student(String name, String surName, int course, String address) {
        this.name = name;
        this.surName = surName;
        this.course = course;
        this.address = address;
    }

    public Student(String name, String surName, int course) {
        this.name = name;
        this.surName = surName;
        this.course = course;
        this.grades = new ArrayList<>();
    }

    public void addGrade(Integer... n) {
        this.grades.addAll(Arrays.asList(n));
    }
}
