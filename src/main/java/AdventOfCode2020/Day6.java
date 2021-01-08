/* Day 6: Custom Customs (https://adventofcode.com/2020/day/6)

--- Part One ---
As your flight approaches the regional airport where you'll switch to a much larger plane, customs 
declaration forms are distributed to the passengers.

The form asks a series of 26 yes-or-no questions marked a through z. All you need to do is identify 
the questions for which anyone in your group answers "yes". Since your group is just you, this 
doesn't take very long.

However, the person sitting next to you seems to be experiencing a language barrier and asks if you 
can help. For each of the people in their group, you write down the questions for which they answer 
"yes", one per line. For example:

    abcx
    abcy
    abcz

In this group, there are 6 questions to which anyone answered "yes": a, b, c, x, y, and z. 
(Duplicate answers to the same question don't count extra; each question counts at most once.)

Another group asks for your help, then another, and eventually you've collected answers from every 
group on the plane (your puzzle input). Each group's answers are separated by a blank line, and 
within each group, each person's answers are on a single line. For example:

    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b

This list represents answers from five groups:

- The first group contains one person who answered "yes" to 3 questions: a, b, and c.
- The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
- The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
- The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
- The last group contains one person who answered "yes" to only 1 question, b.

In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.

For each group, count the number of questions to which anyone answered "yes". What is the sum of 
those counts?
*/

import java.io.*;
import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        // Declarations //////
        File puzzleInputFile = new File(System.getProperty("user.dir") 
            + "\\src\\main\\java\\AdventOfCode2020\\input\\Day6.txt");

        ArrayList<String> questionnaireAnswers = new ArrayList<String>();

        int totalYesQuestions = 0;
        //////////////////////

        readTestInput(puzzleInputFile, questionnaireAnswers);

        for (String groupAnswers : questionnaireAnswers) {
            ArrayList<Character> questions = new ArrayList<Character>();

            for (int i = 0; i < groupAnswers.length(); i++) {
                if (!questions.contains(groupAnswers.charAt(i))) {
                    questions.add(groupAnswers.charAt(i));
                }
            }

            totalYesQuestions += questions.size();
        }

        System.out.println("---------- DAY 6 2020 ----------");

        System.out.println("Part 1 (total unique yes questions for each group): " 
                + totalYesQuestions);
    }

    /**
     * Scans the input file and stores the data in a list
     * @param inputFile text file for the list of boarding passes
     * @param log List of all of the question results
     */
    public static void readTestInput(File inputFile, ArrayList<String> log) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            String groupAnswers = "";

            while (lineTokenizer.hasNextLine()) {
                String passengerAnswers = lineTokenizer.nextLine();

                if (passengerAnswers.equals("")) {
                    log.add(groupAnswers);
                    groupAnswers = "";
                } else {
                    groupAnswers += passengerAnswers;
                }
            }
            log.add(groupAnswers);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}
