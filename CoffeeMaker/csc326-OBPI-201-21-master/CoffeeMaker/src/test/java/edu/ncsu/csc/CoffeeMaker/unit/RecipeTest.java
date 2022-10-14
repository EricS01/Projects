package edu.ncsu.csc.CoffeeMaker.unit;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    @Autowired
    private RecipeService service;

    @BeforeEach
    public void setup () {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        final Ingredient chocolate = new Ingredient( "Chocolate", 1 );
        final Ingredient coffee = new Ingredient( "Coffee", 0 );
        final Ingredient milk = new Ingredient( "Milk", 0 );
        final Ingredient sugar = new Ingredient( "Sugar", 0 );
        r1.addIngredient( chocolate );
        r1.addIngredient( coffee );
        r1.addIngredient( milk );
        r1.addIngredient( sugar );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        final Ingredient chocolate2 = new Ingredient( "Chocolate", 1 );
        final Ingredient coffee2 = new Ingredient( "Coffee", 1 );
        final Ingredient milk2 = new Ingredient( "Milk", 1 );
        final Ingredient sugar2 = new Ingredient( "Sugar", 1 );
        r2.addIngredient( chocolate2 );
        r2.addIngredient( coffee2 );
        r2.addIngredient( milk2 );
        r2.addIngredient( sugar2 );
        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assertions.assertEquals( 2, recipes.size(),
                "Creating two recipes should result in two recipes in the database" );

        Assertions.assertEquals( r1, recipes.get( 0 ), "The retrieved recipe should match the created one" );
    }

    @Test
    @Transactional
    public void testAddRecipe1 () {

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, 1, 1, 0 );

        service.save( r1 );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );

    }

    /* Test2 is done via the API for different validation */

    @Test
    @Transactional
    public void testAddRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, -50, 3, 1, 1, 0 );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative price" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe13 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(),
                "Creating two recipes should result in two recipes in the database" );

    }

    @Test
    @Transactional
    public void testAddRecipe14 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

    }

    @Test
    @Transactional
    public void testDeleteRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "There should be one recipe in the database" );

        service.delete( r1 );
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testDeleteRecipe2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );

    }

    @Test
    @Transactional
    public void testEditRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        r1.setPrice( 70 );

        service.save( r1 );

        final Recipe retrieved = service.findByName( "Coffee" );

        Assertions.assertEquals( 70, (int) retrieved.getPrice() );
        for ( final Ingredient i : retrieved.getIngredients() ) {
            if ( "Coffee".equals( i.getName() ) ) {
                Assertions.assertEquals( 3, (int) i.getAmount() );
            }
            else if ( "Milk".equals( i.getName() ) ) {
                Assertions.assertEquals( 1, (int) i.getAmount() );
            }
            else if ( "Sugar".equals( i.getName() ) ) {
                Assertions.assertEquals( 1, (int) i.getAmount() );
            }
            else if ( "Chocolate".equals( i.getName() ) ) {
                Assertions.assertEquals( 0, (int) i.getAmount() );
            }
        }

        Assertions.assertEquals( 1, service.count(), "Editing a recipe shouldn't duplicate it" );

    }

    /**
     * Tests that involves milestone 4 individual implementation of updateRecipe
     */
    @Test
    @Transactional
    public void testEditRecipe2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final Recipe recipe = new Recipe();
        final Ingredient chocolate = new Ingredient( "Chocolate", 5 );
        final Ingredient coffee = new Ingredient( "Coffee", 3 );
        final Ingredient milk = new Ingredient( "Milk", 0 );

        final ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        // ingredients.add( chocolate );
        // ingredients.add( coffee );
        // ingredients.add( milk );
        recipe.addIngredient( milk );
        recipe.addIngredient( chocolate );
        recipe.addIngredient( coffee );

        Assertions.assertEquals( 5, (int) recipe.getIngredients().get( 1 ).getAmount() );

        // final Recipe r1 = createRecipe( "Coffee1", 50, 3, 1, 1, 0 );
        // final Recipe r2 = createRecipe( "Coffee2", 60, 4, 2, 2, 1 );
        // service.save( r1 );
        // r1.updateRecipe( r2 );
        // service.save( r1 );
        //
        // // UpdateRecipe doesn't change name but changes all other attributes.
        // final Recipe retrieved = service.findByName( "Coffee1" );
        //
        // for ( final Ingredient i : retrieved.getIngredients() ) {
        // if ( "Coffee".equals( i.getName() ) ) {
        // Assertions.assertEquals( 4, (int) i.getAmount() );
        // }
        // else if ( "Milk".equals( i.getName() ) ) {
        // Assertions.assertEquals( 2, (int) i.getAmount() );
        // }
        // else if ( "Sugar".equals( i.getName() ) ) {
        // Assertions.assertEquals( 2, (int) i.getAmount() );
        // }
        // else if ( "Chocolate".equals( i.getName() ) ) {
        // Assertions.assertEquals( 1, (int) i.getAmount() );
        // }
        // }
        //
        // Assertions.assertTrue( retrieved.toString().equals( "Coffee1" ) );
        //
        // Assertions.assertEquals( 1, service.count(), "Editing a recipe
        // shouldn't duplicate it" );

    }

    /**
     * Tests the check recipe method which only returns true when recipe is
     * empty
     *
     * Created by ejsamuel as part of Milestone 1
     */
    @Test
    @Transactional
    public void testCheckRecipe () {
        Assertions.assertEquals( 0, service.findAll().size() );
        final Recipe fullCoffee = createRecipe( "Mocha", 50, 3, 1, 1, 0 );
        final Recipe emptyCoffee = createRecipe( "Latte", 0, 0, 0, 0, 0 );
        Assertions.assertFalse( fullCoffee.checkRecipe() );
        Assertions.assertTrue( emptyCoffee.checkRecipe() );

    }

    @Test
    public void testRecipeEquals () {

        final Recipe r1 = createRecipe( "Coffee1", 50, 3, 1, 1, 0 );
        final Recipe r2 = createRecipe( "Coffee2", 60, 4, 2, 2, 1 );
        final Recipe r3 = createRecipe( "Coffee1", 50, 3, 1, 1, 0 );
        final Recipe r4 = createRecipe( null, 50, 3, 1, 1, 0 );
        Assertions.assertFalse( r1.equals( r2 ) );
        Assertions.assertFalse( r1.equals( null ) );
        Assertions.assertTrue( r1.equals( r3 ) );
        Assertions.assertFalse( r4.equals( r1 ) );

    }

    private Recipe createRecipe ( final String name, final Integer price, final Integer coffeeAmt,
            final Integer milkAmt, final Integer sugarAmt, final Integer chocolateAmt ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );

        final Ingredient chocolate = new Ingredient( "Chocolate", chocolateAmt );
        final Ingredient coffee = new Ingredient( "Coffee", coffeeAmt );
        final Ingredient milk = new Ingredient( "Milk", milkAmt );
        final Ingredient sugar = new Ingredient( "Sugar", sugarAmt );
        recipe.addIngredient( chocolate );
        recipe.addIngredient( coffee );
        recipe.addIngredient( milk );
        recipe.addIngredient( sugar );
        recipe.hashCode();
        return recipe;

    }

}
