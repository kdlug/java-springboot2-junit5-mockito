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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteByObject() {
        // given
        Speciality speciality = new Speciality();

        // when
        service.delete(speciality);

        // then
        then(specialtyRepository).should().delete(any(Speciality.class)); // test if delete method is called with any object that has a Specialty class
    }

    @Test
    void findByIdTest() {
        // given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1l)).willReturn(Optional.of(speciality));

        // when
        Speciality foundSpecialty = service.findById(1l);

        // then
        Assertions.assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        // given - none

        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        // verifies how many times method was called
        then(specialtyRepository).should(times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeast() {
        // given

        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        // verifies how many times method was called
        then(specialtyRepository).should(atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // when
        // verifies how many times method was called
        then(specialtyRepository).should(atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        // verifies how many times method was called
        then(specialtyRepository).should(atLeastOnce()).deleteById(1l);
        then(specialtyRepository).should(never()).deleteById(5l);
    }

    @Test
    void testDelete() {
        // when
        service.delete(new Speciality());

        // then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testFoThrow() {
        // the most typical use of throwing exceptions
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIDThrows() {
        // BDD approach
        given(specialtyRepository.findById(1l)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(1l));

        then(specialtyRepository).should().findById(1l);
    }

    @Test
    void deleteBDD() {
        // BDD when a method returns void
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }
}