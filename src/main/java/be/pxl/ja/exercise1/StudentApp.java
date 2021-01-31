package be.pxl.ja.exercise1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentApp {
    public static void main(String[] args) {
        List<Student> students = StudentDao.createStudents();

        System.out.println("Studenten vandaag jarig");
        students.stream().filter(s -> s.getDateOfBirth().getMonth() == LocalDateTime.now().getMonth()
                && s.getDateOfBirth().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()).forEach(s -> System.out.println(s.getName()));
        System.out.println();

        System.out.println("Student Carol");
        students.stream().filter(s -> s.getName().equals("Carol")).forEach(System.out::println);
        System.out.println();

        System.out.println("Afgestudeerd in 2017");
        System.out.println(students.stream().filter(s -> s.getGraduationYear() == 2017).count());
        System.out.println();

        System.out.println("Hoogste score ooit");
        int highestScore = students.stream().mapToInt(Student::getScore).max().getAsInt();
        System.out.println(highestScore);
        System.out.println(students.stream().filter(s -> s.getScore() == highestScore).map(Student::getName).collect(Collectors.toList()));
        System.out.println();

        System.out.println("Oudste persoon");
        System.out.println(students.stream().min(Comparator.comparing(Student::getDateOfBirth)).get());
        System.out.println();

        System.out.println("Namen studenten");
        System.out.println(students.stream().map(Student::getName).collect(Collectors.joining(", ")));
        System.out.println();

        System.out.println("Jongste student afgestudeerd in 2018");
        System.out.println(students.stream()
                .filter(s -> s.getGraduationYear() == 2018)
                .max(Comparator.comparing(Student::getDateOfBirth))
                .get());
        System.out.println();

        System.out.println("Per afstudeerjaar de gemiddelde score");
        Map<Integer, Double> averageScorePerYear = students.stream()
                .collect(Collectors.groupingBy(Student::getGraduationYear, Collectors.averagingInt(Student::getScore)));
        System.out.println(averageScorePerYear);
        System.out.println();

        System.out.println("Sorteren per afstudeerjaar en score");
        students.stream()
                .sorted(Comparator.comparing(Student::getGraduationYear).thenComparing(Student::getScore).reversed())
                .forEach(s -> System.out.println(s.getName() + " - " + s.getGraduationYear() + " - " + s.getScore()));
    }
}
