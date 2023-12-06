import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    Map<String, Integer> wordsToNumber;

    public Main() {
        wordsToNumber = new HashMap<String, Integer>();
        fillMap(wordsToNumber);
    }

    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.partTwo());
    }

    public int partOne() {
        String text = getFileText();
        String[] textLines = text.split("\n");

        return getSum(textLines, false);
    }

    public int partTwo() {

        String text = getFileText();

        String[] textLines = text.split("\n");

        return getSum(textLines, true);
    }

    // gets the total sum by adding two numbers from each line
    public int getSum(String[] textLines, boolean isPart2) {
        int sum = 0;

        for (String line : textLines) {
            // System.out.println("Before: " + line);
            String convertedLine = line;
            if (isPart2) {
                convertedLine = convert(line, wordsToNumber);
            }

            // if start and end pointer stay at -1 then the line did not have a number.
            int start = -1;
            int end = -1;

            // use two pointer method
            for (int i = 0, j = convertedLine.length() - 1; i < convertedLine.length(); i++, j--) {
                // Finding start Number
                if (start == -1 && Character.isDigit(convertedLine.charAt(i))) {
                    start = Character.getNumericValue(convertedLine.charAt(i));
                }
                // Finding end Number
                if (end == -1 && Character.isDigit(convertedLine.charAt(j))) {
                    end = Character.getNumericValue(convertedLine.charAt(j));
                }
                // if both numbers are found then go to the next Line
                if (start != -1 && end != -1) {
                    // System.out.println(convertedLine);
                    // System.out.println("Start: " + start + " end: " + end);
                    break;

                }

            }
            // If we have both numbers then shift start number to tens place and add end to
            // it
            if (start != -1 && end != -1) {
                sum += start * 10 + end;
            }
        }

        return sum;
    }

    // helper function to convert string words to numbers
    public String convert(String line, Map<String, Integer> mp) {
        // Build the regex pattern to match any of the number words
        String pattern = String.join("|", mp.keySet());

        // Compile the pattern
        Pattern r = Pattern.compile(pattern);

        // Create a matcher for the line
        Matcher m = r.matcher(line);
        StringBuffer sb = new StringBuffer();

        // Replace each matched number word with its numeric equivalent
        while (m.find()) {
            m.appendReplacement(sb, mp.get(m.group()).toString());
        }
        m.appendTail(sb);

        return sb.toString();
    }

    // helper function to fill map with constants
    public void fillMap(Map<String, Integer> mp) {
        mp.put("zero", 0);
        mp.put("one", 1);
        mp.put("two", 2);
        mp.put("three", 3);
        mp.put("four", 4);
        mp.put("five", 5);
        mp.put("six", 6);
        mp.put("seven", 7);
        mp.put("eight", 8);
        mp.put("nine", 9);
    }

    // reads external txt file
    public String getFileText() {
        // String fileContent = "";
        StringBuilder sb = new StringBuilder();
        try {
            File fs = new File("input.txt");
            Scanner scanner = new Scanner(fs);
            while (scanner.hasNextLine()) {
                sb.append("\n");
                sb.append(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An Error Occured");
            e.printStackTrace();
            return "File Not Found";
        }

        return sb.toString();
    }
}