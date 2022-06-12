package com.movsisyan.repository;

import com.movsisyan.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StudentRepository {
    private Map<Integer, ArrayList<Student>> studentsFull = new HashMap<>();
    private ArrayList<Student> studentsDistance = new ArrayList<>();

    /**
     * 1.	Разработать схему данных и коллекций, произвести загрузку данных студентов в них таким образом,
     * что очники сгруппированы по курсу, а заочники не сгруппированы
     */
    private void load(String name) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(name))) {
            while (bufferedReader.ready()) {
                try {
                    String s = bufferedReader.readLine();
                    String[] split = s.split(",");
                    try {
                        Student student = new Student(split[0], split[1], Integer.parseInt(split[2]));
                        String[] strings = Arrays.copyOfRange(split, 3, split.length);
                        Integer[] integers = Arrays.stream(strings).map(Integer::parseInt).toArray(Integer[]::new);
                        student.addGrade(integers);
                        ArrayList<Student> studentArrayList = studentsFull.getOrDefault(Integer.parseInt(split[2]), new ArrayList<>());
                        studentArrayList.add(student);
                        studentsFull.put(Integer.parseInt(split[2]), studentArrayList);
                    } catch (NumberFormatException e) {
                        Student student = new Student(split[0], split[1], Integer.parseInt(split[2]), split[3]);
                        studentsDistance.add(student);
                    }
                } catch (RuntimeException ignored) {
                }
            }
        }
    }

    public StudentRepository(String name) throws IOException {
        load(name);
    }

    /**
     * 3.Вернуть студентов, которые являются отличниками
     */
    public ArrayList<Student> coolestStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Student>> entry : studentsFull.entrySet()) {
            for (Student student : entry.getValue()) {
                boolean isBest = true;
                for (Integer grades : student.getGrades()) {
                    if (grades != 5) {
                        isBest = false;
                        break;
                    }
                }
                if (isBest) {
                    students.add(student);
                }
            }
        }
        return students;
    }

    /**
     * 4.Отсортировать коллекцию студентов-заочников в лексикографическом порядке по фамилии и имени
     */
    public ArrayList<Student> sort() {
       this.studentsDistance.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.getSurName().compareTo(o2.getSurName()) == 0) {
                    return o1.getName().compareTo(o2.getName());
                }
                return o1.getSurName().compareTo(o2.getSurName());
            }
        });
       return this.studentsDistance;
    }

    /**
     * 5.Отсортировать коллекцию студентов-очников по количеству людей из каждого курса
     */
    public ArrayList<Map.Entry<Integer, ArrayList<Student>>> sortByCourse() {
        ArrayList<Map.Entry<Integer, ArrayList<Student>>> students = new ArrayList<>(studentsFull.entrySet());
        students.sort(new Comparator<Map.Entry<Integer, ArrayList<Student>>>() {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Student>> o1, Map.Entry<Integer, ArrayList<Student>> o2) {
                return Integer.compare(o1.getValue().size(), o2.getValue().size());
            }
        });
        return students;
    }


    @Override
    public String toString() {
        return "StudentRepository{" +
                "studentsFull=" + studentsFull +
                ", studentsDistance=" + studentsDistance +
                '}';
    }
}
