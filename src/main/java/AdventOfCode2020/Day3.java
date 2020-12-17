/* Day 3: Toboggan Trajectory (https://adventofcode.com/2020/day/3)

--- Part One ---
With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan 
might be easy, it's certainly not safe: there's very minimal steering and the area is covered in 
trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You 
make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal 
genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the 
bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers 
rational numbers); start by counting all the trees you would encounter for the slope right 3, down 
1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, 
check the position that is right 3 and down 1 from there, and so on until you go past the bottom of 
the map.

The locations you'd check in the above example are marked here with O where there was an open 
square and X where there was a tree:

..##.........##.........##.........##.........##.........##.......  --->
#..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........X.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...#X....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
In this example, traversing the map using this slope would cause you to encounter 7 trees.

Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many 
trees would you encounter?
*/

import java.io.*;
import java.util.*;

public class Day3 {
    public static void main(String[] args) {
         // Declarations //////
         File mapFile = new File(System.getProperty("user.dir") 
         + "\\src\\main\\java\\AdventOfCode2020\\input\\Day3.txt");
 
         int trees = 0;
         int horizontalSlope = 3, verticalSlope = 1;
         //////////////////////
 
         char[][] map = transcribeMap(mapFile);

         for (int i = 0, j = 0; i < map.length;) {
            if (map[i][j] == '#') trees++;

            // j = (j + horizontalSlope == map[0].length - 1) 
            //     ? (j + horizontalSlope) 
            //     : ((((j + 1) + horizontalSlope) % (map[0].length)) - 1);

            j = (j + horizontalSlope <= map[0].length - 1) 
                ? (j + horizontalSlope) 
                : ((j + horizontalSlope) % (map[0].length - 1) - 1);
            i += verticalSlope;
         }

         System.out.println("Part 1 (trees encounters with slope of right 3 down 1): " + trees);
    }

    /**
     * Scans the provided input map text file and converts it to a multi-dimensional character array
     * @param inputFile text input file representing the map
     * @return a multi-dimensional character array representing your copy of the map
     */
    public static char[][] transcribeMap(File inputFile) {
        ArrayList<String> tempMap = new ArrayList<String>();

        copyMapInformation(inputFile, tempMap);

        char map[][] = new char[tempMap.size()][tempMap.get(0).length()];

        for (int i = 0; i < tempMap.size(); i++) {
            for (int j = 0; j < tempMap.get(0).length(); j++) {
                map[i][j] = tempMap.get(i).charAt(j);
            }
        }

        return map;
    }

    /**
     * Process the map from the text file and converts it into an ArrayList structure
     * @param inputFile Text file containing the map
     * @param mapInfo ArrayList representation of the map
     */
    private static void copyMapInformation(File inputFile, ArrayList<String> mapInfo) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            while (lineTokenizer.hasNextLine()) {
                String mapLine = lineTokenizer.nextLine();
                mapInfo.add(mapLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}
