package Tutorial3;// Student name: Wu Kwun Yu
// Student ID  : 54845500

// Submission deadline: Wednesday, 2 Oct, 12 noon.
// Complete the implementation of class Applicant and class Programme.
// Add your own methods where appropriate.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class JUPAS_Alloc {
    public static void main(String[] args)  // No need to modify the main class
    {
        String filename1 = "resources/JUPAS-test1.txt";
        String filename2 = "resources/JUPAS-test2.txt";

        test(filename1);
        System.out.println("\n---------------------------------------");
        test(filename2);
    }

    private static void test(String filename) {
        System.out.println("Data file : " + filename);
        Applicant.clear();
        Programme.clear();
        readDataFile(filename);
        Programme.allocation();
        Programme.printAllocSummary();
        Applicant.printAllocResult();
    }

    private static void readDataFile(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            String line;
            String[] tokens;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.length() > 0) {
                    if (line.charAt(0) == 'a' || line.charAt(0) == 'A') {
                        tokens = line.split("\\s+");
                        int aid = Integer.parseInt(tokens[1]);
                        ArrayList<Integer> list = new ArrayList();

                        line = sc.nextLine();
                        tokens = line.split(",");
                        for (int i = 0; i < tokens.length; i++)
                            list.add(new Integer(tokens[i]));

                        Applicant.add(new Applicant(aid, list));
                    } else if (line.charAt(0) == 'p' || line.charAt(0) == 'P') {
                        tokens = line.split("\\s+");
                        int progCode = Integer.parseInt(tokens[1]);
                        int quota = Integer.parseInt(tokens[2]);
                        int count = Integer.parseInt(tokens[3]);
                        ArrayList<Integer> list = new ArrayList();

                        for (int i = 0; i < count; i++)
                            list.add(sc.nextInt());

                        Programme.add(new Programme(progCode, quota, list));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(filename + " : File not found");
            System.exit(0);
        }
    }
}

// ----------------------------------------------- Applicant

class Applicant implements Comparable<Applicant> {
    // The applicant list is sorted by aid in ascending order.
    private static ArrayList<Applicant> applicants = new ArrayList();

    private int aid;  // applicant ID
    private Programme offer;
    private int offerPriority;  // priority of the offered programme
    ArrayList<Integer> choices; // Program codes in descending priority

    public Applicant(int id, ArrayList<Integer> list) {
        aid = id;
        choices = list;
        offer = null;
        offerPriority = 999;
    }

    @Override
    public int compareTo(Applicant other) {
        return aid - other.aid;
    }

    public int getAid() {
        return aid;
    }

    public Programme getOffer() {
        return offer;
    }

    @Override
    public String toString() {
        String s = "Applicant " + aid + ", choices : ";
        for (Integer i : choices)
            s += i + ", ";
        return s;
    }

    public static void clear() {
        applicants.clear();
    }

    public static void add(Applicant a) {
        applicants.add(a);
    }

    public static void printAllocResult() {
        System.out.println("Allocation results of individual applicants:");
        for (Applicant a : applicants) {
            if (a.offer != null)
                System.out.println("Applicant " + a.aid + " allocated to Programme " + a.offer.getProgCode());
            else
                System.out.println("Applicant " + a.aid + " not allocated to any Programme");
        }
    }

    // ---------------------- place your methods here
    // public Type yourMethod(...)
    // ----------------------------------------------
    public int getProgrammePriority(int progCode) {
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i) == progCode) {
                return i;
            }
        }

        return 999;
    }

    public int getOfferPriority() {
        return offerPriority;
    }

    public void setOffer(Programme offer) {
        this.offer = offer;
        offerPriority = getProgrammePriority(offer.getProgCode());
    }

    public static Applicant getApplicant(int aid) {
        int res = Collections.binarySearch(applicants, new Applicant(aid, null));

        if (res >= 0)
            return applicants.get(res);
        else
            return null;
    }
}

// ----------------------------------------------- Programme

class Programme {
    private static ArrayList<Programme> programmes = new ArrayList();
    private static ArrayList<Programme> masterList = new ArrayList();

    private int progCode;
    private int quota;
    private int vacancy;
    private int ref;
    private ArrayList<Integer> candidates;  // list of applicant ID    

    public Programme(int p, int v, ArrayList<Integer> list) {
        progCode = p;
        ref = 0;
        quota = vacancy = v;
        candidates = list;
    }

    public int getVacancy() {
        return vacancy;
    }

    public int getProgCode() {
        return progCode;
    }

    @Override
    public String toString() {
        return "Programme " + progCode + ", quota = " + quota + ", vacancy = " + vacancy;
    }

    public static void printAllocSummary() {
        System.out.println("Programmes summary:");
        for (Programme p : masterList)
            System.out.println(p);
        System.out.println();
    }

    public static void clear() {
        programmes.clear();
        masterList.clear();
    }

    public static void add(Programme p) {
        programmes.add(p);
    }

    public static void allocation() {
        masterList.addAll(programmes); // save a copy of references to all programmes

        // In this method, the working list of programmes can be modified.
        // Your codes.

        int i = 0;
        while (!Programme.isAllocationCompleted()) {
            i %= programmes.size();
            Programme p = programmes.get(i);
            int j = 0;
            int e = p.getNumOfCandidates();

            for (; j < e; j++) {
                if (p.getVacancy() == 0 || p.getRef() >= e) {
                    programmes.remove(p);
                    break;
                }

                Applicant a = Applicant.getApplicant(p.getCandidates(j));
                if (a != null) {
                    if (a.getOffer() != null) {

                        int progPriority = a.getProgrammePriority(p.getProgCode());
                        int offeredPriority = a.getOfferPriority();

                        if (progPriority < offeredPriority) {
                            Programme currentOffer = a.getOffer();
                            currentOffer.incVacancy();

                            a.setOffer(p);
                            p.decVacancy();
                            programmes.add(currentOffer);
                        }
                    } else {
                        p.decVacancy();
                        a.setOffer(p);
                    }

                    p.setRef(j + 1);
                    programmes.set(i, p);
                }
            }

            i++;
        }
    }

    // ---------------------- place your methods here
    // public Type yourMethod(...)
    // ----------------------------------------------

    public static boolean isAllocationCompleted() {
        return programmes.isEmpty();
    }

    public int getRef() {
        return this.ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public void decVacancy() {
        vacancy--;
    }

    public void incVacancy() {
        vacancy++;
    }

    public int getNumOfCandidates() {
        return candidates.size();
    }

    public int getCandidates(int index) {
        return candidates.get(index);
    }

    public void removeCandidates(Integer candidate) {
        candidates.remove(candidate);
    }

}