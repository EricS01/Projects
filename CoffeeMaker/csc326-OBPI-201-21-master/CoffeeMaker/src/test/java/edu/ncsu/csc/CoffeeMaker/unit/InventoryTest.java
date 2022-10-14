package edu.ncsu.csc.CoffeeMaker.unit;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setup () {
        final Inventory ivt = inventoryService.getInventory();
        final Ingredient chocolate = new Ingredient( "Chocolate", 500 );
        final Ingredient coffee = new Ingredient( "Coffee", 500 );
        final Ingredient milk = new Ingredient( "Milk", 500 );
        ivt.addNewIngredient( chocolate );
        ivt.addNewIngredient( coffee );
        ivt.addNewIngredient( milk );

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        final Ingredient chocolate = new Ingredient( "Chocolate", 10 );
        final Ingredient coffee = new Ingredient( "Coffee", 1 );

        recipe.addIngredient( chocolate );
        recipe.addIngredient( coffee );

        recipe.setPrice( 5 );

        i.useIngredients( recipe );

        // Make sure that all of the inventory fields are now properly updated
        for ( final Ingredient ing : i.getIngredients() ) {
            if ( ing.getName().equals( "Chocolate" ) ) {
                Assert.assertEquals( 490, (int) ing.getAmount() );
            }
            else if ( ing.getName().equals( "Coffee" ) ) {
                Assert.assertEquals( 499, (int) ing.getAmount() );
            }
            else if ( ing.getName().equals( "Milk" ) ) {
                Assert.assertEquals( 500, (int) ing.getAmount() );
            }
        }
    }

    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();
        final Ingredient chocolate = new Ingredient( "Chocolate", 5 );
        final Ingredient coffee = new Ingredient( "Coffee", 3 );
        final Ingredient milk = new Ingredient( "Milk", 0 );

        final ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( chocolate );
        ingredients.add( coffee );
        ingredients.add( milk );

        ivt.addIngredients( ingredients );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        for ( final Ingredient i : ivt.getIngredients() ) {
            if ( i.getName().equals( "Chocolate" ) ) {
                Assert.assertEquals( "5 Chocolate should have been added to the inventory.", 505, (int) i.getAmount() );
            }
            else if ( i.getName().equals( "Coffee" ) ) {
                Assert.assertEquals( "3 Coffee should have been added to the inventory.", 503, (int) i.getAmount() );
            }
            else if ( i.getName().equals( "Milk" ) ) {
                Assert.assertEquals( "0 Milk should have been added to the inventory.", 500, (int) i.getAmount() );
            }
        }
    }
    //
    // @Test
    // @Transactional
    // public void testAddInventory2 () {
    // final Inventory ivt = inventoryService.getInventory();
    //
    // try {
    // ivt.addIngredients( -5, 3, 7, 2 );
    // }
    // catch ( final IllegalArgumentException iae ) {
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for coffee should
    // result in no changes -- coffee",
    // 500, (int) ivt.getCoffee() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for coffee should
    // result in no changes -- milk",
    // 500, (int) ivt.getMilk() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for coffee should
    // result in no changes -- sugar",
    // 500, (int) ivt.getSugar() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for coffee should
    // result in no changes -- chocolate",
    // 500, (int) ivt.getChocolate() );
    // }
    // }
    //
    // @Test
    // @Transactional
    // public void testAddInventory3 () {
    // final Inventory ivt = inventoryService.getInventory();
    //
    // try {
    // ivt.addIngredients( 5, -3, 7, 2 );
    // }
    // catch ( final IllegalArgumentException iae ) {
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for milk should
    // result in no changes -- coffee",
    // 500, (int) ivt.getCoffee() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for milk should
    // result in no changes -- milk",
    // 500, (int) ivt.getMilk() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for milk should
    // result in no changes -- sugar",
    // 500, (int) ivt.getSugar() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for milk should
    // result in no changes -- chocolate",
    // 500, (int) ivt.getChocolate() );
    //
    // }
    //
    // }
    //
    // @Test
    // @Transactional
    // public void testAddInventory4 () {
    // final Inventory ivt = inventoryService.getInventory();
    //
    // try {
    // ivt.addIngredients( 5, 3, -7, 2 );
    // }
    // catch ( final IllegalArgumentException iae ) {
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for sugar should
    // result in no changes -- coffee",
    // 500, (int) ivt.getCoffee() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for sugar should
    // result in no changes -- milk",
    // 500, (int) ivt.getMilk() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for sugar should
    // result in no changes -- sugar",
    // 500, (int) ivt.getSugar() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for sugar should
    // result in no changes -- chocolate",
    // 500, (int) ivt.getChocolate() );
    //
    // }
    //
    // }
    //
    // @Test
    // @Transactional
    // public void testAddInventory5 () {
    // final Inventory ivt = inventoryService.getInventory();
    //
    // try {
    // ivt.addIngredients( 5, 3, 7, -2 );
    // }
    // catch ( final IllegalArgumentException iae ) {
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for chocolate
    // should result in no changes -- coffee",
    // 500, (int) ivt.getCoffee() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for chocolate
    // should result in no changes -- milk",
    // 500, (int) ivt.getMilk() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for chocolate
    // should result in no changes -- sugar",
    // 500, (int) ivt.getSugar() );
    // Assert.assertEquals(
    // "Trying to update the Inventory with an invalid value for chocolate
    // should result in no changes -- chocolate",
    // 500, (int) ivt.getChocolate() );
    //
    // }
    //
    // }
    //
    // /**
    // * Tests that when no inventories exist, calling the getInventory method
    // * creates a new one with 0 stock
    // *
    // * Created by cshimes as part of Milestone 1
    // */
    // @Test
    // @Transactional
    // public void testGetNewInventory () {
    // // Delete existing recipe records
    // inventoryService.deleteAll();
    //
    // // Fetch the records
    // Inventory ivt = inventoryService.getInventory();
    //
    // // Check to assert that a new inventory has been created and has 0
    // // ingredients
    // ivt = inventoryService.getInventory();
    // Assert.assertEquals( 0, (int) ivt.getCoffee() );
    // Assert.assertEquals( 0, (int) ivt.getMilk() );
    // Assert.assertEquals( 0, (int) ivt.getSugar() );
    // Assert.assertEquals( 0, (int) ivt.getChocolate() );
    // }
    //
    // /**
    // * Tests when there isn't enough ingredients for recipe
    // *
    // * Created by ejsamuel as part of Milestone 1
    // */
    // @Test
    // @Transactional
    // public void testNotEnoughIngredients () {
    // // Delete existing recipe records
    // inventoryService.deleteAll();
    // final Recipe r1 = new Recipe();
    // r1.setName( "Black Coffee" );
    // r1.setPrice( 1 );
    // r1.setCoffee( 10 );
    // r1.setMilk( 10 );
    // r1.setSugar( 10 );
    // r1.setChocolate( 10 );
    //
    // // Fetch the records
    // Inventory ivt = inventoryService.getInventory();
    //
    // ivt = inventoryService.getInventory();
    // Assert.assertEquals( 0, (int) ivt.getCoffee() );
    // Assert.assertEquals( 0, (int) ivt.getMilk() );
    // Assert.assertEquals( 0, (int) ivt.getSugar() );
    // Assert.assertEquals( 0, (int) ivt.getChocolate() );
    //
    // ivt.addIngredients( 1, 1, 1, 1 );
    //
    // Assert.assertFalse( ivt.enoughIngredients( r1 ) );
    // }
    //
    // /**
    // * Tests when we check an ingredient for non number value
    // *
    // * Created by ejsamuel as part of Milestone 1
    // */
    // @Test
    // @Transactional
    // public void testCheckingInvalidIngredients () {
    // // Delete existing recipe records
    // final Inventory ivt = inventoryService.getInventory();
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkChocolate( "a" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkMilk( "b" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkSugar( "c" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkCoffee( "d" ) );
    // }
    //
    // @Test
    // @Transactional
    // public void testInventoryCheckMethods () {
    // final Inventory ivt = inventoryService.getInventory();
    //
    // int chocolate = 0;
    // int milk = 0;
    // int sugar = 0;
    // int coffee = 0;
    //
    // // Trying Valid Values
    // try {
    // chocolate = ivt.checkChocolate( "10" );
    // }
    // catch ( final IllegalArgumentException e ) {
    // Assertions.fail();
    // }
    // try {
    // milk = ivt.checkMilk( "10" );
    // }
    // catch ( final IllegalArgumentException e ) {
    // Assertions.fail();
    // }
    // try {
    // sugar = ivt.checkSugar( "10" );
    // }
    // catch ( final IllegalArgumentException e ) {
    // Assertions.fail();
    // }
    // try {
    // coffee = ivt.checkCoffee( "10" );
    // }
    // catch ( final IllegalArgumentException e ) {
    // Assertions.fail();
    // }
    // try {
    // ivt.addIngredients( coffee, milk, sugar, chocolate );
    // }
    // catch ( final IllegalArgumentException iae ) {
    // Assertions.fail();
    // }
    //
    // // Trying Invalid Values
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkChocolate( "-10" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkMilk( "-10" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkSugar( "-10" ) );
    // Assertions.assertThrows( IllegalArgumentException.class, () ->
    // ivt.checkCoffee( "-10" ) );
    //
    // }
    //
    // @Test
    // @Transactional
    // public void testNegativeIngredients () {
    //
    // }

}
