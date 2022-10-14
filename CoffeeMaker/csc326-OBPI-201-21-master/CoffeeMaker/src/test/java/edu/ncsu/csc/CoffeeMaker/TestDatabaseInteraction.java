package edu.ncsu.csc.CoffeeMaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )

public class TestDatabaseInteraction {

    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    public void testRecipes () {
        recipeService.deleteAll();
        final Recipe r = new Recipe();
        final Ingredient chocolate = new Ingredient( "Chocolate", 0 );
        final Ingredient coffee = new Ingredient( "Coffee", 1 );
        final Ingredient sugar = new Ingredient( "Sugar", 0 );
        r.addIngredient( chocolate );
        r.addIngredient( coffee );
        r.addIngredient( sugar );
        r.setName( "Latte" );
        r.setPrice( 4 );
        recipeService.save( r );

        final Recipe r2 = new Recipe();
        final Ingredient chocolate2 = new Ingredient( "Chocolate", 10 );
        final Ingredient coffee2 = new Ingredient( "Coffee", 10 );
        final Ingredient sugar2 = new Ingredient( "Sugar", 10 );
        r2.addIngredient( chocolate2 );
        r2.addIngredient( coffee2 );
        r2.addIngredient( sugar2 );
        r2.setName( "Latte Cream" );
        r2.setPrice( 5 );
        recipeService.save( r2 );

        final List<Recipe> dbRecipes = recipeService.findAll();
        assertEquals( 2, dbRecipes.size() );
        final Recipe dbRecipe = dbRecipes.get( 0 );

        assertEquals( r.getName(), dbRecipe.getName() );
        assertEquals( r.getIngredients(), dbRecipe.getIngredients() );
        assertEquals( r.getPrice(), dbRecipe.getPrice() );

        assertEquals( 4, recipeService.findByName( "Latte" ).getPrice() );

        // Test editing a recipe's custom ingredient value
        dbRecipe.setPrice( 15 );
        dbRecipe.updateIngredient( "Sugar", 12 );
        recipeService.save( dbRecipe );

        final List<Recipe> editRecipes = recipeService.findAll();
        assertEquals( 2, editRecipes.size() );
        final Recipe editedRecipe = dbRecipes.get( 0 );
        assertEquals( 15, editedRecipe.getPrice() );

        for ( final Ingredient i : editedRecipe.getIngredients() ) {
            if ( "Sugar".equals( i.getName() ) ) {
                assertEquals( 12, i.getAmount() );
                break;
            }
        }

        dbRecipe.updateRecipe( r2 );
        for ( final Ingredient i : editedRecipe.getIngredients() ) {
            if ( "Sugar".equals( i.getName() ) ) {
                assertEquals( 10, i.getAmount() );
                break;
            }
        }

    }

    @Test
    @Transactional
    public void testDeleteRecipe () {
        recipeService.deleteAll();

        // Create new Recipe
        final Recipe r = new Recipe();
        r.setName( "Recipe 1" );
        final Ingredient chocolate = new Ingredient( "Chocolate", 5 );
        final Ingredient coffee = new Ingredient( "Coffee", 4 );
        final Ingredient milk = new Ingredient( "Milk", 3 );
        final Ingredient sugar = new Ingredient( "Sugar", 2 );
        r.addIngredient( chocolate );
        r.addIngredient( coffee );
        r.addIngredient( milk );
        r.addIngredient( sugar );
        r.setPrice( 10 );

        // Add Recipe 1 to database
        recipeService.save( r );

        // Check to ensure it is in database
        final List<Recipe> dbRecipes = recipeService.findAll();
        assertEquals( 1, dbRecipes.size() );
        final Recipe dbRecipe = dbRecipes.get( 0 );
        assertEquals( r.getName(), dbRecipe.getName() );

        // Delete Recipe From Database
        recipeService.delete( r );

        // Check to ensure database contains no recipes
        final List<Recipe> dbRecipes2 = recipeService.findAll();
        assertEquals( 0, dbRecipes2.size() );

        // Create new recipe
        final Recipe r2 = new Recipe();
        r2.setName( "Recipe 2" );
        final Ingredient chocolate2 = new Ingredient( "Chocolate", 5 );
        final Ingredient coffee2 = new Ingredient( "Coffee", 4 );
        final Ingredient milk2 = new Ingredient( "Milk", 3 );
        final Ingredient sugar2 = new Ingredient( "Sugar", 2 );
        r2.addIngredient( chocolate2 );
        r2.addIngredient( coffee2 );
        r2.addIngredient( milk2 );
        r2.addIngredient( sugar2 );
        r2.setPrice( 11 );

        // Save Recipe 1 to database again
        recipeService.save( r );

        // Try to delete Recipe 2 which isn't in the database. This shouldn't do
        // anything.
        recipeService.delete( r2 );

        // Check to ensure Recipe 1 is in database
        final List<Recipe> dbRecipes3 = recipeService.findAll();
        assertEquals( 1, dbRecipes3.size() );
        final Recipe dbRecipe3 = dbRecipes.get( 0 );
        assertEquals( r.getName(), dbRecipe3.getName() );

        // Delete Recipe 1 From Database
        recipeService.delete( r );

        // Delete Recipe 1 From Database Again. This shouldn't do anything.
        recipeService.delete( r );
    }
}
