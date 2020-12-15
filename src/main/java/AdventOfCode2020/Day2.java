/* Day 2: Password Philosophy (https://adventofcode.com/2020/day/2)

--- Part One ---

Your flight departs in a few days from the coastal airport; the easiest way down to the coast from 
here is via toboggan.

The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with 
our computers; we can't log in!" You ask if you can take a look.

Their password database seems to be a little corrupted: some of the passwords wouldn't have been 
allowed by the Official Toboggan Corporate Policy that was in effect when they were chosen.

To try to debug the problem, they have created a list (your puzzle input) of passwords (according to
the corrupted database) and the corporate policy when that password was set.

For example, suppose you have the following list:

1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
Each line gives the password policy and then the password. The password policy indicates the lowest 
and highest number of times a given letter must appear for the password to be valid. For example, 
1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no 
instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or
nine c, both within the limits of their respective policies.

How many passwords are valid according to their policies? */

import java.io.*;
import java.util.*;

public class Day2 {
    public static void main(String[] args) {
        // Declarations //////
        File passwordList = new File(System.getProperty("user.dir") 
        + "\\src\\main\\java\\AdventOfCode2020\\input\\Day2.txt");
        List<String> passwordsAndPolicies = new ArrayList<String>();

        int numberOfValidPasswords = 0;
        //////////////////////

        scanReport(passwordList, passwordsAndPolicies);

        for (String line : passwordsAndPolicies) {
            String[] segments = line.split("[\\s-:]+"); // tokens are any blank spaces, - , or :

            // Password Policy details
            int minimum = Integer.parseInt(segments[0]);
            int maximum = Integer.parseInt(segments[1]);
            char letter = segments[2].charAt(0);
            String password = segments[3];

            int letterCount = 0;
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) == letter) {
                    letterCount++;
                }
            }

            if ((letterCount >= minimum) && (letterCount <= maximum)) {
                numberOfValidPasswords++;
            }
        }

        System.out.println("Part 1 (number of valid passwords): " + numberOfValidPasswords);
    }

    /**
     * Scans the list of passwords and loads all of the strings into a list
     * @param inputFile text file for the list of passwords
     * @param passwordAndPolicy List structure for passwords and their policies
     */
    public static void scanReport(File inputFile, List<String> passwordAndPolicy) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            while (lineTokenizer.hasNextLine()) {
                String passwordInfo = lineTokenizer.nextLine();
                passwordAndPolicy.add(passwordInfo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}
