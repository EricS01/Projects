package edu.ncsu.csc.iTrust2.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

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
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PatientAdvocate;
import edu.ncsu.csc.iTrust2.models.PatientAdvocateAssociation;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.PatientAdvocateAssociationService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;

/**
 * Test for API functionality for interacting with PatientAdvocateAssociation
 *
 * @author jalong4
 *
 */
@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles ( { "test" } )
public class APIPatientAdvocateAssociationControllerTest {
    @Autowired
    private MockMvc                           mvc;

    @Autowired
    private UserService<PatientAdvocate>      patientAdvocateService;

    @Autowired
    private PatientService<Patient>           patientService;

    @Autowired
    private PatientAdvocateAssociationService patientAdvocateAssociationService;

    private static final String               USER_1 = "demoTestUser1";

    private static final String               USER_2 = "demoTestUser2";

    private static final String               USER_3 = "demoTestUser3";

    private static final String               USER_4 = "demoTestUser4";

    private static final String               PW     = "123456";

    @BeforeEach
    public void setup () {
        patientAdvocateService.deleteAll();
        patientService.deleteAll();
        patientAdvocateAssociationService.deleteAll();
    }

    /**
     * Test a simple association scenario
     */
    @Test
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    @Transactional
    public void testSimpleAssociation () throws Exception {
        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );

        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_2, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );

        final Map<String, String> payload = new HashMap<>();
        payload.put( "patient", USER_1 );
        payload.put( "patientAdvocate", USER_2 );

        // Create a new association between the patient and patient advocate
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isOk() );

        final String associatedPatients = mvc.perform( get( "/api/v1/patients/associated/" + USER_1 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final String associatedPatientAdvocates = mvc.perform( get( "/api/v1/patient_advocate/associated/" + USER_2 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser2\"" ) );
        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser1\"" ) );
        assertTrue( associatedPatients.contains( "\"username\":\"demoTestUser2\"" ) );
        assertTrue( associatedPatients.contains( "\"username\":\"demoTestUser1\"" ) );
        final PatientAdvocateAssociation assoc = patientAdvocateAssociationService.findByUsernames( USER_1, USER_2 );
        Assertions.assertEquals( USER_1, assoc.getPatient().getUsername() );
    }

    /**
     * Test a advanced association scenario
     */
    @Test
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    @Transactional
    public void testAdvancedAssociation () throws Exception {
        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );

        final Patient p2 = new Patient( new UserForm( USER_2, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p2 );

        final Patient p3 = new Patient( new UserForm( USER_3, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p3 );

        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_4, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );

        Map<String, String> payload = new HashMap<>();
        payload.put( "patient", USER_1 );
        payload.put( "patientAdvocate", USER_4 );

        // Create a new association between the patient and patient advocate
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isOk() );

        payload = new HashMap<>();
        payload.put( "patient", USER_2 );
        payload.put( "patientAdvocate", USER_4 );

        // Create a new association between the patient and patient advocate
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isOk() );

        payload = new HashMap<>();
        payload.put( "patient", USER_3 );
        payload.put( "patientAdvocate", USER_4 );

        // Create a new association between the patient and patient advocate
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isOk() );

        final String associatedPatients1 = mvc.perform( get( "/api/v1/patients/associated/" + USER_1 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final String associatedPatients2 = mvc.perform( get( "/api/v1/patients/associated/" + USER_2 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final String associatedPatients3 = mvc.perform( get( "/api/v1/patients/associated/" + USER_3 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final String associatedPatientAdvocates = mvc.perform( get( "/api/v1/patient_advocate/associated/" + USER_4 ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser4\"" ) );
        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser1\"" ) );
        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser2\"" ) );
        assertTrue( associatedPatientAdvocates.contains( "\"username\":\"demoTestUser3\"" ) );

        assertTrue( associatedPatients1.contains( "\"username\":\"demoTestUser4\"" ) );
        assertTrue( associatedPatients1.contains( "\"username\":\"demoTestUser1\"" ) );

        assertTrue( associatedPatients2.contains( "\"username\":\"demoTestUser4\"" ) );
        assertTrue( associatedPatients2.contains( "\"username\":\"demoTestUser2\"" ) );

        assertTrue( associatedPatients3.contains( "\"username\":\"demoTestUser4\"" ) );
        assertTrue( associatedPatients3.contains( "\"username\":\"demoTestUser3\"" ) );
    }

    /**
     * Test an error association scenario
     */
    @Test
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    @Transactional
    public void testErrorAssociation () throws Exception {
        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );

        final Patient p2 = new Patient( new UserForm( USER_2, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p2 );

        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_3, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );

        final PatientAdvocate pa2 = new PatientAdvocate( new UserForm( USER_4, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa2 );

        Map<String, String> payload = new HashMap<>();
        payload.put( "patient", USER_1 );
        payload.put( "patientAdvocate", USER_2 );

        // Trying to associate two patients
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().is4xxClientError() );

        payload = new HashMap<>();
        payload.put( "patient", "not real" );
        payload.put( "patientAdvocate", USER_4 );

        // Trying to associate a patient advocate and a non-existing patient
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isNotFound() );

        payload = new HashMap<>();
        payload.put( "patient", USER_3 );
        payload.put( "patientAdvocate", "not real" );

        // Trying to associate a patient and a non-existing patient advocate
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isNotFound() );

        payload = new HashMap<>();
        payload.put( "not", USER_1 );
        payload.put( "correct", USER_4 );

        // Trying to associate with incorrect payload format
        mvc.perform( put( "/api/v1/patient_advocate_association" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( payload ) ) )
                .andExpect( status().isBadRequest() );

    }
}
