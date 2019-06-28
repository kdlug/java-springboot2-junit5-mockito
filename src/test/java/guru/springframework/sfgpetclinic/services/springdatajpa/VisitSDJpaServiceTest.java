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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        // given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);

        // when
        Set<Visit> foundVisits = service.findAll();

        // then
        then(visitRepository).should(times(1)).findAll();
        Assertions.assertThat(foundVisits).isNotNull();
        Assertions.assertThat(foundVisits).hasSize(1);
    }

    @Test
    void findById() {
        // given
        Visit visit = new Visit();

        // check if find ById returns a visit Object
        given(visitRepository.findById(1l)).willReturn(Optional.of(visit));

        // when
        Visit foundVisit = service.findById(1L);

        // then
        then(visitRepository).should().findById(anyLong()); // checks that findById was called once when run service.findById()
        Assertions.assertThat(foundVisit).isNotNull();
    }

    @Test
    void save() {
        // given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        // when
        // check if save returns a visit Object
        Visit savedVisit = service.save(new Visit());

        // then
        then(visitRepository).should().save(any(Visit.class));
        Assertions.assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        // given
        Visit visit = new Visit();

        // when
        service.delete(visit);

        // then
        then(visitRepository).should().delete(any(Visit.class));
        then(visitRepository).should(times(1)).delete(visit);
    }

    @Test
    void deleteById() {
        // when
        service.deleteById(1l);

        // then
        then(visitRepository).should().deleteById(anyLong());
        then(visitRepository).should(times(1)).deleteById(1l);
    }
}