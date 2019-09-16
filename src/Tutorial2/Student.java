package Tutorial2;// Student name:
// Student ID  :

// Submission deadline: Friday, 20 Sept, 12 noon.
// Upload 2 files to Canvas, Student.java and Course.java

// Implement methods getCreditCompleted() and getGPA()

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Student {
    private final String name;
    private final String sid;
    private final String major;
    private final ArrayList<Grade> gradeList;

    public Student(String n, String s, String m) {
        name = n;
        sid = s;
        major = m;
        gradeList = new ArrayList();
    }

    public void putGrade(Grade g) {
        gradeList.add(g);
    }

    public void print() {
        System.out.println("Student Information:");
        System.out.println("Name: " + name);
        System.out.println("Student ID: " + sid);
        System.out.println("Major: " + major);
        System.out.println("Grades:");
        for (Grade g : gradeList)
            System.out.println(g);
    }

    // ------------------------------- methods to be implemented by student

    /**
     * @return the number of credits that have been completed by the Student
     */
    public int getCreditEarned() {
        int credit = 0;

        // Your codes
        gradeList.sort(Comparator.comparing(Grade::getCourseCode).thenComparing(Grade::getSem));
        int i = 0;
        while(i < gradeList.size()) {
            int j = i + 1;

            while(j < gradeList.size() && gradeList.get(j).getCourseCode().equals(gradeList.get(i).getCourseCode()))
                j++;

            Grade g = gradeList.get(j - 1);

            if(g.getGrade().charAt(0) >= 'A' && g.getGrade().charAt(0) <= 'D' || g.getGrade().charAt(0) == 'P') {
                Course c = Course.getCourse(g.getCourseCode());
                if(c != null)
                    credit += c.getCredit();
            }
            i = j;
        }

        return credit;
    }

    /**
     * @return the Grade Point Average of the Student
     */
    public double getGPA() {
        double gpa = 0;

        // Your codes
        gradeList.sort(Comparator.comparing(Grade::getCourseCode).thenComparing(Grade::getSem));
        int credits = 0;
        int i = 0;
        while(i < gradeList.size()) {
            int j = i + 1;

            while(j < gradeList.size() && gradeList.get(j).getCourseCode().equals(gradeList.get(i).getCourseCode()))
                j++;

            Grade g = gradeList.get(j - 1);

            Course c = Course.getCourse(g.getCourseCode());
            if(c != null) {
                if(!c.isNoGPA() && g.getGrade().charAt(0) >= 'A' && g.getGrade().charAt(0) <= 'F') {
                    credits += c.getCredit();
                    gpa += Grade.gradePoint(g.getGrade()) * c.getCredit();
                }
            }
            i = j;
        }

        return gpa / credits;
    }
}
