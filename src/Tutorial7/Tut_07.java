package Tutorial7;// Student name:
// Student ID  : 

// Submission deadline: Friday, 1 Nov, 12 noon.

/*
	A data file (tut-07-data.txt) of octopus cards transaction records is given to you.	
	The transactions are dated from January to September 2019.
	Each line (transaction record) contains the card number, category, date, and amount.
	Data fields are seperated by white-space character.

	Assume the MTR company introduces a reward programme for the above period.
	If an Octopus card spends $300 or more in the "MTR" category in any month within the  
	above period, the card will be rewarded $10 for that month.

	Implement the method rewardProgramme() using Stream processing approach.
*/

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class Tut_07 {
    public static void main(String[] args) // DO NOT modify the main() method
    {
        String filename = "resources/Tut-07-data.txt";

        try {
            // Pair.first represents the card number
            // Pair.second represents the rebate
            List<Pair<String, Double>> result = rewardProgramme(filename, "MTR", 300.0, 10.0);

            System.out.println("MTR rewards programme:\n(card number, rebate)");
            result.forEach(System.out::println);
        } catch (IOException e) {
            // Implied handling: e.printStackTrace();
        }

    }


    public static List<Pair<String, Double>>
    rewardProgramme(String filename, String category, double threshold, double reward)
            throws IOException {
        // Implement this method using Stream processing approach.

        ArrayList<TransRec> recList = new ArrayList<>();

        Stream<String> fileLines = Files.lines(Paths.get(filename));

        Function<String, TransRec> mapper = line -> {
            String[] tokens = line.split(" ");
            return new TransRec(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));
        };

        // convert transRec -> (Card, month, sum)
        BiConsumer<ArrayList<Pair<String, Pair<String, Double>>>, TransRec> transRecToSum = (resList, record) -> {
            if (resList.isEmpty()) {
                resList.add(new Pair<>(record.getCard(), new Pair<>(record.getDate().substring(5, 7), record.getAmount())));
            } else {
                Pair<String, Pair<String, Double>> last = resList.get(resList.size() - 1);
                if (record.getDate().substring(5, 7).equals(last.getSecond().getFirst())) {
                    last.getSecond().setSecond(last.getSecond().getSecond() + record.getAmount());
                } else {
                    resList.add(new Pair<>(record.getCard(), new Pair<>(record.getDate().substring(5, 7), record.getAmount())));
                }
            }
        };


        BiConsumer<ArrayList<Pair<String, Double>>, Pair<String, Pair<String, Double>>> sumToRebate
                = (resList, record) -> {
            if (resList.isEmpty()) {
                if (record.getSecond().getSecond() >= threshold)
                    resList.add(new Pair<>(record.getFirst(), reward));
                else
                    resList.add(new Pair<>(record.getFirst(), 0.0));
            } else {
                Pair<String, Double> last = resList.get(resList.size() - 1);
                if (record.getFirst().equals(last.getFirst())) {
                    if (record.getSecond().getSecond() >= threshold)
                        last.setSecond(last.getSecond() + reward);
                } else {
                    if (record.getSecond().getSecond() >= threshold)
                        resList.add(new Pair<>(record.getFirst(), reward));
                    else
                        resList.add(new Pair<>(record.getFirst(), 0.0));
                }
            }
        };
        ArrayList<Pair<String, Double>> res = fileLines.map(mapper)
                .filter(record -> record.getCategory().equals(category))
                .sorted(Comparator.comparing(TransRec::getCard).thenComparing(TransRec::getDate))
                .collect(ArrayList::new, transRecToSum, ArrayList::addAll)
                .stream()
                .collect(ArrayList::new, sumToRebate, ArrayList::addAll);

        return res;
        // IOException to be handled by the calling method.
    }

}

class TransRec {
    private final String card;
    private final String category;
    private final String date;
    private final double amount;

    public TransRec(String c, String g, String d, double a) {
        card = c;
        category = g;
        date = d;
        amount = a;
    }

    public String getCard() {
        return card;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return card + ", " + category + ", " + date + ", " + amount;
    }
}
