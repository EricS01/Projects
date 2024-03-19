package edu.ncsu.csc.iTrust2.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.models.PatientAdvocateAssociation;
import edu.ncsu.csc.iTrust2.repositories.PatientAdvocateAssociationRepository;

@Component
@Transactional
public class PatientAdvocateAssociationService extends Service<PatientAdvocateAssociation, Long> {
    /**
     * Repository for CRUD operations
     */
    @Autowired
    private PatientAdvocateAssociationRepository repository;

    @Override
    protected JpaRepository<PatientAdvocateAssociation, Long> getRepository () {
        return repository;
    }

    /**
     * Finds a User with the given username
     *
     * @param patientUsername
     *            username of the patient to search
     * @param patientAdvocateUsername
     *            username of the patient advocate to search
     * @return Matching user, if any
     */
    public PatientAdvocateAssociation findByUsernames ( final String patientUsername,
            final String patientAdvocateUsername ) {
        final List<PatientAdvocateAssociation> associations = repository.findAll();

        for ( final PatientAdvocateAssociation association : associations ) {
            if ( association.getPatient().getUsername().equals( patientUsername )
                    && association.getPatientAdvocate().getUsername().equals( patientAdvocateUsername ) ) {
                return association;
            }
        }

        return null;
    }
}
