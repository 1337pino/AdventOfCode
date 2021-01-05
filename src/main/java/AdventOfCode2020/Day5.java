/* Day 5: Binary Boarding (https://adventofcode.com/2020/day/5)

--- Part One ---
You board your plane only to discover a new problem: you dropped your boarding pass! You aren't 
sure which seat is yours, and all of the flight attendants are busy with the flood of people that 
suddenly made it through passport control.

You write a quick program to use your phone's camera to scan all of the nearby boarding passes 
(your puzzle input); perhaps you can find your seat through process of elimination.

Instead of zones or groups, this airline uses binary space partitioning to seat people. A seat 
might be specified like FBFBBFFRLR, where F means "front", B means "back", L means "left", and R 
means "right".

The first 7 characters will either be F or B; these specify exactly one of the 128 rows on the 
plane (numbered 0 through 127). Each letter tells you which half of a region the given seat is in. 
Start with the whole list of rows; the first letter indicates whether the seat is in the front (0 
through 63) or the back (64 through 127). The next letter indicates which half of that region the 
seat is in, and so on until you're left with exactly one row.

For example, consider just the first seven characters of FBFBBFFRLR:

- Start by considering the whole range, rows 0 through 127.
- F means to take the lower half, keeping rows 0 through 63.
- B means to take the upper half, keeping rows 32 through 63.
- F means to take the lower half, keeping rows 32 through 47.
- B means to take the upper half, keeping rows 40 through 47.
- B keeps rows 44 through 47.
- F keeps rows 44 through 45.
- The final F keeps the lower of the two, row 44.

The last three characters will be either L or R; these specify exactly one of the 8 columns of 
seats on the plane (numbered 0 through 7). The same process as above proceeds again, this time with 
only three steps. L means to keep the lower half, while R means to keep the upper half.

For example, consider just the last 3 characters of FBFBBFFRLR:

- Start by considering the whole range, columns 0 through 7.
- R means to take the upper half, keeping columns 4 through 7.
- L means to take the lower half, keeping columns 4 through 5.
- The final R keeps the upper of the two, column 5.

So, decoding FBFBBFFRLR reveals that it is the seat at row 44, column 5.

Every seat also has a unique seat ID: multiply the row by 8, then add the column. In this example, 
the seat has ID 44 * 8 + 5 = 357.

Here are some other boarding passes:

- BFFFBBFRRR: row 70, column 7, seat ID 567.
- FFFBBBFRRR: row 14, column 7, seat ID 119.
- BBFFBBFRLL: row 102, column 4, seat ID 820.

As a sanity check, look through your list of boarding passes. What is the highest seat ID on a 
boarding pass?

--- Part Two ---
Ding! The "fasten seat belt" signs have turned on. Time to find your seat.

It's a completely full flight, so your seat should be the only missing boarding pass in your list. 
However, there's a catch: some of the seats at the very front and back of the plane don't exist on 
this aircraft, so they'll be missing from your list as well.

Your seat wasn't at the very front or back, though; the seats with IDs +1 and -1 from yours will be 
in your list.

What is the ID of your seat?
*/

import java.io.*;
import java.util.*;

public class Day5 {
    public static void main(String[] args) {
        // Declarations //////
        File puzzleInputFile = new File(System.getProperty("user.dir") 
            + "\\src\\main\\java\\AdventOfCode2020\\input\\Day5.txt");

        ArrayList<String> boardingPassList = new ArrayList<String>();

        boolean[] seatMap = new boolean[1024];
        int highestSeatIDFound = -1, yourSeatID = -1;
        //////////////////////

        // Load puzzle input data
        scanListFile(puzzleInputFile, boardingPassList);

        // Convert each seat info into seat ID
        for (String boardingPass : boardingPassList) {
            int seatID = translateBoardingPass(boardingPass);

            // Update seating map
            seatMap[seatID] = true;

            // Update largest seat ID found if needed
            if (seatID > highestSeatIDFound) highestSeatIDFound = seatID;
        }

        // Search for your seat
        for (int i = 1; i < 1023; i++) {
            if (seatMap[i] == false) {
                if (seatMap[i - 1] == true && seatMap[i + 1] == true) {
                    yourSeatID = i;
                    break;
                }

                i++; /* Increment i for the for loop to go up by 2 since we know the next seat 
                        can't be ours since this one is unoccupied */
            }
        }

        System.out.println("---------- DAY 5 2020 ----------");

        System.out.println("Part 1 (highest seat ID found): " + highestSeatIDFound);

        System.out.println("Part 2 (your seat ID): " + yourSeatID);
    }

    /**
     * Translates a boarding pass into it's seat ID.
     * 
     * The first 7 characters will either be F or B (where F means "front" and B means "back"); 
     * these specify exactly one of the 128 rows on the plane (numbered 0 through 127). The last 
     * three characters will be either L or R (where L means "left", and R means "right"); these 
     * specify exactly one of the 8 columns of seats on the plane (numbered 0 through 7).
     * @param boardingPass String representation of the boarding pass.
     * @return unique seat ID integer value
     */
    public static int translateBoardingPass(String boardingPass) {
        String rowString = boardingPass.substring(0, 7), columnString = boardingPass.substring(7);

        return (Integer.parseInt(convertBoardingStringToBinary(rowString), 2) * 8) 
                + Integer.parseInt(convertBoardingStringToBinary(columnString), 2);
    }

    /**
     * Converts a string representing a boarding pass row or column into a binary value.
     * @param rowColumn string representing the row (using F|B) or column (using L|R)
     * @return string of binary characters
     */
    private static String convertBoardingStringToBinary(String rowColumn) {
        char[] rowColumnCharArray = rowColumn.toCharArray();

        for (int i = 0; i < rowColumnCharArray.length; i++) {
            if ((rowColumnCharArray[i] == 'F') || (rowColumnCharArray[i] == 'L')) {
                rowColumnCharArray[i] = '0';
            }
            if ((rowColumnCharArray[i] == 'B') || (rowColumnCharArray[i] == 'R')) {
                rowColumnCharArray[i] = '1';
            }
        }

        return new String(rowColumnCharArray);
    }

    /**
     * Scans the list of boarding passes and loads all of the strings into a list
     * @param inputFile text file for the list of boarding passes
     * @param passwordAndPolicy List structure for boarding passes
     */
    public static void scanListFile(File inputFile, List<String> boardingPassList) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            while (lineTokenizer.hasNextLine()) {
                String boardingPass = lineTokenizer.nextLine();
                boardingPassList.add(boardingPass);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}
