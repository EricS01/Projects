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
public class APIIngredientTest {

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
    public void ensureIngredient () throws Exception {
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", -1 );
        final Ingredient milkR = new Ingredient( "Milk", 5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().isOk() ).andDo( print() );
        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( coffeeR ) ) ).andExpect( status().is4xxClientError() )
                .andDo( print() );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().is4xxClientError() )
                .andDo( print() );
        mvc.perform( post( "/api/v1/inventoryNew" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( post( "/api/v1/newRecipe" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( post( "/api/v1/ingredientAdded" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( post( "/api/v1/editRecipeNew" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( post( "/api/v1/deleterecipe" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( post( "/api/v1/makecoffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( null ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

    }

    @Test
    @Transactional
    public void deleteIngredient () throws Exception {
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

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().isOk() ).andDo( print() );

        mvc.perform( delete( "/api/v1/ingredients/Chocolate" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().isOk() );

        mvc.perform( delete( "/api/v1/ingredients/Chocolate" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    public void getIngredients () throws Exception {
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

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().isOk() ).andDo( print() );
        final String recipe2 = mvc.perform( get( "/api/v1/ingredients" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        final String recipe3 = mvc.perform( get( "/api/v1/ingredients/Chocolate" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        final String index = mvc.perform( get( "/" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        // System.out.print( recipe2 );

    }

    @Test
    @Transactional
    public void testMappingControlle () throws Exception {
        mvc.perform( get( "/" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        mvc.perform( get( "/newRecipe" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();

        mvc.perform( get( "/newRecipeSuccess" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        // mvc.perform( get( "/ingredient" ) ).andDo( print() ).andExpect(
        // status().isOk() ).andReturn().getResponse()

        mvc.perform( get( "/deleterecipe" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        // mvc.perform( get( "/editrecipe" ) ).andDo( print() ).andExpect(
        // status().isOk() ).andReturn().getResponse()
        // .getContentAsString();
        mvc.perform( get( "/inventory" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        mvc.perform( get( "/makecoffee" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();

    }

    @Test
    @Transactional
    public void editIngredients () throws Exception {
        service.deleteAll();
        final Recipe recipe = new Recipe();
        final Ingredient chocolateR = new Ingredient( "Chocolate", 5 );
        final Ingredient coffeeR = new Ingredient( "Coffee", 5 );
        final Ingredient milkR = new Ingredient( "Milk", -5 );
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( chocolateR );
        recipe.addIngredient( coffeeR );
        recipe.addIngredient( milkR );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( chocolateR ) ) ).andExpect( status().isOk() ).andDo( print() );
        mvc.perform( put( "/api/v1/ingredients/Chocolate" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( milkR ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );
        mvc.perform( put( "/api/v1/ingredients/Fake" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 0 ) ) ).andExpect( status().is4xxClientError() ).andDo( print() );

        mvc.perform( get( "/editRecipeNew" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        mvc.perform( get( "/updateInventory" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        mvc.perform( get( "/ingredientAdded" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();

        // System.out.print( recipe2 );

    }

}
