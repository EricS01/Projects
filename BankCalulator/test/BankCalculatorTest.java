import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Program to test BankCalculator methods
 * @author Suzanne Balik
 * @author Eric Samuel
 */
public class BankCalculatorTest {
    
    /** Used to compare double values in tests */
    public static final double DELTA = 0.005;

    
    /**
    * Testing calculatePayment method test 1
    */
    
    @Test
    public void testCalculatePayment1() {

        assertEquals(64.24, BankCalculator.calculatePayment(2020,9,36), DELTA,
                     "BankCalculator.calculatePayment(2020,9,36)");

    }
    
    /**
    * Testing calculatePayment method test 2
    */
    
    @Test
    public void testCalculatePayment2() {

        assertEquals(16.10, BankCalculator.calculatePayment(1000,5,72), DELTA,
                     "BankCalculator.calculatePayment(1000,5,72)");

    }
    
    /**
    * Testing calculatePayment method test 3 
    */
    
    @Test
    public void testCalculatePayment3() {

        assertEquals(175.28, BankCalculator.calculatePayment(10000,2,60), DELTA,
                     "BankCalculator.calculatePayment(10000,2,60)");

    }
    
    /**
    * Testing calculatePayment method test 4
    */
    
    @Test
    public void testCalculatePayment4() {

        assertEquals(1096.78, BankCalculator.calculatePayment(25000,5,24), DELTA,
                     "BankCalculator.calculatePayment(25000,5,24)");

    }
    
    


    /**
    * Testing CalculateAmountNoCompounding test 1
    */

    @Test
    public void testCalculateAmountNoCompounding1() {

        assertEquals(215.04, BankCalculator.calculateAmountNoCompounding(192,3,4), DELTA,
                     "BankCalculator.calculateAmountNoCompounding(192,3,4)");

    }
    
    /**
    * Testing CalculateAmountNoCompounding test 2
    */

    @Test
    public void testCalculateAmountNoCompounding2() {

        assertEquals(1542.50, BankCalculator.calculateAmountNoCompounding(1234,5,5), DELTA,
                     "BankCalculator.calculateAmountNoCompounding(1234,5,5)");

    }
    
     /**
    * Testing CalculateAmountNoCompounding test 3
    */

    @Test
    public void testCalculateAmountNoCompounding3() {

        assertEquals(15360.00, BankCalculator.calculateAmountNoCompounding(9600,10,6), DELTA,
                     "BankCalculator.calculateAmountNoCompounding(9600,10,6)");

    }
    
    /**
    * Testing CalculateAmountNoCompounding test 4
    */
    @Test
    public void testCalculateAmountNoCompounding4() {

        assertEquals(43750.00, BankCalculator.calculateAmountNoCompounding(8750,20,20), DELTA,
                     "BankCalculator.calculateAmountNoCompounding(8750,20,20)");

    }


    /**
    * Testing CalculateAmountMonthlyCompounding test 1
    */
 
    @Test
    public void testCalculateAmountMonthlyCompounding1() {

        assertEquals(152173.69, BankCalculator.calculateAmountMonthlyCompounding(56098,5,20), DELTA,
                     "BankCalculator.calculateAmountMonthlyCompounding(56098,5,20)");

    }
    
    /**
    * Testing CalculateAmountMonthlyCompounding test 2
    */
 
    @Test
    public void testCalculateAmountMonthlyCompounding2() {

        assertEquals(71934.21, BankCalculator.calculateAmountMonthlyCompounding(26573,10,10), DELTA,
                     "BankCalculator.calculateAmountMonthlyCompounding(26573,10,10)");

    }
    
    /**
    * Testing CalculateAmountMonthlyCompounding test 3
    */
 
    @Test
    public void testCalculateAmountMonthlyCompounding3() {

        assertEquals(146613.76, 
            BankCalculator.calculateAmountMonthlyCompounding(15670,15,15), DELTA,
                     "BankCalculator.calculateAmountMonthlyCompounding(15670,15,15)");

    }
    
    /**
    * Testing CalculateAmountMonthlyCompounding test 4
    */
 
    @Test
    public void testCalculateAmountMonthlyCompounding4() {

        assertEquals(29036.81, BankCalculator.calculateAmountMonthlyCompounding(25000,5,3), DELTA,
                     "BankCalculator.calculateAmountMonthlyCompounding(25000,5,3)");

    }
    
    /**
    * Testing CalculateAmountContinousCompounding test 1
    */

