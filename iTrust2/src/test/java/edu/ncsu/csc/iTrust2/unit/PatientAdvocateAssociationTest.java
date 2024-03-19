package edu.ncsu.csc.iTrust2.unit;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.ncsu.csc.iTrust2.TestConfig;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PatientAdvocate;
import edu.ncsu.csc.iTrust2.models.PatientAdvocateAssociation;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.PatientAdvocateAssociationService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;

/**
 * Tests the patient advocate association model
 */
@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
@ActiveProfiles ( { "test" } )
public class PatientAdvocateAssociationTest {
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

    @Transactional
    @Test
    public void testSimpleAssociation () {
        Assertions.assertEquals( 0, patientAdvocateService.count(),
                "There should be no Patient Advocate records in the system" );
        Assertions.assertEquals( 0, patientService.count(), "There should be no Patient records in the system" );
        Assertions.assertEquals( 0, patientAdvocateAssociationService.count(),
                "There should be no Association records in the system" );

        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );

        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_2, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );

        final PatientAdvocateAssociation a1 = new PatientAdvocateAssociation( pa1, p1 );
        patientAdvocateAssociationService.save( a1 );

        p1.addAssociatedPatientAdvocates( a1 );
        patientService.save( p1 );

        pa1.addAssociatedPatients( a1 );
        patientAdvocateService.save( pa1 );

        final List<PatientAdvocateAssociation> savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 1, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals( USER_1, savedRecords.get( 0 ).getPatient().getUsername(),
                "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals( USER_2, savedRecords.get( 0 ).getPatientAdvocate().getUsername(),
                "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (Patient) patientService.findByName( USER_1 ) ).getAssociatedPatientAdvocates().get( 0 ).getId(),
                a1.getId(), "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (PatientAdvocate) patientAdvocateService.findByName( USER_2 ) ).getAssociatedPatients().get( 0 )
                        .getId(),
                a1.getId(), "Creating a Association record should results in its creation in the DB" );
    }

    /**
     * Test adding three patients to one patient advocate
     */
    @Transactional
    @Test
    public void testAdvancedAssociation1 () {
        Assertions.assertEquals( 0, patientAdvocateService.count(),
                "There should be no Patient Advocate records in the system" );
        Assertions.assertEquals( 0, patientService.count(), "There should be no Patient records in the system" );
        Assertions.assertEquals( 0, patientAdvocateAssociationService.count(),
                "There should be no Association records in the system" );

        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );
        final Patient p2 = new Patient( new UserForm( USER_2, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p2 );
        final Patient p3 = new Patient( new UserForm( USER_3, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p3 );
        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_4, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );

        final PatientAdvocateAssociation a1 = new PatientAdvocateAssociation( pa1, p1 );
        patientAdvocateAssociationService.save( a1 );

        p1.addAssociatedPatientAdvocates( a1 );
        patientService.save( p1 );

        pa1.addAssociatedPatients( a1 );
        patientAdvocateService.save( pa1 );

        List<PatientAdvocateAssociation> savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 1, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        final PatientAdvocateAssociation a2 = new PatientAdvocateAssociation( pa1, p2 );
        patientAdvocateAssociationService.save( a2 );

        p2.addAssociatedPatientAdvocates( a2 );
        patientService.save( p2 );

        pa1.addAssociatedPatients( a2 );
        patientAdvocateService.save( pa1 );

        savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 2, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        final PatientAdvocateAssociation a3 = new PatientAdvocateAssociation( pa1, p3 );
        patientAdvocateAssociationService.save( a3 );

        p3.addAssociatedPatientAdvocates( a3 );
        patientService.save( p3 );

        pa1.addAssociatedPatients( a3 );
        patientAdvocateService.save( pa1 );

        savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 3, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (Patient) patientService.findByName( USER_1 ) ).getAssociatedPatientAdvocates().get( 0 ).getId(),
                a1.getId(), "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (Patient) patientService.findByName( USER_2 ) ).getAssociatedPatientAdvocates().get( 0 ).getId(),
                a2.getId(), "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (Patient) patientService.findByName( USER_3 ) ).getAssociatedPatientAdvocates().get( 0 ).getId(),
                a3.getId(), "Creating a Association record should results in its creation in the DB" );
    }

    /**
     * Test adding three patient advocates to one patient
     */
    @Transactional
    @Test
    public void testAdvancedAssociation2 () {
        Assertions.assertEquals( 0, patientAdvocateService.count(),
                "There should be no Patient Advocate records in the system" );
        Assertions.assertEquals( 0, patientService.count(), "There should be no Patient records in the system" );
        Assertions.assertEquals( 0, patientAdvocateAssociationService.count(),
                "There should be no Association records in the system" );

        final Patient p1 = new Patient( new UserForm( USER_1, PW, Role.ROLE_PATIENT, 1 ) );
        patientService.save( p1 );
        final PatientAdvocate pa1 = new PatientAdvocate( new UserForm( USER_2, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa1 );
        final PatientAdvocate pa2 = new PatientAdvocate( new UserForm( USER_3, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa2 );
        final PatientAdvocate pa3 = new PatientAdvocate( new UserForm( USER_4, PW, Role.ROLE_PATIENTADVOCATE, 1 ) );
        patientAdvocateService.save( pa3 );

        final PatientAdvocateAssociation a1 = new PatientAdvocateAssociation( pa1, p1 );
        patientAdvocateAssociationService.save( a1 );

        p1.addAssociatedPatientAdvocates( a1 );
        patientService.save( p1 );

        pa1.addAssociatedPatients( a1 );
        patientAdvocateService.save( pa1 );

        List<PatientAdvocateAssociation> savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 1, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        final PatientAdvocateAssociation a2 = new PatientAdvocateAssociation( pa2, p1 );
        patientAdvocateAssociationService.save( a2 );

        p1.addAssociatedPatientAdvocates( a2 );
        patientService.save( p1 );

        pa2.addAssociatedPatients( a2 );
        patientAdvocateService.save( pa2 );

        savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 2, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        final PatientAdvocateAssociation a3 = new PatientAdvocateAssociation( pa3, p1 );
        patientAdvocateAssociationService.save( a3 );

        p1.addAssociatedPatientAdvocates( a3 );
        patientService.save( p1 );

        pa3.addAssociatedPatients( a3 );
        patientAdvocateService.save( pa3 );

        savedRecords = patientAdvocateAssociationService.findAll();

        Assertions.assertEquals( 3, savedRecords.size(),
                "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (PatientAdvocate) patientAdvocateService.findByName( USER_2 ) ).getAssociatedPatients().get( 0 )
                        .getId(),
                a1.getId(), "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (PatientAdvocate) patientAdvocateService.findByName( USER_3 ) ).getAssociatedPatients().get( 0 )
                        .getId(),
                a2.getId(), "Creating a Association record should results in its creation in the DB" );

        Assertions.assertEquals(
                ( (PatientAdvocate) patientAdvocateService.findByName( USER_4 ) ).getAssociatedPatients().get( 0 )
                        .getId(),
                a3.getId(), "Creating a Association record should results in its creation in the DB" );
    }
}
