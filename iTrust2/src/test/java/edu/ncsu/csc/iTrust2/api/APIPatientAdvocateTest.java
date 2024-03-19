package edu.ncsu.csc.iTrust2.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.PatientAdvocateForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.PatientAdvocate;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.State;
import edu.ncsu.csc.iTrust2.services.UserService;

/**
 * Test for API functionality for interacting with PatientAdvocate
 *
 * @author jalong4
 *
 */
@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles ( { "test" } )
public class APIPatientAdvocateTest {

    @Autowired
    private MockMvc                      mvc;

    @Autowired
    private UserService<PatientAdvocate> service;

    /**
     * Sets up test
     */
    @BeforeEach
    public void setup () {

        service.deleteAll();
    }

    /**
     * Tests getting a non existent personnel and ensures that the correct
     * status is returned.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    public void testGetNonExistentPersonnel () throws Exception {
        mvc.perform( get( "/api/v1/patient_advocate/-1" ) ).andExpect( status().isNotFound() );
    }

    /**
     * Tests getting a non existent personnel and ensures that the correct
     * status is returned.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "advocate", roles = { "PATIENTADVOCATE" } )
    public void testCurrentAdvocate () throws Exception {
        mvc.perform( get( "/api/v1/current_patient_advocate" ) ).andExpect( status().isNotFound() );
        final PatientAdvocate hcp = new PatientAdvocate( new UserForm( "pa", "123456", Role.ROLE_PATIENTADVOCATE, 1 ) );

        service.save( hcp );
        final PatientAdvocateForm patientAdvocate = new PatientAdvocateForm();

        patientAdvocate.setAddress1( "1 Test Street" );
        patientAdvocate.setAddress2( "Address Part 2" );
        patientAdvocate.setCity( "Prag" );
        patientAdvocate.setEmail( "pa@itrust.cz" );
        patientAdvocate.setFirstName( "Test" );
        patientAdvocate.setMiddleName( "Middle" );
        patientAdvocate.setPreferredName( "PreferredName" );
        patientAdvocate.setLastName( "PA" );
        patientAdvocate.setPhone( "123-456-7890" );
        patientAdvocate.setUsername( "pa" );
        patientAdvocate.setState( State.NC.toString() );
        patientAdvocate.setZip( "27514" );
        mvc.perform( get( "/api/v1/patient_advocate/associated/pa" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/api/v1/patient_advocate/associated/pa" ) ).andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/officevisits/patientadvocate/pa" ) ).andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/bills/pa" ) ).andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/bills/patientadvocate/pa" ) ).andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/prescriptions/patientadvocate/pa" ) ).andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/patient_advocate/associated/" ) ).andExpect( status().isNotFound() );
        mvc.perform( get( "/api/v1/patientadvocate/viewBills/pa" ) ).andExpect( status().isNotFound() );

        // final PatientAdvocate hcp2 = new PatientAdvocate(
        // new UserForm( "ha", "123456", Role.ROLE_PATIENTADVOCATE, 1 ) );
        //
        // service.save( hcp2 );
        final PatientAdvocateForm patientAdvocate2 = new PatientAdvocateForm();

        patientAdvocate2.setAddress1( "2 Test Street" );
        patientAdvocate2.setAddress2( "Address Part 2" );
        patientAdvocate2.setCity( "Prag" );
        patientAdvocate2.setEmail( "pa@itrust.cz" );
        patientAdvocate2.setFirstName( "Test" );
        patientAdvocate2.setMiddleName( "Middle" );
        patientAdvocate2.setPreferredName( "PreferredName" );
        patientAdvocate2.setLastName( "PA" );
        patientAdvocate2.setPhone( "123-456-7890" );
        patientAdvocate2.setUsername( "pa" );
        patientAdvocate2.setState( State.NC.toString() );
        patientAdvocate2.setZip( "27514" );
        // mvc.perform( put( "/api/v1/users/pa/adv" ).with( csrf()
        // ).contentType( MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( patientAdvocate2 ) ) ).andExpect(
        // status().isOk() );

        // mvc.perform( get( "/api/v1/patientadvocate/viewBills/pa" )
        // ).andExpect( status().isOk() );
        // mvc.perform( get( "/api/v1/patientadvocate/viewOfficeVisits/pa" )
        // ).andExpect( status().isOk() );
        // mvc.perform( get( "/api/v1/patientadvocate/viewPrescriptions/pa" )
        // ).andExpect( status().isOk() );

    }

    /**
     * Tests adding a PatientAdvocate personnel class
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    public void testPatientAdvocateAPI () throws Exception {

        final PatientAdvocate hcp = new PatientAdvocate( new UserForm( "pa", "123456", Role.ROLE_PATIENTADVOCATE, 1 ) );

        service.save( hcp );

        final PatientAdvocateForm patientAdvocate = new PatientAdvocateForm();

        patientAdvocate.setAddress1( "1 Test Street" );
        patientAdvocate.setAddress2( "Address Part 2" );
        patientAdvocate.setCity( "Prag" );
        patientAdvocate.setEmail( "pa@itrust.cz" );
        patientAdvocate.setFirstName( "Test" );
        patientAdvocate.setMiddleName( "Middle" );
        patientAdvocate.setPreferredName( "PreferredName" );
        patientAdvocate.setPassword( "asdf" );
        patientAdvocate.setPassword2( "asdf" );
        patientAdvocate.setLastName( "PA" );
        patientAdvocate.setPhone( "123-456-7890" );
        patientAdvocate.setUsername( "pa" );
        patientAdvocate.setState( State.NC.toString() );
        patientAdvocate.setZip( "27514" );

        // Should be able to update with the new values
        mvc.perform( put( "/api/v1/patient_advocate/pa" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patientAdvocate ) ) ).andExpect( status().isOk() );

        final PatientAdvocate retrieved = (PatientAdvocate) service.findByName( "pa" );

        Assertions.assertEquals( "Prag", retrieved.getCity() );
        Assertions.assertEquals( "Middle", retrieved.getMiddleName() );
        Assertions.assertEquals( "PreferredName", retrieved.getPreferredName() );
        Assertions.assertEquals( State.NC, retrieved.getState() );

        mvc.perform( get( "/api/v1/patient_advocate" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) );

        mvc.perform( get( "/api/v1/patient_advocate/pa" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) );

        // Edit with wrong url ID should fail
        mvc.perform( put( "/api/v1/patient_advocate/badpa" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patientAdvocate ) ) ).andExpect( status().isNotFound() );

        mvc.perform( get( "/api/v1/patient_advocate/associated/pa" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) );

        // Edit with matching, but nonexistent ID should fail.
        patientAdvocate.setUsername( "badpa" );
        mvc.perform( put( "/api/v1/patient_advocate/badpa" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patientAdvocate ) ) ).andExpect( status().is4xxClientError() );

        // mvc.perform( get( "/api/v1/patients/associated" ) ).andExpect(
        // status().isOk() )
        // .andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE )
        // );

    }

}
