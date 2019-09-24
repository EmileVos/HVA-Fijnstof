import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Emile Vos
 *
 * BTW het is een best practice om je code en comentaar in het engels te doen dus dat doe ik ook :)
 * BTW it is an best practive to write your code and comments in english so thats what i'm going to do :)
 */
public class Main {

    private static final double WHF_VALUE = 20;
    private static final double EU_VALUE = 40;
    private static final double SIG_VALUE = 6;

    private static final String[] DAYS = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday"
    };
    private static Scanner scanner;

    /**
     * @param args Arguments specified by the user in the command line {command} {arg}...
     */
    public static void main(String[] args) {
        if(args.length != 0) {
            System.out.println("Invalid arguments");
        }
        scanner = new Scanner(System.in);


        System.out.println("Mijn naam is Emile Vos en mijn studentnummer is 500091234");
        System.out.println();
        System.out.println("Please enter the values of the following days in μg/m3");

        double[] userEnteredValues = new double[5];
        //loop trough every index and fills the userEnteredValues
        for(int index = 0; index < 5; index++) {
            userEnteredValues[index] = waitForNextDouble(true, DAYS[index] + ": ");
        }
        System.out.println();

        Object[] tableTitle = {"Day", "Value", "Sigs", "Comment"};
        String tableFormat = "%-10s %-10s %-5s     %-50s\n";
        System.out.format(tableFormat, tableTitle);

        int dayIndex = 0;
        for (double value : userEnteredValues) {
            Object[] tableRow = {DAYS[dayIndex], value, ((double) ((double) ((int) value / SIG_VALUE * 100)) / 100), ""}; //initialize the row that will be printed

            if(value > WHF_VALUE) {
                tableRow[3] = (value - WHF_VALUE) + " above the WHF norm";
            }
            if(value > EU_VALUE) {
                //i can show "and" because the EU_VALUE is always more than the WHF_VALUE
                tableRow[3] += " and " + (value - EU_VALUE) + " above the EU norm";
            }
            System.out.printf(tableFormat, tableRow);
            dayIndex++; //Show the next day i.e. Monday > Tuesday
        }


        System.out.println();
        System.out.println("Amount of values above the WHF norm (" + WHF_VALUE + "): " + amountOfValuesAboveNorm(userEnteredValues, WHF_VALUE));
        System.out.println("Amount of values above the EU norm (" + EU_VALUE + "): " + amountOfValuesAboveNorm(userEnteredValues, EU_VALUE));
    }

    /**
     * Loops through the values array parameter and counts all values that are above the norm parameter
     *
     * @param values Array with doubles
     * @param norm
     *          double
     * @return int
     */
    private static int amountOfValuesAboveNorm(double[] values, double norm) {
        int valuesAboveNorm = 0;
        for (double value : values) {
            if(value > norm) {
                valuesAboveNorm++;
            }
        }
        return valuesAboveNorm;
    }

    /**
     * Waits and returns a by the user specified double in the command line
     *
     * @param checkForBelowZero Checks if the returned double is more than zero
     * @param prefix will be printed before waiting for a user input
     * @return double
     */
    private static double waitForNextDouble(boolean checkForBelowZero, String prefix) {
        System.out.print(prefix);
        if(scanner.hasNext()) {
            try  {
                double returnValue = Double.parseDouble(scanner.next());

                if(checkForBelowZero && returnValue < 0) {
                    //The specified value has to be checked and is below zero
                    System.out.println("Invalid format! The value may not be below zero");
                    return waitForNextDouble(true, prefix);
                }

                return returnValue;
            } catch (NumberFormatException exception) {
                //This is called when the user specified a invalid number format and tries again
                System.out.println("Invalid format! ");
                return waitForNextDouble(checkForBelowZero, prefix);
            }
        }
        return 0;
    }
}
