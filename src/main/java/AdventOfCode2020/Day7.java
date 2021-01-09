/* Day 7: Handy Haversacks (https://adventofcode.com/2020/day/7)

--- Part One ---
You land at the regional airport in time for your next flight. In fact, it looks like you'll even 
have time to grab some food: all flights are currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags 
and their contents; bags must be color-coded and must contain specific quantities of other 
color-coded bags. Apparently, nobody responsible for these regulations considered how long they 
would take to enforce!

For example, consider the following rules:

    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.

These rules specify the required contents for 9 bag types. In this example, every faded blue bag is 
empty, every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different 
bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually, 
contain at least one shiny gold bag?)

In the above rules, the following options would be available to you:

- A bright white bag, which can hold your shiny gold bag directly.
- A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
- A dark orange bag, which can hold bright white and muted yellow bags, either of which could then 
hold your shiny gold bag.
- A light red bag, which can hold bright white and muted yellow bags, either of which could then hold 
your shiny gold bag.

So, in this example, the number of bag colors that can eventually contain at least one shiny gold 
bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite 
long; make sure you get all of it.)
*/


import AdventOfCodeHelper.LuggageBags;
import java.io.*;
import java.util.*;

public class Day7 {
    public static void main(String[] args) {
        // Declarations //////
        File puzzleInputFile = new File(System.getProperty("user.dir") 
        + "\\src\\main\\java\\AdventOfCode2020\\input\\Day7.txt");

        HashMap<String, LuggageBags> luggageDefinitions = new HashMap<String, LuggageBags>();
        //////////////////////

        // Load puzzle input data
        readTestInput(puzzleInputFile, luggageDefinitions);



        System.out.println("---------- DAY 7 2020 ----------");

        System.out.println(
                "Part 1 (How many bag colors can eventually contain at least one shiny gold bag?): " 
                + countBagColorsThatContainX(luggageDefinitions, "shiny gold"));
    }

    /**
     * Counts the number of bag colors could be the outermost container for a bag of a given color
     * 
     * @param luggageDefinitions List of all luggage content rules
     * @param bagColorX Given color to be looking for
     * @return Total number of bag colors
     */
    private static int countBagColorsThatContainX(HashMap<String, LuggageBags> luggageDefinitions, 
            String bagColorX) {
        int count = 0;

        for (String key : luggageDefinitions.keySet()) {
            LuggageBags bag = luggageDefinitions.get(key);

            if (containsColorX(bag, luggageDefinitions, bagColorX)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Recursively "searches through a bag" to see if a given color bag exists within it according 
     * to the luggage definitions.
     * 
     * @param bag Given bag to look into the contents of
     * @param luggageDefinitions List of all luggage content rules
     * @param colorX Given color to be looking for
     * @return True if any contents of the bag, whether immediate contents or further nested in 
     * other bags, match the given color
     */
    private static boolean containsColorX(LuggageBags bag, HashMap<String, 
            LuggageBags> luggageDefinitions, String colorX) {
        for (String bagColor : bag.nestedBags.keySet()) {
            if (bagColor.equals(colorX)) {
                return true;
            }

            if (Day7.containsColorX(luggageDefinitions.get(bagColor), luggageDefinitions, colorX)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Scans the input file and stores the data in a map of bag colors (String) and bags 
     * (LuggageBags)
     * 
     * @param inputFile text file for the list of luggage content rules
     * @param log Map for storing the rules definitions locally
     */
    public static void readTestInput(File inputFile, HashMap<String, LuggageBags> log) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            while (lineTokenizer.hasNextLine()) {
                String bagDefinition = lineTokenizer.nextLine();
                String[] splitBagDefition = bagDefinition.split(" bags contain ");

                log.put(splitBagDefition[0], 
                        new LuggageBags(splitBagDefition[0], splitBagDefition[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}
