import java.util.*;

/**
 * Calculates interest rates for loans and deposits based on user input
 * @author Eric Samuel
 */
public class BankCalculator {

    /** Number of months in a year */
    public static final int NUMBER_MONTHS_YEAR = 12;

    /** Used to convert percent to decimal */
    public static final int PERCENT_TO_DECIMAL = 100;

    /** One constant used in loan calculation */
    public static final int ONE_CONSTANT = 1;

    /** one year after max months on loan for the for loop */
    public static final int MAX_MONTHS = 84;

    /** thirty six months used in for loop */
    public static final int THIRTY_SIX = 36;

    /**  Zero constant used in for loops */
    public static final int ZERO_CONSTANT = 0;

    /**  three constant used in for loops */
    public static final int THREE_CONSTANT = 3;
    
    /**  20,000 constant for length conditions */
    public static final int TWENTY_THOUSAND_CONSTANT = 20000;
    
    /**  2000 constant for length conditions */
    public static final int TWO_THOUSAND_CONSTANT = 2000;
    
    /**  500 constant for length conditions */
    public static final int FIVE_HUNDRED_CONSTANT = 500;

    /** 
     * Constantly prompts user for input until they press q to quit
     * Calculates loan interest rates if L is entered(non-case sensitive)
     * Calculates interest rates for deposit if D is enterest(non-case sensitive)
     * @param args command line arguments (not used)
     */

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        String option = "";


