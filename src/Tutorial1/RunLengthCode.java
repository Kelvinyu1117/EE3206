// Student name: Wu Kwun Yu
// Student ID  : 54845500

// Submission deadline: Friday, 13 Sept 2019, 12 noon.
// Upload your Java file to Canvas.
// Late submission or submission by other means will not be graded.

// Simplicity, clarity, and efficiency of the program are taken into consideration
// in the grading.

/*
    Your task is to implement the compress() and expand() methods in 
    the class RunLengthCode using the revised RLE. 

    In this exercise, you may make use of the class StringBuilder.
    StringBuilder object is mutable. It is more convenient and efficient to use
    a StringBuilder object to store the intermediate result of a computation.

    You can use the append() method to append a char, a string, or an integer 
    to a StringBuilder object.

    You can use the toString() method to produce a String object from a 
    StringBuffer object.

    You may need to use the following methods of the String class
       int length()
       String substring(int, int) 
       char charAt(int)

    To check if a char is a digit, you can use the static method
    Character.isDigit(char)

    To convert a string of digits to an integer value, you can use the static 
    method Integer.parseInt(String)
*/

public class RunLengthCode {
    public static void main(String[] args) {
        // test data
        String msg1 = "000000101111101000000000010";
        String msg2 = "1111111111110111111111111000111111111111111111111111011111111111111";
        String rleMsg1 = "OO6IOII5OIOO10IO";
        String rleMsg2 = "II12OII12OO3II24OII14";

        test(1, msg1, rleMsg1);
        test(2, msg2, rleMsg2);
    }

    public static void test(int n, String msg, String codedMsg) {
        System.out.println("Test " + n + ": ");
        String temp = compress(msg);
        System.out.println("compressed message");
        System.out.println(temp);
        if (!temp.equals(codedMsg))
            System.out.println("  *** Error in the compress method");

        temp = expand(codedMsg);
        System.out.println("expanded message");
        System.out.println(temp);
        if (!temp.equals(msg))
            System.out.println("  *** Error in the expand method");
        System.out.println();
    }

    // ------------------------------------------------- methods to be implemented by you

    // Compress the input message to RLE encoded message.
    // The input message is a sequence of '0' and '1'.
    // We use the letter 'O' to represent '0' and 'I' to represent '1' in the encoded message.
    public static String compress(String msg) {
        StringBuilder buffer = new StringBuilder();

        // Your codes
        int i = 0;
        while (i < msg.length()) {
            if (msg.charAt(i) == '0') {
                if (i + 1 < msg.length() && msg.charAt(i + 1) == '0') {
                    int cnt = 2;
                    int j = i + 2;

                    while (j < msg.length() && msg.charAt(j) == '0') {
                        j++;
                        cnt++;
                    }
                    i = j;

                    buffer.append("OO");
                    buffer.append(cnt);
                } else {
                    buffer.append("O");
                    i++;
                }
            } else {
                if (i + 1 < msg.length() && msg.charAt(i + 1) == '1') {
                    int cnt = 2;
                    int j = i + 2;

                    while (j < msg.length() && msg.charAt(j) == '1') {
                        j++;
                        cnt++;
                    }
                    i = j;

                    buffer.append("II");
                    buffer.append(cnt);
                } else {
                    buffer.append("I");
                    i++;
                }
            }
        }
        return buffer.toString();
    }

    // Expand the RLE encoded message to original message.
    // The original message is a sequence of '0' and '1'.
    public static String expand(String codedMsg) {
        StringBuilder buffer = new StringBuilder();

        // Your codes
        int i = 0;

        while (i < codedMsg.length()) {
            if (codedMsg.charAt(i) == 'O') {
                if (i + 1 < codedMsg.length() && codedMsg.charAt(i + 1) == 'O') {

                    int j = i + 2;
                    StringBuilder strNum = new StringBuilder();

                    while (j < codedMsg.length() && Character.isDigit(codedMsg.charAt(j))) {
                        strNum.append(codedMsg.charAt(j++));
                    }

                    i = j;

                    int num = Integer.parseInt(strNum.toString());

                    for (int k = 0; k < num; k++) {
                        buffer.append('0');
                    }

                } else {
                    buffer.append('0');
                    i++;
                }

            } else {
                if (i + 1 < codedMsg.length() && codedMsg.charAt(i + 1) == 'I') {
                    int j = i + 2;
                    StringBuilder strNum = new StringBuilder();

                    while (j < codedMsg.length() && Character.isDigit(codedMsg.charAt(j))) {
                        strNum.append(codedMsg.charAt(j++));
                    }

                    i = j;

                    int num = Integer.parseInt(strNum.toString());

                    for (int k = 0; k < num; k++) {
                        buffer.append('1');
                    }

                } else {
                    buffer.append('1');
                    i++;
                }
            }
        }
        return buffer.toString();
    }
}
