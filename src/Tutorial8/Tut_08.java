package Tutorial8;// Student name:
// Student ID  : 

// Submission deadline: Friday, 8 Nov, 12 noon.

// Definition of Fibonacci number
//     Fibonacci(0) = 0
//     Fibonacci(1) = 1
//     Fibonacci(n) = Fibonacci(n-2) + Fibonacci(n-1) for n >= 2

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
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


        BigInteger[] base = {BigInteger.ZERO, BigInteger.ONE};
        List<BigInteger> list2 = Stream.iterate(base, FibonacciUtil::next)
                .limit(100)
                .map(g -> {
                    return g[0];
                })
                .collect(Collectors.toList());


        System.out.println("Fibonacci numbers produced by Stream.iterate()");
        for (int i = 0; i < 8; i++)
            System.out.println("Fibonacci(" + i + ") = " + list2.get(i));
        for (int i = 96; i < 100; i++)
            System.out.println("Fibonacci(" + i + ") = " + list2.get(i));


    }
}


class FibonacciUtil {
    private BigInteger[] f = {BigInteger.ZERO, BigInteger.ONE};
    private int i = 0;

    // Your codes
    public BigInteger next() {
        if (i == 0) {
            i++;
            return BigInteger.ZERO;
        } else if (i == 1) {
            i++;
            return BigInteger.ONE;
        } else {
            BigInteger[] res = next(f);
            f = res;
            return res[1];
        }
    }

    public static BigInteger[] next(BigInteger[] g) {
        BigInteger[] t = Arrays.copyOf(g, 2);
        BigInteger res = t[0].add(t[1]);
        t[0] = t[1];
        t[1] = res;
        return t;
    }
}

