package edu.ncsu.csc.CoffeeMaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for the URL mappings for CoffeeMaker. The controller returns
 * the approprate HTML page in the /src/main/resources/templates folder. For a
 * larger application, this should be split across multiple controllers.
 *
 * @author Kai Presler-Marshall
 */
@Controller
public class MappingController {

    /**
     * On a GET request to /index, the IndexController will return
     * /src/main/resources/templates/index.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/index", "/" } )
    public String index ( final Model model ) {
        return "index";
    }

    /**
     * On a GET request to /recipe, the RecipeController will return
     * /src/main/resources/templates/recipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/newRecipe", "/newRecipe.html" } )
    public String addRecipePage ( final Model model ) {
        return "newRecipe";
    }

    @GetMapping ( { "/inventoryNew", "/inventoryNew.html" } )
    public String inventoryNew ( final Model model ) {
        return "inventoryNew";
    }

    // @GetMapping ( { "/recipe", "/recipe.html" } )
    // public String recipePage ( final Model model ) {
    // return "recipe";
    // }
    //
    // @GetMapping ( { "/recipeTooMany", "/recipeTooMany.html" } )
    // public String recipeTooMany ( final Model model ) {
    // return "recipeTooMany";
    // }

    @GetMapping ( { "/newRecipeSuccess", "/newRecipeSuccess.html" } )
    public String newRecipeSuccess ( final Model model ) {
        return "newRecipeSuccess";
    }

    // @GetMapping ( { "/ingredient.html", "/ingredient.html" } )
    // public String ingredient ( final Model model ) {
    // return "ingredient";
    // }
    //
    // @GetMapping ( { "/invalidRecipePrice", "/invalidRecipePrice.html" } )
    // public String invalidRecipePrice ( final Model model ) {
    // return "invalidRecipePrice";
    // }

    // @GetMapping ( { "/ingredientDuplicate", "/ingredientDuplicate.html" } )
    // public String ingredientDuplicate ( final Model model ) {
    // return "ingredientDuplicate";
    // }
    //
    // @GetMapping ( { "/ingredientInvalidUnits", "/ingredientInvalidUnits.html"
    // } )
    // public String ingredientInvalidUnits ( final Model model ) {
    // return "ingredientInvalidUnits";
    // }

    @GetMapping ( { "/ingredientAdded", "/ingredientAdded.html" } )
    public String ingredientAdded ( final Model model ) {
        return "ingredientAdded";
    }

    @GetMapping ( { "/editRecipeNew", "/editRecipeNew.html" } )
    public String editRecipeNew ( final Model model ) {
        return "editRecipeNew";
    }

    @GetMapping ( { "/updateInventory", "/updateInventory.html" } )
    public String updateInventory ( final Model model ) {
        return "updateInventory";
    }

    /**
     * On a GET request to /deleterecipe, the DeleteRecipeController will return
     * /src/main/resources/templates/deleterecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/deleterecipe", "/deleterecipe.html" } )
    public String deleteRecipeForm ( final Model model ) {
        return "deleterecipe";
    }

    /**
     * On a GET request to /editrecipe, the EditRecipeController will return
     * /src/main/resources/templates/editrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/editrecipe", "/editrecipe.html" } )
    public String editRecipeForm ( final Model model ) {
        return "editrecipe";
    }

    /**
     * Handles a GET request for inventory. The GET request provides a view to
     * the client that includes the list of the current ingredients in the
     * inventory and a form where the client can enter more ingredients to add
     * to the inventory.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/inventory", "/inventory.html" } )
    public String inventoryForm ( final Model model ) {
        return "inventory";
    }

    /**
     * On a GET request to /makecoffee, the MakeCoffeeController will return
     * /src/main/resources/templates/makecoffee.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/makecoffee", "/makecoffee.html" } )
    public String makeCoffeeForm ( final Model model ) {
        return "makecoffee";
    }

}
