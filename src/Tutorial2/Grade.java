package Tutorial2;// No need to modify this class

public class Grade 
{
    private final String courseCode;
    private final String grade;
    private final String sem;
    
    public Grade(String c, String g, String s)
    {
        courseCode = c;
        grade = g;
        sem = s;
    }
    
    public String getCourseCode()
    {
        return courseCode;
    }
    
    public String getGrade()
    {
        return grade;
    }
    
    public String getSem()
    {
        return sem;
    }
    
    @Override
    public String toString()
    {
        return courseCode + "\t" + grade + "\t" + sem;
    }
    
    public static double gradePoint(String letterGrade)
    {
        double gp = 0;
        
        if (letterGrade.charAt(0) >= 'A' && letterGrade.charAt(0) <= 'D')
        {
            gp = 'E' - letterGrade.charAt(0);
            if (letterGrade.length() > 1)
                if (letterGrade.charAt(1) == '+')
                    gp += 0.3;
                else if (letterGrade.charAt(1) == '-')
                    gp -= 0.3;            
        }
        return gp;
    }    
}
