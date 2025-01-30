package week2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

class Student {
    private String Name;
    private int Marks;
    private String Id;

    public Student(String name, String id, int marks) {
        this.Name = name;
        this.Id = id;
        this.Marks = marks;
    }

    public String Name() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String Id() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public int Marks() {
        return Marks;
    }

    public void setMarks(int marks) {
        this.Marks = marks;
    }

    public String display() {
        return "Student{" +
                "name='" + Name + '\'' +
                ", id='" + Id + '\'' +
                ", marks=" + Marks +
                '}';
    }
}

class Register {
    private static HashMap<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Sort Students by Marks");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = s.nextInt();
            s.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    addStudent(s);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent(s);
                    break;
                case 4:
                    deleteStudent(s);
                    break;
                case 5:
                    sortStudentsByMarks();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student marks: ");
        int marks = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        students.put(id, new Student(name, id, marks));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        for (Student student : students.values()) {
            System.out.println(student.display());
        }
    }

    private static void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine();
        if (students.containsKey(id)) {
            System.out.print("Enter new student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new student marks: ");
            int marks = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Student student = students.get(id);
            student.setName(name);
            student.setMarks(marks);
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        if (students.remove(id) != null) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void sortStudentsByMarks() {
        ArrayList<Student> studentList = new ArrayList<>(students.values());
        Collections.sort(studentList, Comparator.comparingInt(Student::Marks));
        for (Student student : studentList) {
            System.out.println(student.display());
        }
    }
}
