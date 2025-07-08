package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.services.DoctorValidationService;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoctorsRepositoryTest {
    @Mock
    DoctorRepository testRepository;
    @Mock
    DoctorValidationService testService;
    @InjectMocks
    DoctorsRepository testDoctorsRepository;
    Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = Doctor.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();
    }

    private void invokeInvalidSpeciality() {
        testDoctorsRepository.findBySpeciality("INVALID").blockFirst();
    }

    private void invokeNotFoundToDelete() {
        testDoctorsRepository.deleteDoctor("ID").block();
    }

    private void invokeDuplicateDoctor() {
        testDoctorsRepository.createDoctor(doctor).block();
    }

    @Test
    void testFindAll() {
        Mockito.when(testRepository.findAll()).thenReturn(Flux.just(doctor));

        Doctor result = testDoctorsRepository.findAll().blockFirst();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    void testFindBySpeciality() {
        Mockito.when(testService.validateSpeciality(Mockito.anyString()))
            .thenReturn(true);
        Mockito.when(testRepository.findBySpecializationIgnoreCaseOrderByPersonNameAsc(Mockito.anyString()))
            .thenReturn(Flux.just(doctor));

        Doctor result = testDoctorsRepository.findBySpeciality("NEUROLOGY").blockFirst();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    void testFindByInvalidSpeciality() {
        Mockito.when(testService.validateSpeciality(Mockito.anyString()))
            .thenReturn(false);

        SpecialityExc result = assertThrows(SpecialityExc.class, this::invokeInvalidSpeciality);
        assertEquals("Invalid speciality", result.getMessage());
    }

    @Test
    void testFindById() {
        Mockito.when(testRepository.findByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));

        Doctor result = testDoctorsRepository.findById("ID").block();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    void testFindByFullName() {
        Mockito.when(testRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(doctor));

        Doctor result = testDoctorsRepository.findByFullName("NAME", "SURNAME").blockFirst();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    void testCreateDoctor() {
        Mockito.when(testRepository.existsByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(false));
        Mockito.when(testRepository.save(Mockito.any(Doctor.class)))
            .thenReturn(Mono.just(doctor));

        Doctor result = testDoctorsRepository.createDoctor(doctor).block();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
        assertTrue(doctor.isActive());
    }

    @Test
    void testCreateDoctorDuplicate() {
        Mockito.when(testRepository.existsByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(true));

        DuplicateExc result = assertThrows(DuplicateExc.class, this::invokeDuplicateDoctor);
        assertEquals("Entity already in the database", result.getMessage());
    }

    @Test
    void testDeleteDoctor() {
        Mockito.when(testRepository.findByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));
        Mockito.when(testRepository.save(Mockito.any(Doctor.class)))
            .thenReturn(Mono.just(doctor));

        Doctor result = testDoctorsRepository.deleteDoctor("ID").block();

        assert result != null;
        assertEquals(doctor.getPersonName(), result.getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getPersonID());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
        assertFalse(doctor.isActive());
    }

    @Test
    void testDeleteDoctorNotActive() {
        doctor.setActive(false);
        Mockito.when(testRepository.findByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));

        NotFoundExc result = assertThrows(NotFoundExc.class, this::invokeNotFoundToDelete);
        assertEquals("Cannot find an entity to modify", result.getMessage());
    }
}