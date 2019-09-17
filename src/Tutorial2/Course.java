package Tutorial2;// Student name:
// Student ID  :

// Submission deadline: Friday, 20 Sept, 12 noon.
// Upload 2 files to Canvas, Student.java and Course.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Course implements Comparable<Course> {
    private static final ArrayList<Course> courseList = new ArrayList();

    private final String courseCode;
    private final String courseTitle;
    private final int credit;
    private final boolean noGPA;

    public Course(String c, int u, boolean g, String t) {
        courseCode = c;
        courseTitle = t;
        credit = u;
        noGPA = g;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCredit() {
        return credit;
    }

    public boolean isNoGPA() {
        return noGPA;
    }

    @Override
    public int compareTo(Course other) {
        return courseCode.compareTo(other.courseCode);
    }

    @Override
    public String toString() {
        String g = noGPA ? "NoGPA" : "GPA";
        return courseCode + "\t" + credit + "\t" + g + "\t" + courseTitle;
    }

    public static void putCourse(Course c) {
        courseList.add(c);
    }

    // ------------------------------- method to be implemented by student

    public static Course getCourse(String courseCode) {
        // Implement this method using binary search.
        // Return the course with the given courseCode

        courseList.sort(Course::compareTo);

        int i = Collections.binarySearch(courseList, new Course(courseCode, 0, false, ""));
        if (i >= 0) {
            return courseList.get(i);
        } else {
            return null;  // dummy return statement
        }
    }
}
