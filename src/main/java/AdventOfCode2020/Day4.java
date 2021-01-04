/* Day 4: Passport Processing (https://adventofcode.com/2020/day/4)

--- Part One ---
You arrive at the airport only to realize that you grabbed your North Pole Credentials instead of 
your passport. While these documents are extremely similar, North Pole Credentials aren't issued by 
a country and therefore aren't actually valid documentation for travel in most of the world.

It seems like you're not the only one having problems, though; a very long line has formed for the 
automatic passport scanners, and the delay could upset your travel itinerary.

Due to some questionable network security, you realize you might be able to solve both of these 
problems at the same time.

The automatic passport scanners are slow because they're having trouble detecting which passports 
have all required fields. The expected fields are as follows:

- byr (Birth Year)
- iyr (Issue Year)
- eyr (Expiration Year)
- hgt (Height)
- hcl (Hair Color)
- ecl (Eye Color)
- pid (Passport ID)
- cid (Country ID)

Passport data is validated in batch files (your puzzle input). Each passport is represented as a 
sequence of key:value pairs separated by spaces or newlines. Passports are separated by blank lines.

Here is an example batch file containing four passports:

    ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
    byr:1937 iyr:2017 cid:147 hgt:183cm

    iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
    hcl:#cfa07d byr:1929

    hcl:#ae17e1 iyr:2013
    eyr:2024
    ecl:brn pid:760753108 byr:1931
    hgt:179cm

    hcl:#cfa07d eyr:2025 pid:166559648
    iyr:2011 ecl:brn hgt:59in

The first passport is valid - all eight fields are present. The second passport is invalid - it is 
missing hgt (the Height field).

The third passport is interesting; the only missing field is cid, so it looks like data from North 
Pole Credentials, not a passport at all! Surely, nobody would mind if you made the system 
temporarily ignore missing cid fields. Treat this "passport" as valid.

The fourth passport is missing two fields, cid and byr. Missing cid is fine, but missing any other 
field is not, so this passport is invalid.

According to the above rules, your improved system would report 2 valid passports.

Count the number of valid passports - those that have all required fields. Treat cid as optional. 
In your batch file, how many passports are valid?

--- Part Two ---
The line is moving more quickly now, but you overhear airport security talking about how passports 
with invalid data are getting through. Better add some data validation, quick!

You can continue to ignore the cid field, but each other field has strict rules about what values 
are valid for automatic validation:

- byr (Birth Year) - four digits; at least 1920 and at most 2002.
- iyr (Issue Year) - four digits; at least 2010 and at most 2020.
- eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
- hgt (Height) - a number followed by either cm or in:
- If cm, the number must be at least 150 and at most 193.
- If in, the number must be at least 59 and at most 76.
- hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
- ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
- pid (Passport ID) - a nine-digit number, including leading zeroes.
- cid (Country ID) - ignored, missing or not.

Your job is to count the passports where all required fields are both present and valid according 
to the above rules. Here are some example values:

    byr valid:   2002
    byr invalid: 2003

    hgt valid:   60in
    hgt valid:   190cm
    hgt invalid: 190in
    hgt invalid: 190

    hcl valid:   #123abc
    hcl invalid: #123abz
    hcl invalid: 123abc

    ecl valid:   brn
    ecl invalid: wat

    pid valid:   000000001
    pid invalid: 0123456789

Here are some invalid passports:

    eyr:1972 cid:100
    hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

    iyr:2019
    hcl:#602927 eyr:1967 hgt:170cm
    ecl:grn pid:012533040 byr:1946

    hcl:dab227 iyr:2012
    ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

    hgt:59cm ecl:zzz
    eyr:2038 hcl:74454a iyr:2023
    pid:3556412378 byr:2007

Here are some valid passports:

    pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
    hcl:#623a2f

    eyr:2029 ecl:blu cid:129 byr:1989
    iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

    hcl:#888785
    hgt:164cm byr:2001 iyr:2015 cid:88
    pid:545766238 ecl:hzl
    eyr:2022

    iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719

Count the number of valid passports - those that have all required fields and valid values. 
Continue to treat cid as optional. In your batch file, how many passports are valid?
*/

import java.io.*;
import java.util.*;

