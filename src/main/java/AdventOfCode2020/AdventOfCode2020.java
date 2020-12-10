public class AdventOfCode2020 {
    public static void main(String[] args) {
        String runSelection = (args.length == 0) ? "all" : args[0];

        switch(runSelection) {
            case "1":
                Day1.main(null);
                break;
            case "all":
                System.out.println("----- RUNNING ALL PROBLEMS -----");
                Day1.main(null);
        }
    }
}