package Tutorial8;// Student name:
// Student ID  : 

// Submission deadline: Friday, 8 Nov, 12 noon.

// Definition of Fibonacci number
//     Fibonacci(0) = 0
//     Fibonacci(1) = 1
//     Fibonacci(n) = Fibonacci(n-2) + Fibonacci(n-1) for n >= 2

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tut_08 {
    public static void main(String[] args) {
        // Design your program to generate a stream of Fibonacci numbers
        // using Stream.generate().
        // Collect the fist 100 Fibonacci numbers in a List<BigInteger>.


        List<BigInteger> list1 = Stream.generate(new FibonacciUtil()::next)
                .limit(100)
                .collect(Collectors.toList());


        System.out.println("Fibonacci numbers produced by Stream.generate()");
        for (int i = 0; i < 8; i++)
            System.out.println("Fibonacci(" + i + ") = " + list1.get(i));
        for (int i = 96; i < 100; i++)
            System.out.println("Fibonacci(" + i + ") = " + list1.get(i));


        System.out.println("--------------------------------------------- ");

        // Design your program to generate a stream of Fibonacci numbers
        // using Stream.iterate().
        // Collect the fist 100 Fibonacci numbers in a List<BigInteger>.  


        List<BigInteger> list2 = Stream.iterate(new BigInteger("0"), i -> i.add(new BigInteger("1")))
                .limit(100)
                .map(FibonacciUtil::next)
                .collect(Collectors.toList());


        System.out.println("Fibonacci numbers produced by Stream.iterate()");
        for (int i = 0; i < 8; i++)
            System.out.println("Fibonacci(" + i + ") = " + list2.get(i));
        for (int i = 96; i < 100; i++)
            System.out.println("Fibonacci(" + i + ") = " + list2.get(i));


    }
}


class FibonacciUtil {
    private static BigInteger p;
    private static BigInteger q;
    private BigInteger i = new BigInteger("0");

    // Your codes
    public BigInteger next() {
        BigInteger res = next(i);
        i = i.add(new BigInteger("1"));
        return res;
    }

    public static BigInteger next(BigInteger after) {
        if (after.compareTo(new BigInteger("0")) == 0) {
            p = new BigInteger("0");
            return p;
        } else if (after.compareTo(new BigInteger("1")) == 0) {
            q = new BigInteger("1");
            return q;
        } else {
            BigInteger res = p.add(q);
            p = q;
            q = res;
            return res;
        }
    }
}
