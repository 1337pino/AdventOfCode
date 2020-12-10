/* Day 1: Report Repair (https://adventofcode.com/2020/day/1)

--- Part One ---

After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a 
tropical island. Surely, Christmas will go on without you.

The tropical island has its own currency and is entirely cash-only. The gold coins used there have a
little picture of a starfish; the locals just call them stars. None of the currency exchanges seem
to have heard of them, but somehow, you'll need to find fifty of these coins by the time you arrive 
so you can pay the deposit on your room.

To save your vacation, you need to get all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent 
calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. 
Good luck!

Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle 
input); apparently, something isn't quite adding up.

Specifically, they need you to find the two entries that sum to 2020 and then multiply those two 
numbers together.

For example, suppose your expense report contained the following:

1721
979
366
299
675
1456
In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 
1721 * 299 = 514579, so the correct answer is 514579.

Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you 
get if you multiply them together?

--- Part Two ---
The Elves in accounting are thankful for your help; one of them even offers you a starfish coin they
 had left over from a past vacation. They offer you a second one if you can find three numbers in 
 your expense report that meet the same criteria.

Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying
 them together produces the answer, 241861950.

In your expense report, what is the product of the three entries that sum to 2020? */

import java.io.*;
import java.util.*;

public class Day1 {
    public static void main(String[] args) {
        // Declarations //////
        File expenseReport = new File(System.getProperty("user.dir") 
        + "\\src\\main\\java\\AdventOfCode2020\\input\\Day1.txt");
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