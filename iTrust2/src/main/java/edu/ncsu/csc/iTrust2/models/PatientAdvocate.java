package edu.ncsu.csc.iTrust2.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.ncsu.csc.iTrust2.forms.PatientAdvocateForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.State;

/**
 * PatientAdvocate class
 *
 * @author tanvirarora
 *
 */
@Entity
public class PatientAdvocate extends Personnel {
    /**
     * Middle name of the PatientAdvocate
     */
    @Length ( max = 30 )
    private String                           middleName;

    /**
     * Preferred name of the PatientAdvocate
     */
    @Length ( max = 20 )
    private String                           preferredName;

    /** List of Patients associated with this PatientAdvocate */
    @OneToMany ( cascade = CascadeType.ALL )
    @LazyCollection ( LazyCollectionOption.FALSE )
    @JsonIgnore
    private List<PatientAdvocateAssociation> associatedPatients;

    /**
     * For Hibernate
     */
    public PatientAdvocate () {

    }

    /**
     * Creates a PatientAdvocate from the provided UserForm
     *
     * @param uf
     *            UserForm to build a PatientAdvocate from
     */
    public PatientAdvocate ( final UserForm form ) {
        super( form );
        if ( getRoles().contains( Role.ROLE_HCP ) ) {
            throw new IllegalArgumentException( "Attempted to create a HCP PatientAdvocate!" );
        }
        associatedPatients = new ArrayList<>();
    }

    /**
     * Creates a PatientAdvocate from the provided UserForm
     *
     * @param uf
     *            UserForm to build a PatientAdvocate from
     */
    public PatientAdvocate ( final PatientAdvocateForm form ) {
        super( form );
        setMiddleName( form.getMiddleName() );
        setPreferredName( form.getPreferredName() );
        associatedPatients = new ArrayList<>();
    }

    /**
     * Updates this PatientAdvocate based on the provided UserForm
     *
     * @param patientAdvocate
     *            Form to update from
     * @return updated patient advocate
     */
    public PatientAdvocate update ( final PatientAdvocateForm patientAdvocate ) {
        setUsername( patientAdvocate.getUsername() );
        setFirstName( patientAdvocate.getFirstName() );
        setMiddleName( patientAdvocate.getMiddleName() );
        setLastName( patientAdvocate.getLastName() );
        setPreferredName( patientAdvocate.getPreferredName() );
        setAddress1( patientAdvocate.getAddress1() );
        setAddress2( patientAdvocate.getAddress2() );
        setCity( patientAdvocate.getCity() );

        if ( patientAdvocate.getState() != null ) {
            setState( State.valueOf( patientAdvocate.getState() ) );
        }

        setZip( patientAdvocate.getZip() );
        setPhone( patientAdvocate.getPhone() );
        setEmail( patientAdvocate.getEmail() );
        final PasswordEncoder pe = new BCryptPasswordEncoder();
        setPassword( pe.encode( patientAdvocate.getPassword() ) );

        return this;
    }

    /**
     * Get the middle name of the PatientAdvocate
     *
     * @return the middle name
     */
    public String getMiddleName () {
        return middleName;
    }

    /**
     * Set the middle name of the PatientAdvocate
     *
     * @param middleName
     *            the name to set
     */
    public void setMiddleName ( final String middleName ) {
        this.middleName = middleName;
    }

    /**
     * Get the preferred name of the PatientAdvocate
     *
     * @return the preferred name
     */
    public String getPreferredName () {
        return preferredName;
    }

    /**
     * Set the preferred name of the PatientAdvocate
     *
     * @param preferredName
     *            the name to set
     */
    public void setPreferredName ( final String preferredName ) {
        this.preferredName = preferredName;
    }

    /**
     * Get associated patient list
     *
     * @return the associatedPatientAdvocates
     */
    public List<PatientAdvocateAssociation> getAssociatedPatients () {
        return associatedPatients;
    }

    /**
     * Add associated patient list
     *
     * @param associatedPatientAdvocate
     *            the associatedPatientAdvocates to set
     */
    public void addAssociatedPatients ( final PatientAdvocateAssociation associatedPatientAdvocate ) {
        associatedPatients.add( associatedPatientAdvocate );
    }

    /**
     * Remove associated patient list
     *
     * @param associatedPatientAdvocate
     *            the associatedPatientAdvocates to set
     */
    public void removeAssociatedPatients ( final PatientAdvocateAssociation associatedPatientAdvocate ) {
        associatedPatients.remove( associatedPatientAdvocate );
    }
}