        while (!option.equalsIgnoreCase("Q")) {

            displayMenu();

            System.out.print("Option: ");
            option = scnr.next();


            if (option.equalsIgnoreCase("L")) {

                System.out.print("Amount: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int loAmount = scnr.nextInt();
                if (loAmount <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                System.out.print("Minimum interest rate: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int minInterestRate = scnr.nextInt();
                if (minInterestRate <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }

                System.out.print("Maximum interest rate: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int maxInterestRate = scnr.nextInt();
                if (maxInterestRate <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                if (minInterestRate > maxInterestRate) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }

                System.out.println();
                System.out.println("\t\t\t     Monthly Payment");
                System.out.println();
                System.out.print("Interest   ");
                if (loAmount > TWENTY_THOUSAND_CONSTANT) {
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print(" " + i + " mos    ");
                    }
                    System.out.println();
                    System.out.print("--------   ");
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print(" ------    ");
                    }

                } else if (loAmount < TWENTY_THOUSAND_CONSTANT && loAmount > 
                    TWO_THOUSAND_CONSTANT) {
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print(i + " mos    ");
                    }
                    System.out.println();
                    System.out.print("--------   ");
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print("------    ");
                    }

                } else if (loAmount < TWO_THOUSAND_CONSTANT) {
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print(i + " mos   ");
                    }
                    System.out.println();
                    System.out.print("--------   ");
                    for (int i = NUMBER_MONTHS_YEAR; i < MAX_MONTHS; i += NUMBER_MONTHS_YEAR) {
                        System.out.print("------   ");
                    }

                }
                System.out.println();
                for (int i = minInterestRate; i < maxInterestRate + 1; i++) {
                    System.out.print("   " + i + "%      ");
                    for (int j = NUMBER_MONTHS_YEAR; j < THIRTY_SIX; j += NUMBER_MONTHS_YEAR) {
                        System.out.printf("%.2f    ", (calculatePayment(loAmount, i, j)));

                    }
                    for (int j = THIRTY_SIX; j < MAX_MONTHS; j += NUMBER_MONTHS_YEAR) {
                        System.out.printf("%.2f     ", (calculatePayment(loAmount, i, j)));

                    }
                    System.out.println();




                }


            } else if (option.equalsIgnoreCase("D")) {

                System.out.print("Amount: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int depAmount = scnr.nextInt();
                if (depAmount <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                System.out.print("Years: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int years = scnr.nextInt();
                if (years <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                System.out.print("Minimum interest rate: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int minInterestRate = scnr.nextInt();
                if (minInterestRate <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                System.out.print("Maximum interest rate: ");
                if (!scnr.hasNextInt()) {
                    System.out.print("Invalid value");
                    scnr.next();
                    System.out.println();
                    continue;

                }
                int maxInterestRate = scnr.nextInt();
                if (maxInterestRate <= ZERO_CONSTANT) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                if (minInterestRate > maxInterestRate) {
                    System.out.print("Invalid value");

                    System.out.println();
                    continue;
                }
                System.out.println();

                System.out.println("\t\t        Final Amount");
                System.out.println();
                System.out.println("              No          Monthly      Continuous");
                System.out.print("Interest  Compounding   Compounding   Compounding");


                System.out.println();
                System.out.print("--------  ");
                for (int i = ZERO_CONSTANT; i < THREE_CONSTANT; i++) {
                    System.out.print("-----------   ");
                }

                System.out.println();
                if (depAmount < FIVE_HUNDRED_CONSTANT) {
                    for (int i = minInterestRate; i < maxInterestRate + ONE_CONSTANT; i++) {
                        System.out.print("   " + i + "%        ");
                        System.out.printf("%.2f           ",
                            calculateAmountNoCompounding(depAmount, i, years));
                        System.out.printf("%.2f           ",
                            calculateAmountMonthlyCompounding(depAmount, i, years));
                        System.out.printf("%.2f         ",
                            calculateAmountContinuousCompounding(depAmount, i, years));
                        System.out.println();


                    }
                } else {
                    for (int i = minInterestRate; i < maxInterestRate + ONE_CONSTANT; i++) {
                        System.out.print("   " + i + "%        ");
                        System.out.printf("%.2f      ",
                            calculateAmountNoCompounding(depAmount, i, years));
                        System.out.printf("%.2f      ",
                            calculateAmountMonthlyCompounding(depAmount, i, years));
                        System.out.printf("%.2f      ",
                            calculateAmountContinuousCompounding(depAmount, i, years));
                        System.out.println();


                    }


                }


            } else if (!option.equalsIgnoreCase("Q")) {


                System.out.print("Invalid option");
                System.out.println();
            }
        }

        System.out.println();


    }

    /**
    * Displays the bank calculator menu
    
    */


    public static void displayMenu() {
        System.out.println();
        System.out.println("Bank Calculator - Please choose an option.");
        System.out.println();
        System.out.println("L - Loan \n" +
            "D - Deposit \n" +
            "Q - Quit");
        System.out.println();

    }
    /** 
     * Calculates loan payments based on amount given, interest rate, and number of months.
     * @param loanAmount will be used to calculate interets rates on
     * @param annualInterestRate will be used to convert to monthly interest rate
     * @param numberOfMonths will be used to determine interest rates 
     * based on how long loan will be payed off in
     * @return total payment as double based on given inputs
     * @throws IllegalArgumentException if loanAmount is less than or equal to 0
     * @throws IllegalArgumentException if annualInterestRate is less than or equal to 0
     * @throws IllegalArgumentException if numberOfMonths is less than or equal to 0
     */

    public static double calculatePayment(int loanAmount, int annualInterestRate,
        int numberOfMonths) {
        if (loanAmount <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid loan amount");

        }
        if (annualInterestRate <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid interest rate");

        }
        if (numberOfMonths <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid number of months");

        }

        double monthlyInterestRate = ((double) annualInterestRate / PERCENT_TO_DECIMAL) /
            NUMBER_MONTHS_YEAR;

        double totalTop = monthlyInterestRate * Math.pow((ONE_CONSTANT + monthlyInterestRate),
            numberOfMonths);
        double totalBot = Math.pow((ONE_CONSTANT + monthlyInterestRate), numberOfMonths) -
            ONE_CONSTANT;
        double total = (double) loanAmount * ((totalTop) / (totalBot));
        return total;


    }

    /** 
     * Calculates Interest rates based on amount given, interest rate, 
     * and number of months with no compounding.
     * @param depositAmount will be used to calculate interets rates on
     * @param annualInterestRate will be used to convert to monthly interest rate
     * @param numberOfYears will be used to determine interest rates 
     * based on how long loan will be payed off in
     * @return total interest included amount as double based on given inputs
     * @throws IllegalArgumentException if depositAmount is less than or equal to 0
     * @throws IllegalArgumentException if annualInterestRate is less than or equal to 0
     * @throws IllegalArgumentException if numberOfMonths is less than or equal to 0
     */

    public static double calculateAmountNoCompounding(int depositAmount, int annualInterestRate,
        int numberOfYears) {
        if (depositAmount <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid deposit amount");

        }
        if (annualInterestRate <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid interest rate");

        }
        if (numberOfYears <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid number of years");

        }

        double rate = (double) annualInterestRate / PERCENT_TO_DECIMAL;
        double total = (double) depositAmount * (ONE_CONSTANT + (rate * numberOfYears));
        return total;
    }

    /** 
     * Calculates Interest rates based on amount given, interest rate,
     * and number of months with monthly compounding.
     * @param depositAmount will be used to calculate interets rates on
     * @param annualInterestRate will be used to convert to monthly interest rate
     * @param numberOfYears will be used to determine interest rates based 
     * on how long loan will be payed off in
     * @return total interest included amount as double based on given inputs
     * @throws IllegalArgumentException if depositAmount is less than or equal to 0
     * @throws IllegalArgumentException if annualInterestRate is less than or equal to 0
     * @throws IllegalArgumentException if numberOfMonths is less than or equal to 0
     */

    public static double calculateAmountMonthlyCompounding(int depositAmount,
        int annualInterestRate,
        int numberOfYears) {

        if (depositAmount <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid deposit amount");

        }
        if (annualInterestRate <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid interest rate");

        }
        if (numberOfYears <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid number of years");

        }
        double rate = (double) annualInterestRate / PERCENT_TO_DECIMAL;
        double total = (double) depositAmount * (Math.pow(ONE_CONSTANT +
            (rate / NUMBER_MONTHS_YEAR), (NUMBER_MONTHS_YEAR * numberOfYears)));
        return total;
    }

    /** 
     * Calculates Interest rates based on amount given, interest rate, 
     * and number of months with continuous compounding.
     * @param depositAmount will be used to calculate interets rates on
     * @param annualInterestRate will be used to convert to monthly interest rate
     * @param numberOfYears will be used to determine interest rates based
     * on how long loan will be payed off in
     * @return total interest included amount as double based on given inputs
     * @throws IllegalArgumentException if depositAmount is less than or equal to 0
     * @throws IllegalArgumentException if annualInterestRate is less than or equal to 0
     * @throws IllegalArgumentException if numberOfMonths is less than or equal to 0
     */

    public static double calculateAmountContinuousCompounding(int depositAmount,
        int annualInterestRate,
        int numberOfYears) {
        if (depositAmount <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid deposit amount");

        }
        if (annualInterestRate <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid interest rate");

        }
        if (numberOfYears <= ZERO_CONSTANT) {
            throw new IllegalArgumentException("Invalid number of years");

        }
        double rate = (double) annualInterestRate / PERCENT_TO_DECIMAL;
        double total = (double) depositAmount * Math.pow(Math.E, rate * numberOfYears);
        return total;
    }

}