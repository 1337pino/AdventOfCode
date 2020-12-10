import java.io.*;
import java.util.*;

public class Day1 {
    public static void main(String[] args) {
        // Declarations //////
        File expenseReport = new File(System.getProperty("user.dir") + "\\input.txt");
        List<Integer> complements = null, readNumbers = new ArrayList<Integer>();
        //////////////////////
        
        scanExpenseReport(expenseReport, readNumbers);

        System.out.println("---------- DAY 1 2020 ----------");

        // Finding two numbers that add up 2020 and their product
        complements = findComplements(readNumbers, new ArrayList<Integer>(), 0, 2);
        System.out.println("Part 1 (two numbers that add up to 2020): " 
            + ((complements != null) ? calculateProduct(complements) 
                                     : "no two numbers add up to 2020!!"));

        // Finding three numbers that add up 2020 and their product
        complements = findComplements(readNumbers, new ArrayList<Integer>(), 0, 3);
        System.out.println("Part 2 (three numbers that add up to 2020): " 
            + ((complements != null) ? calculateProduct(complements) 
                                     : "no three numbers add up to 2020!!"));
    }

    /**
     * Scans the Expense Report and loads all of the integers into a list
     * @param inputFile text file for the Expense Report
     * @param expenseNumbers List structure for storing the numbers
     */
    public static void scanExpenseReport(File inputFile, List<Integer> expenseNumbers) {
        Scanner intTokenizer = null;
        try {
            intTokenizer = new Scanner(inputFile);

            while (intTokenizer.hasNextInt()) {
                int num = intTokenizer.nextInt();
                expenseNumbers.add(num);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            intTokenizer.close();
        }
    }

    /**
     * Calcuates the product of the list of numbers
     * @param numbers List of numbers to be multiplied
     * @return long value for the product
     */
    public static long calculateProduct(List<Integer> numbers) {
        long product = 1;

        for (Integer integer : numbers) {
            product *= integer;
        }

        return product;
    }

    /**
     * Recursively finds the complements that add up to 2020
     * @param expenseNumbers List of the Expense Report numbers
     * @param ignoredExpenseIndexes List of the Expense Report indexes to ignore
     * @param rollingSum Total sum so far in searching for the next complement
     * @param numberOfComplements Number of complements to be found
     * @return List of the complements that add up to 2020
     */
    public static List<Integer> findComplements(List<Integer> expenseNumbers, 
                        List<Integer> ignoredExpenseIndexes, int rollingSum, 
                        int numberOfComplements) {
        List<Integer> complements = null;

        if (numberOfComplements == 1) {
            for (int i = 0; i < expenseNumbers.size(); i++) {
                if (ignoredExpenseIndexes.contains(i)) {
                    continue;
                }

                if (rollingSum + expenseNumbers.get(i) == 2020) {
                    List<Integer> lastComplement = new ArrayList<Integer>();
                    lastComplement.add(expenseNumbers.get(i));
                    return lastComplement;
                }
            }
        } else {
            for (int i = 0; i < expenseNumbers.size(); i++) {
                if (rollingSum + expenseNumbers.get(i) < 2020) {
                    ignoredExpenseIndexes.add(i);
                    List<Integer> results = findComplements(expenseNumbers, ignoredExpenseIndexes, 
                        rollingSum + expenseNumbers.get(i), numberOfComplements - 1);

                    if (results != null) {
                        results.add(expenseNumbers.get(i));
                        return results;
                    }

                    ignoredExpenseIndexes.remove(ignoredExpenseIndexes.size() - 1);
                }
            }
        }

        return complements;
    }
}