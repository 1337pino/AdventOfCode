public class AdventOfCode2020 {
    public static void main(String[] args) {
        String runSelection = (args.length == 0) ? "all" : args[0];

        switch(runSelection) {
            case "1":
                Day1.main(null);
                break;
            case "2":
                Day2.main(null);
                break;
            case "3":
                Day3.main(null);
                break;
            case "4":
                Day4.main(null);
                break;
            case "all":
                System.out.println("----- RUNNING ALL PROBLEMS -----");
                Day1.main(null);
                Day2.main(null);
                Day3.main(null);
                Day4.main(null);
        }
    }
}