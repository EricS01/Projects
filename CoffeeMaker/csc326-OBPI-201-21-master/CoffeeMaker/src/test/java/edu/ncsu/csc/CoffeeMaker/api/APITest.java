package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APITest {
    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

    @Autowired
    private InventoryService      inventoryService;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    @Transactional
    public void ensureRecipe () throws Exception {
        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        if ( !recipe.contains( "Mocha" ) ) {
            // create a new Mocha recipe
            final Recipe r = new Recipe();
            final Ingredient chocolate = new Ingredient( "Chocolate", 5 );
            final Ingredient coffee = new Ingredient( "Coffee", 3 );
            final Ingredient milk = new Ingredient( "Milk", 2 );
            final Ingredient sugar = new Ingredient( "Sugar", 4 );
            r.setName( "Mocha" );
            r.addIngredient( chocolate );
            r.addIngredient( coffee );
            r.addIngredient( milk );
            r.addIngredient( sugar );
            r.setPrice( 6 );

            // Make a POST request to insert the Mocha recipe
            mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        }

        recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        // Now that it has been inserted if it didn't exist before, the Mocha
        // recipe should now exist
        assertTrue( recipe.contains( "Mocha" ) );

        // Add an excessive amount of inventory to ensure enough is present for
        // the Mocha recipe
        final Inventory ivt = inventoryService.getInventory();
        final Ingredient chocolate = new Ingredient( "Chocolate", 50 );
        final Ingredient coffee = new Ingredient( "Coffee", 50 );
        final Ingredient milk = new Ingredient( "Milk", 50 );
        final Ingredient sugar = new Ingredient( "Coffee", 50 );
        // final Inventory i = new Inventory( 50, 50, 50, 50 );
        ivt.addNewIngredient( chocolate );
        ivt.addNewIngredient( coffee );
        ivt.addNewIngredient( milk );
        ivt.addNewIngredient( sugar );

        final Ingredient peanut = new Ingredient( "Peanut", 12 );

        mvc.perform( put( "/api/v1/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ivt ) ) ).andExpect( status().isOk() );

        // Try making coffee
        mvc.perform( post( "/api/v1/makecoffee/Mocha" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 100 ) ) ).andExpect( status().isOk() ).andDo( print() );

        mvc.perform( post( "/api/v1/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( peanut ) ) ).andExpect( status().isOk() ).andDo( print() );

        final String recipe3 = mvc.perform( get( "/api/v1/inventory" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @Transactional
    public void testDeleteRecipe () throws Exception {
        final String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        if ( !recipe.contains( "testDeleteRecipe" ) ) {
            // create a new recipe
            final Recipe r = new Recipe();
            r.setName( "testDeleteRecipe" );
            final Ingredient chocolate = new Ingredient( "Chocolate", 6 );
            final Ingredient coffee = new Ingredient( "Coffee", 4 );
            final Ingredient milk = new Ingredient( "Milk", 3 );
            final Ingredient sugar = new Ingredient( "Sugar", 2 );

            r.addIngredient( chocolate );
            r.addIngredient( coffee );
            r.addIngredient( milk );
            r.addIngredient( sugar );
            r.setPrice( 10 );

            // Make a POST request to insert the recipe
            mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        }
        if ( !recipe.contains( "testDeleteRecipe2" ) ) {
            // create a new recipe
            final Recipe r1 = new Recipe();
            final Ingredient chocolate = new Ingredient( "Chocolate", 1 );
            final Ingredient coffee = new Ingredient( "Coffee", 2 );
            final Ingredient milk = new Ingredient( "Milk", 3 );
            final Ingredient sugar = new Ingredient( "Sugar", 4 );
            r1.setName( "testDeleteRecipe2" );
            r1.addIngredient( chocolate );
            r1.addIngredient( coffee );
            r1.addIngredient( milk );
            r1.addIngredient( sugar );
            r1.setPrice( 11 );

            // Make a POST request to insert the Mocha recipe
            mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );

        }
        final String recipe2 = mvc.perform( get( "/api/v1/recipes" ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        assertTrue( recipe2.contains( "testDeleteRecipe" ) );
        assertTrue( recipe2.contains( "testDeleteRecipe2" ) );

        mvc.perform( delete( "/api/v1/recipes/testDeleteRecipe2" ) ).andExpect( status().isOk() );

        final String recipe3 = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        assertTrue( recipe3.contains( "testDeleteRecipe" ) );
        assertFalse( recipe3.contains( "testDeleteRecipe2" ) );

        mvc.perform( delete( "/api/v1/recipes/testDeleteRecipe" ) ).andExpect( status().isOk() );

        final String recipe4 = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        assertFalse( recipe4.contains( "testDeleteRecipe" ) );
        assertFalse( recipe4.contains( "testDeleteRecipe2" ) );
    }

    /**
     * Tests the response of the APIRecipeController when attempting to add a
     * fourth recipe (only 3 are allowed)
     *
     * @throws Exception
     *
     *             Created by cshimes as part of Milestone 1
     */
    @Test
    @Transactional
    public void testCreateRecipeInsufficientStorage () throws Exception {
        // Delete all recipes currently in the db
        service.deleteAll();

        final Recipe r1 = new Recipe();
        r1.setName( "recipe1" );

        final Recipe r2 = new Recipe();
        r2.setName( "recipe2" );

        final Recipe r3 = new Recipe();
        r3.setName( "recipe3" );

        // Save the first three recipes to the db
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().isOk() );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r3 ) ) ).andExpect( status().isOk() );

        // Try saving a fourth recipe to the db
        final Recipe r4 = new Recipe();
        r4.setName( "r4" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isInsufficientStorage() );
    }

    /**
     * Tests the response of the APIRecipeController when a recipe with a name
     * the same as another is added to the database
     *
     * @throws Exception
     *
     *             Created by cshimes as part of Milestone 1
     */
    @Test
    @Transactional
    public void testCreateRecipeDuplicateName () throws Exception {
        // Delete all recipes currently in the db
        service.deleteAll();

        final Recipe r1 = new Recipe();
        r1.setName( "recipe1" );

        final Recipe r2 = new Recipe();
        r2.setName( "recipe1" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().isConflict() );
    }

}
