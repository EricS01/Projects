package edu.ncsu.csc.iTrust2.controllers.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import edu.ncsu.csc.iTrust2.models.PatientAdvocateAssociation;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.PatientAdvocateAssociationService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Controller for the PatientAdvocate landing screen
 *
 * @author Kai Presler-Marshall
 *
 */
@Controller
public class PatientAdvocateController {

    /**
     * Used to get the association when a PatientAdvocate views a Patient.
     */
    @Autowired
    private PatientAdvocateAssociationService patientAdvocateAssociationService;

    /**
     * Landing screen for a PatientAdvocate when they log in
     *
     * @param model
     *            The data from the front end
     * @return The page to show to the user
     */
    @RequestMapping ( value = "patientadvocate/index" )
    @PreAuthorize ( "hasRole('ROLE_PATIENTADVOCATE')" )
    public String index ( final Model model ) {
        return Role.ROLE_PATIENTADVOCATE.getLanding();
    }

    /**
     * Page for choosing Patient to view
     *
     * @param model
     *            The data from the front end
     * @return The page to show to the user
     */
    @RequestMapping ( value = "patientadvocate/viewPatients" )
    @PreAuthorize ( "hasRole('ROLE_PATIENTADVOCATE')" )
    public String viewPatients ( final Model model ) {
        return "patientadvocate/viewPatients";
    }

    /**
     * Create a page for the patient to view bills
     *
     * @param id
     *            the id of the patient to view
     * @param model
     *            data for front end
     * @return The page for the patient to view bills history
     */
    @GetMapping ( value = "patientadvocate/viewBills/{patientID}" )
    @PreAuthorize ( "hasRole('ROLE_PATIENTADVOCATE')" )
    public String viewBills ( @PathVariable ( value = "patientID" ) final String id, final Model model )
            throws JsonProcessingException {
        final PatientAdvocateAssociation association = patientAdvocateAssociationService.findByUsernames( id,
                LoggerUtil.currentUser() );

        if ( association == null || !association.isViewBilling() ) {
            return null;
        }

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        model.addAttribute( "association", ow.writeValueAsString( association ) );
        return "patientadvocate/viewBills";
    }

    /**
     * Create a page for the patient to view office visits
     *
     * @param id
     *            the id of the patient to view
     * @param model
     *            data for front end
     * @return The page for the patient to view office visits history
     */
    @GetMapping ( value = "patientadvocate/viewOfficeVisits/{patientID}" )
    @PreAuthorize ( "hasRole('ROLE_PATIENTADVOCATE')" )
    public String viewOfficeVisits ( @PathVariable ( value = "patientID" ) final String id, final Model model )
            throws JsonProcessingException {
        final PatientAdvocateAssociation association = patientAdvocateAssociationService.findByUsernames( id,
                LoggerUtil.currentUser() );

        if ( association == null || !association.isViewOfficeVisits() ) {
            return null;
        }

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        model.addAttribute( "association", ow.writeValueAsString( association ) );
        return "patientadvocate/viewOfficeVisits";
    }

    /**
     * Create a page for the patient to view prescriptions
     *
     * @param id
     *            the id of the patient to view
     * @param model
     *            data for front end
     * @return The page for the patient to view prescriptions history
     */
    @GetMapping ( value = "patientadvocate/viewPrescriptions/{patientID}" )
    @PreAuthorize ( "hasRole('ROLE_PATIENTADVOCATE')" )
    public String viewPrescriptions ( @PathVariable ( value = "patientID" ) final String id, final Model model )
            throws JsonProcessingException {
        final PatientAdvocateAssociation association = patientAdvocateAssociationService.findByUsernames( id,
                LoggerUtil.currentUser() );

        if ( association == null || !association.isViewPrescriptions() ) {
            return null;
        }

        final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        model.addAttribute( "association", ow.writeValueAsString( association ) );
        return "patientadvocate/viewPrescriptions";
    }
}
