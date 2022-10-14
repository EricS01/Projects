package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                   id;

    /**
     * List of Ingredients in Inventory
     */
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<Ingredient> ingredients;

    /**
     * Used to create an inventory with a blank list of Ingredients
     */
    public Inventory () {
        ingredients = new ArrayList<Ingredient>();
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {
        for ( final Ingredient i : r.getIngredients() ) {
            for ( final Ingredient j : ingredients ) {
                if ( i.getId() == j.getId() ) {
                    if ( i.getAmount() > j.getAmount() ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {
        if ( enoughIngredients( r ) ) {
            for ( final Ingredient i : r.getIngredients() ) {
                for ( final Ingredient j : ingredients ) {
                    if ( i.getName().equals( j.getName() ) ) {
                        j.setAmount( j.getAmount() - i.getAmount() );
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds an ingredient to the list
     *
     * @return true if successful, false if not
     */
    public boolean addNewIngredient ( final Ingredient ingredient ) {
        if ( ingredient.getAmount() < 0 ) {
            return false;
        }

        for ( final Ingredient i : ingredients ) {
            // Duplicate name error
            if ( i.getName().equals( ingredient.getName() ) ) {
                return false;
            }
        }

        ingredients.add( ingredient );
        return true;
    }

    /**
     * Adds ingredients to the inventory
     *
     * @return true if successful, false if not
     */
    public boolean addIngredients ( final List<Ingredient> newIngredients ) {
        for ( final Ingredient i : newIngredients ) {
            if ( i.getAmount() < 0 ) {
                return false;
            }

            for ( final Ingredient j : ingredients ) {
                if ( i.getName().equals( j.getName() ) ) {
                    j.setAmount( j.getAmount() + i.getAmount() );
                }
            }
        }
        return true;
    }

    /**
     * Getter method for the list of Ingredients in the inventory
     *
     * @return the list of ingredients in the inventory
     */
    public List<Ingredient> getIngredients () {
        return ingredients;
    }

    /**
     * Returns a string describing the current contents of the inventory.
     *
     * @return String
     */
    @Override
    public String toString () {
        final StringBuffer buf = new StringBuffer();
        return buf.toString();
    }

}
