package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.services.PatientValidationService;
import com.example.basic.persons.insfrastructure.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PatientsRepositoryTest {
    @Mock
    PatientRepository testRepository;
    @Mock
    PatientValidationService testValidation;
    @InjectMocks
    PatientsRepository testPatientsRepository;
    Patient patient;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();
    }

    private void invokeInvalidDateRange() {
        testPatientsRepository.findByBirthDate("01/01/2000", "12/12/2020").blockFirst();
    }

    private void invokeInvalidDateFormat() {
        testPatientsRepository.findByBirthDate("01-01-2000", "12-12-2020").blockFirst();
    }

    @Test
    void testFindById() {
        Mockito.when(testRepository.findByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(patient));

        Patient result = testPatientsRepository.findById("ID").block();

        assert result != null;
        assertEquals(patient.getPersonName(), result.getPersonName());
        assertEquals(patient.getPersonSurname(), result.getPersonSurname());
        assertEquals(patient.getPersonID(), result.getPersonID());
    }

    @Test
    void testFindByFullName() {
        Mockito.when(testRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(patient));

        Patient result = testPatientsRepository.findByFullName("NAME", "SURNAME").blockFirst();

        assert result != null;
        assertEquals(patient.getPersonName(), result.getPersonName());
        assertEquals(patient.getPersonSurname(), result.getPersonSurname());
        assertEquals(patient.getPersonID(), result.getPersonID());
    }

    @Test
    void testFindByBirthDate() {
        Mockito.when(testValidation.validateDateRange(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
            .thenReturn(true);
        Mockito.when(testRepository.findByBirthDateBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
            .thenReturn(Flux.just(patient));

        Patient result = testPatientsRepository.findByBirthDate("01/01/2000", "12/12/2020").blockFirst();

        assert result != null;
        assertEquals(patient.getPersonName(), result.getPersonName());
        assertEquals(patient.getPersonSurname(), result.getPersonSurname());
        assertEquals(patient.getPersonID(), result.getPersonID());
    }

    @Test
    void testFindByBirthInvalidDateRange() {
        Mockito.when(testValidation.validateDateRange(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
            .thenReturn(false);

        DateFormatExc result = assertThrows(DateFormatExc.class, this::invokeInvalidDateRange);
        assertEquals("Invalid date range", result.getMessage());
    }

    @Test
    void testFindByBirthInvalidDateFormat() {
        DateFormatExc result = assertThrows(DateFormatExc.class, this::invokeInvalidDateFormat);
        assertEquals("Invalid date format: Invalid date format, must be dd/MM/YYYY", result.getMessage());
    }

    @Test
    void testCreatePatient() {
        Mockito.when(testRepository.existsByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(false));
        Mockito.when(testRepository.save(Mockito.any(Patient.class)))
            .thenReturn(Mono.just(patient));

        PatientRequest request = PatientRequest.builder()
            .personID("ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .birthDate("26/05/2002")
            .build();
        Patient result = testPatientsRepository.createPatient(request).block();

        assert result != null;
        assertEquals(patient.getPersonName(), result.getPersonName());
        assertEquals(patient.getPersonSurname(), result.getPersonSurname());
        assertEquals(patient.getPersonID(), result.getPersonID());
    }
}