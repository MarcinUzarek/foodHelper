package com.example.foodhelper.service;

import com.example.foodhelper.TestDataSample;
import com.example.foodhelper.authentication_info.AuthenticationFacade;
import com.example.foodhelper.exception.custom.*;
import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.mapper.Mapper;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.model.dto.ResetPasswordDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.repository.UserRepository;
import com.example.foodhelper.user_details.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements TestDataSample {

    @InjectMocks
    private UserService userService;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private TokenService tokenService;
    @Mock
    private IntoleranceService intoleranceService;
    @Mock
    private MailFacade mailFacade;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Spy
    private Mapper mapper = new Mapper(new ModelMapper());


    @Test
    void should_be_able_to_get_logged_user() {
        //given
        var user = getSampleDataForUsers().get(0);
        user.setId(1L);

        given(userRepository.findById(1L))
                .willReturn(java.util.Optional.ofNullable(user));
        given(authenticationFacade.getPrincipal())
                .willReturn(new UserDetailsImpl(user));

        //when
        var loggedUser = userService.getLoggedUser();

        //then
        assertThat(user, is(loggedUser));
    }

    @Test
    void should_throw_UserNotFoundException_when_user_not_logged() {
        //given
        given(authenticationFacade.getPrincipal())
                .willReturn(null);

        //when
        //then
        assertThrows(UserNotLoggedException.class,
                () -> userService.getLoggedUser());
    }


    @Test
    void should_throw_EmailAlreadyExistsException_when_email_already_exists() {
        //given
        var userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("test@gmail.com");
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new User()));

        //when
        //then
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(userRegisterDTO));
    }

    @Test
    void should_create_new_user_when_given_correct_data() {
        //given
        var userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setName("registerMe");
        userRegisterDTO.setEmail("test@gmail.com");
        userRegisterDTO.setPassword("password");

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString()))
                .willReturn("encodedPassword");
        given(roleService.getUserRole())
                .willReturn(new Role("USER"));
        doNothing().when(mailFacade)
                .sendActivationEmail(any(User.class));

        //when
        userService.createUser(userRegisterDTO);

        //then
        verify(passwordEncoder, times(1))
                .encode(userRegisterDTO.getPassword());
        verify(roleService, times(1))
                .getUserRole();
        then(userRepository).should().save(any(User.class));
        then(tokenService).should().setTokenForUser(any(User.class));
        then(mailFacade).should()
                .sendActivationEmail(any(User.class));
    }

    @Test
    void should_activate_user_with_given_token() {
        //given
        var user = getSampleDataForUsers().get(0);
        var token = new Token();
        token.setValue("Q@wertyuiop");
        token.setUser(user);
        given(tokenService.findToken(anyString()))
                .willReturn(token);

        //when
        userService.activateUserWithToken(token.getValue());

        //then
        assertThat(user.isEnabled(), is(true));
    }

    @Test
    void should_throw_DifferentPasswordsException_when_passwords_not_same() {
        //given
        ResetPasswordDTO passwords = new ResetPasswordDTO();
        passwords.setPassword("password");
        passwords.setPassword("notMatchingPassword");

        //when
        //then
        assertThrows(DifferentPasswordsException.class,
                () -> userService.changePassword(passwords));
    }

    @Test
    void should_change_password_when_given_correct() {
        //given
        var user = getSampleDataForUsers().get(0);
        var token = new Token();
        token.setValue("Q@wertyuiop");
        token.setUser(user);

        var passwords = new ResetPasswordDTO();
        passwords.setPassword("matchingPassword");
        passwords.setConfirmPassword("matchingPassword");
        passwords.setToken(token.getValue());

        given(tokenService.findToken(token.getValue()))
                .willReturn(token);

        //when
        userService.changePassword(passwords);

        //then
        verify(passwordEncoder, times(1))
                .encode(passwords.getPassword());
        then(userRepository).should().save(user);
    }

    @Test
    void should_throw_ItemDuplicateException_if_user_already_has_this_intolerance() {
        //given
        IntoleranceDTO intoleranceDTO = new IntoleranceDTO();
        intoleranceDTO.setProduct("Milk");
        Intolerance intolerance = new Intolerance("Milk");

        var user = getSampleDataForUsers().get(0);
        user.setId(1L);
        user.setIntolerances(new HashSet<>(Set.of(intolerance)));

        given(authenticationFacade.getPrincipal())
                .willReturn(new UserDetailsImpl(user));
        given(userRepository.findById(1L))
                .willReturn(Optional.ofNullable(user));
        given(intoleranceService.getIntoleranceByName("Milk"))
                .willReturn(Optional.of(intolerance));

        //when
        //then
        assertThrows(ItemDuplicateException.class,
                () -> userService.addIntolerance(intoleranceDTO));
    }

    @Test
    void should_add_intolerance_if_user_dont_have_it() {
        //given
        IntoleranceDTO intoleranceDTO = new IntoleranceDTO();
        intoleranceDTO.setProduct("newProduct");

        var user = getSampleDataForUsers().get(0);
        user.setId(1L);

        given(authenticationFacade.getPrincipal())
                .willReturn(new UserDetailsImpl(user));
        given(userRepository.findById(1L))
                .willReturn(Optional.ofNullable(user));
        given(intoleranceService.getIntoleranceByName("newProduct"))
                .willReturn(Optional.of(new Intolerance("newProduct")));

        //when
        userService.addIntolerance(intoleranceDTO);

        //then
        assertThat(user.getIntolerances(), hasSize(1));
        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void should_remove_intolerance_from_user() {
        //given
        Intolerance intolerance = new Intolerance("Bread");

        var user = getSampleDataForUsers().get(0);
        user.setId(1L);
        user.setIntolerances(new HashSet<>(Set.of(intolerance)));

        given(authenticationFacade.getPrincipal())
                .willReturn(new UserDetailsImpl(user));
        given(userRepository.findById(1L))
                .willReturn(Optional.ofNullable(user));
        given(intoleranceService.findById(1L))
                .willReturn(intolerance);

        //when
        userService.removeIntoleranceById(1L);

        //then
        assertThat(user.getIntolerances(), is(empty()));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void should_throw_WrongCredentialsException_when_given_wrong_credentials() {
        //given
        given(authenticationFacade.getPrincipal())
                .willThrow(UserNotLoggedException.class);

        //when
        //then
        assertThrows(WrongCredentialsException.class,
                () -> userService.verifyLogging());
    }

    @Test
    void should_return_UserShowDto_when_logging_successful() {
        //given
        var user = getSampleDataForUsers().get(0);
        given(authenticationFacade.getPrincipal())
                .willReturn(new UserDetailsImpl(user));

        //when
        var userShowDTO = userService.verifyLogging();

        //then
        assertThat(userShowDTO, instanceOf(UserShowDTO.class));
        assertThat(userShowDTO.getName(), is("First"));
        assertThat(userShowDTO.getEmail(), is("first@email.com"));

    }
}