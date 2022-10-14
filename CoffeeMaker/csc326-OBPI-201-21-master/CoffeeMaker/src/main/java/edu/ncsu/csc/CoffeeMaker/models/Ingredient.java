package edu.ncsu.csc.CoffeeMaker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class Ingredient extends DomainObject {

    /**
     * Id for the Ingredient
     */
    @Id
    @GeneratedValue
    private Long           id;

    /**
     * Amount of Ingredient
     */
    @Min( 0 )
    private Integer        amount;
    
    /**
     * Name of Ingredient
     */
    private String		   name;
    
    /**
     * Default constructor for the Ingredient
     */
    public Ingredient ( ) {
        super();
        this.name = "";
        this.amount = 0;
    }

    /**
     * Constructor with parameters for the Ingredient
     */
    public Ingredient ( final String name, final Integer amount ) {
        super();
        this.name = name;
        this.amount = amount;
    }

    /**
     * Getter method for amount
     *
     * @return amount for Ingredient
     */
    public Integer getAmount () {
        return amount;
    }

    /**
     * Setter method for amount
     *
     * @param amount
     *            Amount for Ingredient to be set
     */
    public void setAmount ( final Integer amount ) {
        this.amount = amount;
    }

    /**
     * Setter method for Ingredient
     *
     * @param id
     *            id for Ingredient to be set
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Getter method for ID
     */
    @Override
    public Long getId () {
        return this.id;
    }

    /**
     * Getter method for the Ingredient's name
     * @return the Ingredient's name
     */
	public String getName() {
		return name;
	}

	/**
     * Setter method for the Ingredient's name
     * @param name the Ingredient's name
     */
	public void setName(String name) {
		this.name = name;
	}

}
