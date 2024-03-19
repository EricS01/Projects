package edu.ncsu.csc.iTrust2.forms;

import org.hibernate.validator.constraints.Length;

import edu.ncsu.csc.iTrust2.models.PatientAdvocate;

/**
 * Form for user to fill out to add a Patient Advocate to the system.
 */
public class PatientAdvocateForm extends PersonnelForm {
    /**
     * Preferred name of the PatientAdvocate
     */
    @Length ( max = 20 )
    private String preferredName;

    /**
     * Middle name of the PatientAdvocate
     */
    @Length ( max = 30 )
    private String middleName;

    /**
     * For Spring
     */
    public PatientAdvocateForm () {

    }

    /**
     * Populate the patient advocate form from a patient advocate object
     *
     * @param patientAdvocate
     *            the patient advocate object to set the form with
     */
    public PatientAdvocateForm ( final PatientAdvocate patientAdvocate ) {
        if ( patientAdvocate == null ) {
            return;
        }

        setUsername( patientAdvocate.getUsername() );
        setFirstName( patientAdvocate.getFirstName() );
        setMiddleName( patientAdvocate.getMiddleName() );
        setLastName( patientAdvocate.getLastName() );
        setPreferredName( patientAdvocate.getPreferredName() );
        setAddress1( patientAdvocate.getAddress1() );
        setAddress2( patientAdvocate.getAddress2() );
        setCity( patientAdvocate.getCity() );

        if ( patientAdvocate.getState() != null ) {
            setState( patientAdvocate.getState().toString() );
        }

        setZip( patientAdvocate.getZip() );
        setPhone( patientAdvocate.getPhone() );
        setEmail( patientAdvocate.getEmail() );
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
}
