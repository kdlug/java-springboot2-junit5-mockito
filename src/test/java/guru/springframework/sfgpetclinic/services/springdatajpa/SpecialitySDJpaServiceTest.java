package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void findByIdTest() {
        // Mock will return back this object
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));

        Speciality foundSpecialty = service.findById(1l);

        Assertions.assertThat(foundSpecialty).isNotNull();

        verify(specialtyRepository).findById(1l); // checks that findById was called once
    }

    @Test
    void deleteById() {
        service.deleteById(1l);
        service.deleteById(1l);

        // verifies how many times method was called
        verify(specialtyRepository, times(2)).deleteById(1l);
    }


    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1l);
        service.deleteById(1l);

        // verifies how many times method was called
        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1l);
        service.deleteById(1l);

        // verifies how many times method was called
        verify(specialtyRepository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1l);
        service.deleteById(1l);

        // verifies how many times method was called
        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
        verify(specialtyRepository, never()).deleteById(5l);
    }

    @Test
    void testDelete() {
        service.delete(new Speciality());
    }
}