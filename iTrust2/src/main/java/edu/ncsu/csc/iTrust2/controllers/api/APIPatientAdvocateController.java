package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.forms.PatientAdvocateForm;
import edu.ncsu.csc.iTrust2.models.PatientAdvocate;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Controller responsible for providing various REST API endpoints for the
 * PatientAdvocate model.
 *
 * @author Kai Presler-Marshall
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIPatientAdvocateController extends APIController {

    /** LoggerUtil */
    @Autowired
    private LoggerUtil                   loggerUtil;

    /** Personnel Service */
    @Autowired
    private UserService<PatientAdvocate> service;

    /**
     * Retrieves and returns a list of all PatientAdvocate stored in the system
     *
     * @return list of PatientAdvocate
     */
    @GetMapping ( BASE_PATH + "/patient_advocate" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_ADMIN')" )
    public List<PatientAdvocate> getPatientAdvocate () {
        return service.findAll().stream()
                .filter( ( final User u ) -> u.getRoles().contains( Role.ROLE_PATIENTADVOCATE ) )
                .collect( Collectors.toList() );
    }

    /**
     * Retrieves and returns the PatientAdvocate with the username provided
     *
     * @param id
     *            The username of the PatientAdvocate to be retrieved, as stored
     *            in the Users table
     * @return response
     */
    @GetMapping ( BASE_PATH + "/patient_advocate/{id}" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_ADMIN')" )
    public ResponseEntity getPatientAdvocate ( @PathVariable ( "id" ) final String id ) {
        final PatientAdvocate patientAdvocate = (PatientAdvocate) service.findByName( id );
        if ( null == patientAdvocate ) {
            return new ResponseEntity( errorResponse( "No patient advocate found for id " + id ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            loggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, LoggerUtil.currentUser() );
            return new ResponseEntity( patientAdvocate, HttpStatus.OK );
        }
    }

    /**
     * If you are logged in as a patient advocate, then you can use this
     * convenience lookup to find your own information without remembering your
     * id. This allows you the shorthand of not having to look up the id in
     * between.
     *
     * @return The patient advocate object for the currently authenticated user.
     */
    @GetMapping ( BASE_PATH + "/current_patient_advocate" )
    @PreAuthorize ( "hasAnyRole('ROLE_PATIENTADVOCATE')" )
    public ResponseEntity getCurrentPatientAdvocate () {
        final String username = LoggerUtil.currentUser();
        final PatientAdvocate patientAdvocate = (PatientAdvocate) service.findByName( username );
        if ( patientAdvocate == null ) {
            return new ResponseEntity( errorResponse( "Could not find a personnel entry for you, " + username ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            loggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, username,
                    "Retrieved demographics for user " + username );
            return new ResponseEntity( patientAdvocate, HttpStatus.OK );
        }
    }

    /**
     * Get a list of all the Patients associated with a PatientAdvocate. The
     * provided ID is the ID of a PatientAdvocate that is in the system.
     *
     * @param id
     *            of the patient advocate
     * @return response
     */
    @GetMapping ( BASE_PATH + "/patient_advocate/associated/" )
    @PreAuthorize ( "hasAnyRole('ROLE_PATIENTADVOCATE', 'ROLE_ADMIN')" )
    public ResponseEntity getAssociatedPatients () {
        final PatientAdvocate patientAdvocate = (PatientAdvocate) service.findByName( LoggerUtil.currentUser() );
        if ( null == patientAdvocate ) {
            return new ResponseEntity( errorResponse( "No patient advocate found for id " + LoggerUtil.currentUser() ),
                    HttpStatus.NOT_FOUND );
        }

        loggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, LoggerUtil.currentUser() );
        return new ResponseEntity( patientAdvocate.getAssociatedPatients(), HttpStatus.OK );
    }

    /**
     * Get a list of all the Patients associated with a PatientAdvocate. The
     * provided ID is the ID of a PatientAdvocate that is in the system.
     *
     * @param id
     *            of the patient advocate
     * @return response
     */
    @GetMapping ( BASE_PATH + "/patient_advocate/associated/{id}" )
    @PreAuthorize ( "hasAnyRole('ROLE_PATIENTADVOCATE', 'ROLE_ADMIN')" )
    public ResponseEntity getAssociatedPatients ( @PathVariable ( "id" ) final String id ) {
        final PatientAdvocate patientAdvocate = (PatientAdvocate) service.findByName( id );
        if ( null == patientAdvocate ) {
            return new ResponseEntity( errorResponse( "No patient advocate found for id " + id ),
                    HttpStatus.NOT_FOUND );
        }

        loggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, patientAdvocate.getUsername() );
        return new ResponseEntity( patientAdvocate.getAssociatedPatients(), HttpStatus.OK );
    }

    /**
     * Updates the PatientAdvocate with the id provided by overwriting it with
     * the new PatientAdvocate record that is provided. If the ID provided does
     * not match the ID set in the Patient provided, the update will not take
     * place
     *
     * @param id
     *            The username of the PatientAdvocate to be updated
     * @param form
     *            The updated PatientAdvocate to save
     * @return response
     */
    @PutMapping ( BASE_PATH + "/patient_advocate/{id}" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_ADMIN')" )
    public ResponseEntity updatePatientAdvocate ( @PathVariable final String id,
            @RequestBody final PatientAdvocateForm form ) {

        final PatientAdvocate fromDb = (PatientAdvocate) service.findByName( id );

        if ( null == fromDb ) {
            return new ResponseEntity( errorResponse( "Could not find a PatientAdvocate entry for you, " + id ),
                    HttpStatus.NOT_FOUND );
        }

        fromDb.update( form );
        if ( ( null != fromDb.getUsername() && !id.equals( fromDb.getUsername() ) ) ) {
            return new ResponseEntity(
                    errorResponse( "The ID provided does not match the ID of the PatientAdvocate provided" ),
                    HttpStatus.CONFLICT );
        }
        try {
            service.save( fromDb );
            loggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS, LoggerUtil.currentUser() );
            return new ResponseEntity( fromDb, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Could not update " + id + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }
}
