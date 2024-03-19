package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PatientAdvocate;
import edu.ncsu.csc.iTrust2.models.PatientAdvocateAssociation;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.PatientAdvocateAssociationService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * API Controller for adding a new PatientAdvocateAssociation
 *
 * @author jalong4
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIPatientAdvocateAssociationController extends APIController {
    /** PatientAdvocate Service */
    @Autowired
    private UserService<PatientAdvocate>      patientAdvocateService;
    /** Patient Service */

    @Autowired
    private PatientService<Patient>           patientService;

    /** PatientAdvocateAssociation Service */
    @Autowired
    private PatientAdvocateAssociationService patientAdvocateAssociationService;

    /**
     * LoggerUtil
     */
    @Autowired
    private LoggerUtil                        loggerUtil;

    /**
     * Add a new PatientAdvocateAssociation to the system given the ID of the
     * Patient and PatientAdvocate. If either of the provided users aren't found
     * or are the wrong kind, an error response is sent back.
     *
     * @param payload
     *            the payload with the patient and patient advocate
     * @return response
     */
    @PutMapping ( BASE_PATH + "/patient_advocate_association" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public ResponseEntity addAssociation ( @RequestBody final Map<String, String> payload ) {

        try {

            if ( !payload.containsKey( "patient" ) || !payload.containsKey( "patientAdvocate" ) ) {
                throw new Exception();
            }

            final String patientUsername = payload.get( "patient" );
            final String patientAdvocateUsername = payload.get( "patientAdvocate" );

            final PatientAdvocate patientAdvocate = (PatientAdvocate) patientAdvocateService
                    .findByName( patientAdvocateUsername );
            if ( patientAdvocate == null ) {
                return new ResponseEntity(
                        errorResponse( "No PatientAdvocate found for username " + patientAdvocateUsername ),
                        HttpStatus.NOT_FOUND );
            }

            final Patient patient = (Patient) patientService.findByName( patientUsername );
            if ( patient == null ) {
                return new ResponseEntity( errorResponse( "No Patient found for username " + patientUsername ),
                        HttpStatus.NOT_FOUND );
            }

            final List<PatientAdvocateAssociation> associations = patientAdvocateAssociationService.findAll();

            for ( final PatientAdvocateAssociation associationFromList : associations ) {
                if ( associationFromList.getPatient().getUsername().compareTo( patientUsername ) == 0
                        && associationFromList.getPatientAdvocate().getUsername()
                                .compareTo( patientAdvocateUsername ) == 0 ) {
                    return new ResponseEntity( errorResponse( "Association Already Exists " ), HttpStatus.BAD_REQUEST );
                }
            }

            final PatientAdvocateAssociation patientAdvocateAssociation = new PatientAdvocateAssociation(
                    patientAdvocate, patient );

            patientAdvocateAssociationService.save( patientAdvocateAssociation );

            patient.addAssociatedPatientAdvocates( patientAdvocateAssociation );
            patientService.save( patient );

            patientAdvocate.addAssociatedPatients( patientAdvocateAssociation );
            patientAdvocateService.save( patientAdvocate );

            loggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS, LoggerUtil.currentUser(),
                    "User with username " + patient.getUsername() + "updated demographics" );

            return new ResponseEntity( patientAdvocateAssociation, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Could not update because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Add a new PatientAdvocateAssociation to the system given the ID of the
     * Patient and PatientAdvocate. If either of the provided users aren't found
     * or are the wrong kind, an error response is sent back.
     *
     * @param payload
     *            the payload with the patient and patient advocate
     * @return response
     */
    @PutMapping ( BASE_PATH + "/update_patient_advocate_association" )
    @PreAuthorize ( "hasAnyRole('ROLE_PATIENT', 'ROLE_ADMIN')" )
    public ResponseEntity updateAssociation ( @RequestBody final Map payload ) {
        try {
            final String patientAdvocateUsername = (String) payload.get( "patientAdvocateUsername" );
            final String patientUsername = (String) payload.get( "patientUsername" );
            final boolean billing = (boolean) payload.get( "billing" );
            final boolean prescriptions = (boolean) payload.get( "prescriptions" );
            final boolean officeVisits = (boolean) payload.get( "officeVisits" );

            final PatientAdvocate patientAdvocate = (PatientAdvocate) patientAdvocateService
                    .findByName( patientAdvocateUsername );
            if ( patientAdvocate == null ) {
                return new ResponseEntity(
                        errorResponse( "No PatientAdvocate found for username " + patientAdvocateUsername ),
                        HttpStatus.NOT_FOUND );
            }

            final Patient patient = (Patient) patientService.findByName( patientUsername );
            if ( patient == null ) {
                return new ResponseEntity( errorResponse( "No Patient found for username " + patientUsername ),
                        HttpStatus.NOT_FOUND );
            }

            final List<PatientAdvocateAssociation> associations = patientAdvocateAssociationService.findAll();

            PatientAdvocateAssociation patientAdvocateAssociation = null;

            for ( final PatientAdvocateAssociation associationFromList : associations ) {
                if ( associationFromList.getPatient().getUsername().compareTo( patientUsername ) == 0
                        && associationFromList.getPatientAdvocate().getUsername()
                                .compareTo( patientAdvocateUsername ) == 0 ) {
                    patientAdvocateAssociation = associationFromList;
                    break;
                }

            }

            patientAdvocateAssociation.setViewBilling( billing );
            patientAdvocateAssociation.setViewOfficeVisits( officeVisits );
            patientAdvocateAssociation.setViewPrescriptions( prescriptions );

            patient.removeAssociatedPatientAdvocates( patientAdvocateAssociation );
            patient.addAssociatedPatientAdvocates( patientAdvocateAssociation );

            patientAdvocate.removeAssociatedPatients( patientAdvocateAssociation );
            patientAdvocate.addAssociatedPatients( patientAdvocateAssociation );

            patientAdvocateAssociationService.save( patientAdvocateAssociation );
            patientAdvocateService.save( patientAdvocate );
            patientService.save( patient );

            return new ResponseEntity( patientAdvocateAssociation, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Could not update because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }
}
