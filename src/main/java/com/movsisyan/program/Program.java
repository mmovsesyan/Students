package com.movsisyan.program;

import com.movsisyan.repository.StudentRepository;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            StudentRepository repository = new StudentRepository("students.csv");
            System.out.println(repository);
            System.out.println(repository.coolestStudents());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