    @Test
    public void testCalculateAmountContinuousCompounding1() {

        assertEquals(2875.68, BankCalculator.calculateAmountContinuousCompounding(2500,2,7), DELTA,
                     "BankCalculator.calculateAmountContinuousCompounding(2500,2,7)");

    }
    
    /**
    * Testing CalculateAmountContinousCompounding test 2
    */

    @Test
    public void testCalculateAmountContinuousCompounding2() {

        assertEquals(4257.20, BankCalculator.calculateAmountContinuousCompounding(3000,5,7), DELTA,
                     "BankCalculator.calculateAmountContinuousCompounding(3000,5,7)");

    }
    
    /**
    * Testing CalculateAmountContinousCompounding test 3
    */

    @Test
    public void testCalculateAmountContinuousCompounding3() {

        assertEquals(225312.45, 
            BankCalculator.calculateAmountContinuousCompounding(75000,10,11), DELTA,
                     "BankCalculator.calculateAmountContinuousCompounding(75000,10,11)");

    }
    
    /**
    * Testing CalculateAmountContinousCompounding test 4
    */

    @Test
    public void testCalculateAmountContinuousCompounding4() {

        assertEquals(18551644.89, 
            BankCalculator.calculateAmountContinuousCompounding(125000,20,25), DELTA,
                     "BankCalculator.calculateAmountContinuousCompounding(125000,20,25)");

    }







    /**
     * Test the BankCalculator methods with invalid values
     */
    @Test
    public void testInvalidMethods() {

        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculatePayment(0,2,10),
                                           "BankCalculator.calculatePayment(0,2,10)");
        assertEquals("Invalid loan amount", exception.getMessage(),
                     "Testing BankCalculator.calculatePayment(0,2,10) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculatePayment(5000,-2,10),
                                           "BankCalculator.calculatePayment(5000,-2,10)");
        assertEquals("Invalid interest rate", exception.getMessage(),
                     "Testing BankCalculator.calculatePayment(5000,-2,10) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculatePayment(1000,2,-1),
                                           "BankCalculator.calculatePayment(1000,2,-1)");
        assertEquals("Invalid number of months", exception.getMessage(),
                     "Testing BankCalculator.calculatePayment(1000,2,-1) - exception message");
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountNoCompounding(0,2,10),
                                           "BankCalculator.calculateAmountNoCompounding(0,2,10)");
        assertEquals("Invalid deposit amount", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountNoCompounding(0,2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountNoCompounding(5000,-2,10),
                    "BankCalculator.calculateAmountNoCompounding(5000,-2,10)");
        assertEquals("Invalid interest rate", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountNoCompounding(5000,-2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountNoCompounding(1000,2,-1),
                    "BankCalculator.calculateAmountNoCompounding(1000,2,-1)");
        assertEquals("Invalid number of years", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountNoCompounding(1000,2,-1)" +
                     " - exception message");
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountMonthlyCompounding(0,2,10),
                    "BankCalculator.calculateAmountMonthlyCompounding(0,2,10)");
        assertEquals("Invalid deposit amount", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountMonthlyCompounding(0,2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountMonthlyCompounding(5000,-2,10),
                    "BankCalculator.calculateAmountMonthlyCompounding(5000,-2,10)");
        assertEquals("Invalid interest rate", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountMonthlyCompounding(5000,-2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountMonthlyCompounding(1000,2,-1),
                    "BankCalculator.calculateAmountMonthlyCompounding(1000,2,-1)");
        assertEquals("Invalid number of years", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountMonthlyCompounding(1000,2,-1)" +
                     " - exception message");                    
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountContinuousCompounding(0,2,10),
                    "BankCalculator.calculateAmountContinuousCompounding(0,2,10)");
        assertEquals("Invalid deposit amount", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountContinuousCompounding(0,2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountContinuousCompounding(5000,-2,10),
                    "BankCalculator.calculateAmountContinuousCompounding(5000,-2,10)");
        assertEquals("Invalid interest rate", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountContinuousCompounding(5000,-2,10)" +
                     " - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> BankCalculator.calculateAmountContinuousCompounding(1000,2,-1),
                    "BankCalculator.calculateAmountContinuousCompounding(1000,2,-1)");
        assertEquals("Invalid number of years", exception.getMessage(),
                     "Testing BankCalculator.calculateAmountContinuousCompounding(1000,2,-1)" +
                     " - exception message");




    }
}
