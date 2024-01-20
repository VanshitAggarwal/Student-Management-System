import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
// Created By Vanshit Aggarwal
class Student implements Serializable {
    private int rollno, age;
    private String name;

    public Student(int stdId, String stdName, int stdAge) {
        rollno = stdId;
        age = stdAge;
        name = stdName;
    }

    public int getRollno() {
        return rollno;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void displayStudent() {
        JOptionPane.showMessageDialog(null, "Roll No: " + rollno + "\nName: " + name + "\nAge: " + age);
    }
}

class StudentManagementSystem2 extends JFrame {
    private ArrayList<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem2() {
        students = readFromFile(); // Load existing data from file

        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton addBtn = new JButton("Add new student");
        JButton displayBtn = new JButton("Display all students");
        JButton searchBtn = new JButton("Search student");
        JButton updateBtn = new JButton("Update student");
        JButton deleteBtn = new JButton("Delete student");
        JButton exitBtn = new JButton("Exit");

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewStudent();
            }
        });

        displayBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });

        panel.add(addBtn);
        panel.add(displayBtn);
        panel.add(searchBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(exitBtn);

        add(panel, BorderLayout.CENTER);
    }

    private void addNewStudent() {
        int rollno = Integer.parseInt(JOptionPane.showInputDialog("Enter Roll No: "));
        String name = JOptionPane.showInputDialog("Enter Name: ");
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age: "));

        Student newStudent = new Student(rollno, name, age);
        students.add(newStudent);

        JOptionPane.showMessageDialog(null, "Student added successfully!");

        writeToFile(); // Save updated data to file
    }

    private void displayAllStudents() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students found.");
        } else {
            StringBuilder message = new StringBuilder("All Students:\n");
            for (Student student : students) {
                message.append("Roll No: ").append(student.getRollno()).append(", Name: ")
                        .append(student.getName()).append(", Age: ").append(student.getAge()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }

    private void writeToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Student> readFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Student>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file not found or any other exception, return an empty ArrayList
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementSystem2().setVisible(true);
            }
        });
    }
}
