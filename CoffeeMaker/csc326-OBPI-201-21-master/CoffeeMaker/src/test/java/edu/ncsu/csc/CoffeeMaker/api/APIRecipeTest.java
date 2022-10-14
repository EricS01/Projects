package edu.ncsu.csc.CoffeeMaker.api;

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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIRecipeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

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
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", 5 );
        final Ingredient milkR = new Ingredient( "Milk", 5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        // final Recipe r = new Recipe();
        // r.setChocolate( 5 );
        // r.setCoffee( 3 );
        // r.setMilk( 4 );
        // r.setSugar( 8 );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void deleteRecipe () throws Exception {
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", 5 );
        final Ingredient milkR = new Ingredient( "Milk", 5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        service.save( recipe );

        // final Recipe r = new Recipe();
        // r.setChocolate( 5 );
        // r.setCoffee( 3 );
        // r.setMilk( 4 );
        // r.setSugar( 8 );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );

        mvc.perform( delete( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void getRecipe () throws Exception {
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", 5 );
        final Ingredient milkR = new Ingredient( "Milk", 5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        service.save( recipe );

        // final Recipe r = new Recipe();
        // r.setChocolate( 5 );
        // r.setCoffee( 3 );
        // r.setMilk( 4 );
        // r.setSugar( 8 );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );

        final String recipe2 = mvc.perform( get( "/api/v1/recipes/Coffee" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        // System.out.print( recipe2 );

    }

    @Test
    @Transactional
    public void editRecipe () throws Exception {
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", 5 );
        final Ingredient milkR = new Ingredient( "Milk", 5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) ).andExpect( status().isOk() ).andDo( print() );
        recipe.setPrice( 10 );
        mvc.perform( put( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) ).andExpect( status().isOk() ).andDo( print() );

        // final Recipe r = new Recipe();
        // r.setChocolate( 5 );
        // r.setCoffee( 3 );
        // r.setMilk( 4 );
        // r.setSugar( 8 );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );

        // System.out.print( recipe2 );

    }

}
