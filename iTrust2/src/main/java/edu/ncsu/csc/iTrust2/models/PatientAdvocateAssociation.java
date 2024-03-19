package edu.ncsu.csc.iTrust2.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Represents a association relationship between a PatientAdvocate and a
 * Patient.
 *
 * @author jalong4
 */
@Entity
public class PatientAdvocateAssociation extends DomainObject {
    /**
     * ID of this patient advocate association
     */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long            id;

    /**
     * PatientAdvocate associated with the Patient
     */
    @ManyToOne
    private PatientAdvocate patientAdvocate;

    /**
     * Patient associated with the PatientAdvocate
     */
    @ManyToOne
    private Patient         patient;

    /**
     * Can the PatientAdvocate view the Patient's prescriptions
     */
    private boolean         viewPrescriptions;

    /**
     * Can the PatientAdvocate view the Patient's billing information
     */
    private boolean         viewBilling;

    /**
     * Can the PatientAdvocate view the Patient's office visits
     */
    private boolean         viewOfficeVisits;

    /**
     * For Hibernate
     */
    public PatientAdvocateAssociation () {

    }

    /**
     * Create a new PatientAdvocateAssociation based on the PatientAdvocate and
     * Patient
     *
     * @param patientAdvocate
     *            patient advocate to add
     * @param patient
     *            patient to add
     */
    public PatientAdvocateAssociation ( final PatientAdvocate patientAdvocate, final Patient patient ) {
        setPatientAdvocate( patientAdvocate );
        setPatient( patient );
    }

    /**
     * Get the PatientAdvocate
     *
     * @return the patientAdvocate
     */
    public PatientAdvocate getPatientAdvocate () {
        return patientAdvocate;
    }

    /**
     * Set the PatientAdvocate
     *
     * @param patientAdvocate
     *            the patientAdvocate to set
     */
    public void setPatientAdvocate ( final PatientAdvocate patientAdvocate ) {
        this.patientAdvocate = patientAdvocate;
    }

    /**
     * Get the Patient
     *
     * @return the patient
     */
    public Patient getPatient () {
        return patient;
    }

    /**
     * Set the Patient
     *
     * @param patient
     *            the patient to set
     */
    public void setPatient ( final Patient patient ) {
        this.patient = patient;
    }

    /**
     * Get the prescriptions boolean
     *
     * @return the viewPrescriptions
     */
    public boolean isViewPrescriptions () {
        return viewPrescriptions;
    }

    /**
     * Set the prescriptions boolean
     *
     * @param viewPrescriptions
     *            the viewPrescriptions to set
     */
    public void setViewPrescriptions ( final boolean viewPrescriptions ) {
        this.viewPrescriptions = viewPrescriptions;
    }

    /**
     * Get the billing boolean
     *
     * @return the viewBilling
     */
    public boolean isViewBilling () {
        return viewBilling;
    }

    /**
     * Set the billing boolean
     *
     * @param viewBilling
     *            the viewBilling to set
     */
    public void setViewBilling ( final boolean viewBilling ) {
        this.viewBilling = viewBilling;
    }

    /**
     * Get the office visits boolean
     *
     * @return the viewOfficeVisits
     */
    public boolean isViewOfficeVisits () {
        return viewOfficeVisits;
    }

    /**
     * Set the office visits boolean
     *
     * @param viewOfficeVisits
     *            the viewOfficeVisits to set
     */
    public void setViewOfficeVisits ( final boolean viewOfficeVisits ) {
        this.viewOfficeVisits = viewOfficeVisits;
    }

    @Override
    public Serializable getId () {
        return id;
    }
}
