package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APICoffeeTest {

    @Autowired
    private MockMvc          mvc;

    @Autowired
    private RecipeService    service;

    @Autowired
    private InventoryService iService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {

        final Inventory ivt = iService.getInventory();
        final Ingredient chocolate = new Ingredient( "Chocolate", 500 );
        final Ingredient coffee = new Ingredient( "Coffee", 500 );
        final Ingredient milk = new Ingredient( "Milk", 500 );
        final Ingredient toffee = new Ingredient( "Toffee", 1 );
        final Ingredient pumpkin = new Ingredient( "Pumpkin", 1 );
        final Ingredient melon = new Ingredient( "Melon", 1 );
        ivt.addNewIngredient( chocolate );
        ivt.addNewIngredient( coffee );
        ivt.addNewIngredient( milk );
        ivt.addNewIngredient( toffee );
        ivt.addNewIngredient( pumpkin );
        ivt.addNewIngredient( melon );

        iService.save( ivt );

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

        final Recipe recipe2 = new Recipe();
        final Ingredient toffeeR = new Ingredient( "Toffee", 5 );
        final Ingredient pumpkinR = new Ingredient( "Pumpkin", 5 );
        final Ingredient melonR = new Ingredient( "Melon", 5 );
        recipe2.setName( "Cafe" );
        recipe2.setPrice( 50 );
        recipe2.addIngredient( toffeeR );
        recipe2.addIngredient( pumpkinR );
        recipe2.addIngredient( melonR );

        service.save( recipe2 );

    }

    @Test
    @Transactional
    public void testPurchaseBeverage1 () throws Exception {

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 60 ) ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.message" ).value( 10 ) );

    }

    @Test
    @Transactional
    public void testPurchaseBeverage2 () throws Exception {
        /* Insufficient amount paid */

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 40 ) ) ).andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough money paid" ) );

    }
    //
    // @Test
    // @Transactional
    // public void testPurchaseBeverage3 () throws Exception {
    // /* Insufficient inventory */
    //
    // final String name = "Cafe";
    //
    // mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name )
    // ).contentType( MediaType.APPLICATION_JSON )
    // .content( TestUtils.asJsonString( 50 ) ) ).andExpect(
    // status().is4xxClientError() )
    // .andExpect( jsonPath( "$.message" ).value( "Not enough inventory" ) );
    //
    // }

}
