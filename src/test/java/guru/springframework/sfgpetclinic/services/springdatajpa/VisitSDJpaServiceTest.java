package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    private VisitRepository visitRepository;

//    @Mock
//    private Pet pet;

//    @Mock
//    private Owner owner;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAllTest() {
        Visit visit = new Visit();

        Set<Visit> visits = new HashSet<>();
        visits.add(visit);

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> foundVisits = service.findAll();
        Assertions.assertThat(foundVisits).isNotNull();
        Assertions.assertThat(foundVisits).hasSize(1);

        verify(visitRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        Visit visit = new Visit();

        // check if find ById returns a visit Object
        when(visitRepository.findById(1l)).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(1L);
        Assertions.assertThat(foundVisit).isNotNull();
        verify(visitRepository).findById(anyLong()); // checks that findById was called once when run service.findById()

    }

    @Test
    void save() {
        Visit visit = new Visit();

       // check if save returns a visit Object
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = service.save(new Visit());

        verify(visitRepository).save(any(Visit.class));

        Assertions.assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
        verify(visitRepository, times(1)).delete(visit);

    }

    @Test
    void deleteByIdTest() {
        service.deleteById(1l);

        verify(visitRepository).deleteById(anyLong());
        verify(visitRepository, times(1)).deleteById(1l);
    }
}