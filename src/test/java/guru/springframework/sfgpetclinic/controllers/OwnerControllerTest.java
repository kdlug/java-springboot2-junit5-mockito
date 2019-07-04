package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Mock
    OwnerService ownerService;

    @BeforeEach
    void setUp() {
        controller = new OwnerController(ownerService);
    }

    @Test
    void processCreationForm() {
        // given
        final Long OWNER_ID = 5l;
        Owner owner =  new Owner(OWNER_ID, "John", "Doe");
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);

        //when
        String response = controller.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).should(times(1)).save(any(Owner.class));
        assertThat(response).isEqualTo("redirect:/owners/" + OWNER_ID);
    }

    @Test
    void processCreationFormHasErrors() {
        // given
        Owner owner =  new Owner(5l, "John", "Doe");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String response = controller.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).shouldHaveZeroInteractions();
        assertThat(response).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }


}