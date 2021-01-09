package AdventOfCodeHelper;

import java.util.HashMap;

public class LuggageBags {
    public String name;
    public HashMap<String, Integer> nestedBags;

    public LuggageBags(String bagName, String bagContents) {
        this.name = bagName;
        this.nestedBags = new HashMap<String, Integer>();

        if (!bagContents.equals("no other bags.")) {
            String[] contentsDefinition = bagContents.split("(\\sbags,|\\sbag\\,|\\sbags\\.|\\sbag\\.)\\s*");
            //String[] contentsDefinition = bagContents.split("(\\,|\\.)\\s*");
            for (String bag : contentsDefinition) {
                if (!bag.isEmpty()) {
                    int firstSpaceIndex = bag.indexOf(" ");

                    nestedBags.put(bag.substring(firstSpaceIndex + 1), 
                            Integer.parseInt(bag.substring(0, firstSpaceIndex)));
                }
            }
        }
    }


}
