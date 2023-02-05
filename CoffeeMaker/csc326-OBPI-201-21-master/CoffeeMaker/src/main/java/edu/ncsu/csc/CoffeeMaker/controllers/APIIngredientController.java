package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

/**
 * This class is responsible for receiving and handling http requests related to
 * ingredient functionality.
 *
 * The methods in this class are based primarily on those in the
 * APIRecipeController.java class by Kai Presler-Marshall and Michelle Lemons.
 *
 * @author Cameron Himes
 * @author Eric Samue
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIIngredientController extends APIController {
    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService service;

    /**
     * REST API method to provide GET access to a single ingredient in the
     * system
     *
     * @return JSON representation of the ingredient with the given name
     */
    @GetMapping ( BASE_PATH + "/ingredients/{name}" )
    public Ingredient getIngredient ( @PathVariable final String name ) {
        return service.findByName( name );
    }

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getIngredients () {
        return service.findAll();
    }

    /**
     * REST API method to provide POST access to the Ingredient model. This is
     * used to create a new Ingredient by automatically converting the JSON
     * RequestBody provided to a Ingredient object. Invalid JSON will fail.
     *
     * @param ingredient
     *            The valid Ingredient to be saved.
     * @return ResponseEntity indicating success if the Ingredient could be
     *         saved or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/ingredients" )
    public ResponseEntity createIngredient ( @RequestBody final Ingredient ingredient ) {
        if ( ingredient.getAmount() < 0 ) {
            return new ResponseEntity( errorResponse( "Ingredient amount must be a positive integer." ),
                    HttpStatus.BAD_REQUEST );
        }

        if ( null != service.findByName( ingredient.getName() ) ) {
            return new ResponseEntity(
                    errorResponse( "Ingredient with the name " + ingredient.getName() + " already exists." ),
                    HttpStatus.CONFLICT );
        }

        // Save in the Ingredient table
        service.save( ingredient );
        return new ResponseEntity( successResponse( "Ingredient successfully created" ), HttpStatus.OK );
    }

    /**
     * REST API method to allow deleting an Ingredient
     *
     * @param name
     *            The name of the Ingredient to delete
     * @return Success if the ingredient could be deleted; an error if the
     *         ingredient does not exist
     */
    @DeleteMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity deleteIngredient ( @PathVariable final String name ) {
        final Ingredient ingredient = service.findByName( name );
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for name " + name ), HttpStatus.NOT_FOUND );
        }
        service.delete( ingredient );

        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }

    /**
     * REST API method to provide PUT access to the Ingredient model. This is
     * used to update an existing ingredient
     *
     * @param ingredient
     *            The valid Ingredient to be saved in place of an existing
     *            ingredient.
     * @return ResponseEntity indicating success if the Ingredient could be
     *         updated or an error if it could not be
     */
    @PutMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity editIngredient ( @RequestBody final Ingredient ingredient ) {
        if ( ingredient.getAmount() < 0 ) {
            return new ResponseEntity( errorResponse( "Ingredient amount must be a positive integer." ),
                    HttpStatus.BAD_REQUEST );
        }

        if ( null == service.findByName( ingredient.getName() ) ) {
            return new ResponseEntity(
                    errorResponse( "Ingredient with the name " + ingredient.getName() + " does not exist." ),
                    HttpStatus.CONFLICT );
        }

        service.save( ingredient );
        return new ResponseEntity( successResponse( "Ingredient successfully updated." ), HttpStatus.OK );
    }
}
