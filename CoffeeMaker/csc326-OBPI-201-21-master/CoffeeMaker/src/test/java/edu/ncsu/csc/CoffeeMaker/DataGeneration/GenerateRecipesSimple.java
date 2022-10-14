package edu.ncsu.csc.CoffeeMaker.DataGeneration;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class GenerateRecipesSimple {

    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    public void testCreateRecipes () {

        recipeService.deleteAll();

        final Recipe r1 = new Recipe();
        Ingredient chocolate = new Ingredient("Chocolate", 0);
        Ingredient coffee = new Ingredient("Coffee", 1);
        Ingredient milk = new Ingredient("Milk", 0);
        r1.addIngredient(chocolate);
        r1.addIngredient(coffee);
        r1.addIngredient(milk);
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );

        final Recipe r2 = new Recipe();
        Ingredient vanilla = new Ingredient("Vanilla", 4);
        Ingredient creamer = new Ingredient("Creamer", 1);
        r2.addIngredient(vanilla);
        r2.addIngredient(creamer);
        r2.setName( "Mocha" );
        r2.setPrice( 3 );

        recipeService.save( r1 );
        recipeService.save( r2 );

        Assert.assertEquals( "Creating two recipes should results in two recipes in the database", 2,
                recipeService.count() );

    }

}
