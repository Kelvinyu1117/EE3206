package Tutorial2;// No need to modify this class

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tut_2 
{
    public static void main(String[] args) throws IOException
    {
        String file_c = "resources/course_list.txt";
        String file_s1 = "resources/student1_info.txt";  // credits earned = 24, GPA = 2.2
        String file_s2 = "resources/student2_info.txt";  // credits earned = 48, GPA = 3.31
        
        readCourse(file_c);
        processStudent(file_s1);
        processStudent(file_s2);        
    }
    
    private static void processStudent(String filename) throws IOException
    {
        Student st = readStudent(filename);
        System.out.println("---------------------------------------------");
        st.print();
        
        int creditEarned = st.getCreditEarned();        
        double gpa = st.getGPA();
        System.out.printf("\ncredits earned = %d, GPA = %.2f\n", creditEarned, gpa);
    }
    
    private static void readCourse(String filename) throws IOException
    {       
        Scanner sc = new Scanner(new File(filename));       
        System.out.println("Course list:");        
        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            String[] tokens = line.split("\\s+");
                        
            for (int i = 3; i < tokens.length; i++)
                tokens[3] = tokens[3] + " " + tokens[i];            
            
            boolean noGPA = tokens[2].equals("NoGPA");
            Course c = new Course(tokens[0], Integer.parseInt(tokens[1]), noGPA, tokens[3]);
            Course.putCourse(c);
            System.out.println(c);
        }
        System.out.println("");
    }
     
    private static Student readStudent(String filename) throws IOException
    {
        Scanner sc = new Scanner(new File(filename));
        String line = sc.nextLine();
        String[] tokens = line.split("\\s+");
        String name = tokens[0];
        for (int i = 1; i < tokens.length - 2; i++)
            name = name + " " + tokens[i];
        
        Student st = new Student(name, tokens[tokens.length-2], tokens[tokens.length-1]);
        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            tokens = line.split("\\s+");
            Grade g = new Grade(tokens[0], tokens[1], tokens[2]);
            st.putGrade(g);
        }
        return st;
    }
    
}
