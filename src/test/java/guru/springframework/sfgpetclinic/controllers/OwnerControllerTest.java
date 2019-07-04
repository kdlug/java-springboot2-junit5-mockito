package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
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

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        controller = new OwnerController(ownerService);

        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();

                    String name = invocation.getArgument(0);

                    if (name.equals("%Buck%")) {
                        owners.add(new Owner(1l, "John", "Buck"));
                        return owners;
                    } else if (name.equals("%DontFindMe%")) {
                        return owners;
                    } else if (name.equals("%FindMe%")) {
                        owners.add(new Owner(1l, "John", "Buck"));
                        owners.add(new Owner(2l, "John", "Lennon"));
                        return owners;
                    }

                    throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildcardStringAnnotationFoundAnswers() {
        // given
        Owner owner = new Owner(1l, "John", "Buck");
        List<Owner> ownerList = new ArrayList<>();

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildcardStringAnnotationFoundMultipleAnswers() {
        // given
        Owner owner = new Owner(1l, "John", "FindMe");
        List<Owner> ownerList = new ArrayList<>();

        // when
        String viewName = controller.processFindForm(owner, bindingResult, mock(Model.class));

        // then
        assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);
    }


    @Test
    void processFindFormWildcardStringNotFoundAnnotationAnswers() {
        // given
        Owner owner = new Owner(1l, "John", "DontFindMe");
        List<Owner> ownerList = new ArrayList<>();

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat("%DontFindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildcardString() {
        // given
        Owner owner = new Owner(1l, "John", "Buck");
        List<Owner> ownerList = new ArrayList<>();

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Buck%");
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // given
        Owner owner = new Owner(1l, "John", "Buck");
        List<Owner> ownerList = new ArrayList<>();

        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
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