public class Day4 {
    static List<String> validEyeColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    public static void main(String[] args) {
        // Declarations //////
        File passportDataFile = new File(System.getProperty("user.dir") 
            + "\\src\\main\\java\\AdventOfCode2020\\input\\Day4.txt");
        ArrayList<String> passportData = new ArrayList<String>();

        int numberOfValidPassportsPart1 = 0, numberOfValidPassportsPart2 = 0;
        //////////////////////
 
        scanBatchFile(passportDataFile, passportData);

        for (String passportDataBatch : passportData) {
            if (isValid(passportDataBatch)) numberOfValidPassportsPart1++;
            if (hasValidData(passportDataBatch)) numberOfValidPassportsPart2++;
        }

        System.out.println("Part 1 (number of valid passports): " + numberOfValidPassportsPart1);
        
        System.out.println("Part 1 (number of valid passports): " + numberOfValidPassportsPart2);
    }

    /**
     * Checks a batch of passport data to determine if the passport is valid
     * @param passportData String of passport data
     * @return TRUE if the string contains data for all eight required fields
     */
    private static boolean isValid(String passportData) {
        if (passportData.contains("byr") 
            && passportData.contains("iyr")
            && passportData.contains("eyr")
            && passportData.contains("hgt")
            && passportData.contains("hcl")
            && passportData.contains("ecl")
            && passportData.contains("pid")) {

            return true;
        }

        return false;
    }

    /**
     * Confirms if the passport data is completely valid
     * @param passportData string representing a passport
     * @return TRUE if all the fields in the passport are correct
     */
    private static boolean hasValidData(String passportData) {
        if (isValid(passportData)) {
            String[] passportDataItems = passportData.split("\\s");

            for (String dataItem : passportDataItems) {
                if (dataItem.length() == 0) {
                    continue;
                } else {
                    String dataName = dataItem.substring(0, 3);
                    String dataValue = dataItem.substring(4);

                    try {
                        switch(dataName) {
                            case "byr":
                                if ((Integer.parseInt(dataValue) < 1920) 
                                        || (Integer.parseInt(dataValue) > 2002)) {
                                    return false;
                                }
                                break;
                            case "iyr":
                                if ((Integer.parseInt(dataValue) < 2010) 
                                        || (Integer.parseInt(dataValue) > 2020)) {
                                    return false;
                                }
                                break;
                            case "eyr":
                                if ((Integer.parseInt(dataValue) < 2020) 
                                        || (Integer.parseInt(dataValue) > 2030)) {
                                    return false;
                                }
                                break;
                            case "hgt":
                                int hgt = Integer.parseInt(
                                        dataValue.substring(0, dataValue.length() - 2));
                                String measurementSystem = dataValue.substring(
                                        dataValue.length() - 2);
                                if (measurementSystem.equals("cm") 
                                        && ((hgt < 150) || (hgt > 193))) {
                                    return false;
                                }
                                if (measurementSystem.equals("in") && ((hgt < 59) || (hgt > 76))) {
                                    return false;
                                }
                                break;
                            case "hcl":
                                if (!isHexColorCode(dataValue)) return false;
                                break;
                            case "ecl":
                                if (!validEyeColors.contains(dataValue)) return false;
                                break;
                            case "pid":
                                if (!isIdentificationNumber(dataValue)) return false;
                                break;
                            case "cid":
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Confirms if the string is a valid ID number: a nine-digit number, including leading zeroes.
     * @param id string representation of an ID number
     * @return TRUE if a valid ID number
     */
    private static boolean isIdentificationNumber(String id) {
        if (id.length() != 9) return false;

        for (int i = 0; i < id.length(); i++) {
            switch (id.charAt(i)) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    /**
     * Confirms if the string is a valid hex color code: a # followed by exactly six characters 0-9 
     * or a-f.
     * @param hexcode string representation of a hex code
     * @return TRUE if a valid hex color
     */
    private static boolean isHexColorCode(String hexcode) {
        if ((hexcode.length() != 7) || (hexcode.charAt(0) != '#')) return false;

        for (int i = 1; i < hexcode.length(); i++) {
            switch (hexcode.charAt(i)) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    /**
     * Scans file of password data. Each batch of passport data i put into a single String
     * @param inputFile text file for the password data
     * @param passportData List structure for storing the restructured password data
     */
    private static void scanBatchFile(File inputFile, List<String> passportData) {
        Scanner lineTokenizer = null;
        try {
            lineTokenizer = new Scanner(inputFile);

            String passportBatch = "";
            while (lineTokenizer.hasNextLine()) {
                String nextLine = lineTokenizer.nextLine();
                if (nextLine.equals("")) {
                    passportData.add(passportBatch);
                    passportBatch = "";
                } else {
                    passportBatch = passportBatch + " " + nextLine; 
                }
            }
            if (passportBatch != "") passportData.add(passportBatch);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lineTokenizer.close();
        }
    }
}