package com.example.foodhelper.service;

import com.example.foodhelper.authentication_info.AuthenticationFacade;
import com.example.foodhelper.exception.*;
import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.mapper.Mapper;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.model.dto.ResetPasswordDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MailFacade mailFacade;
    private final AuthenticationFacade authenticationFacade;
    private final IntoleranceService intoleranceService;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, TokenService tokenService, RoleService roleService, PasswordEncoder passwordEncoder, MailFacade mailFacade, AuthenticationFacade authenticationFacade, IntoleranceService intoleranceService, Mapper mapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailFacade = mailFacade;
        this.authenticationFacade = authenticationFacade;
        this.intoleranceService = intoleranceService;
        this.mapper = mapper;
    }

    public User getLoggedUser() {
        var principal = authenticationFacade.getPrincipal();
        if (principal == null) {
            throw new UserNotLoggedException("Test");
        }
        var id = principal.getUser().getId();
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user with such Id"));
    }

    public UserShowDTO getLoggedUserAsDto() {
        var user = getLoggedUser();
        return userToShowDto(user);
    }

    @Transactional
    public UserRegisterDTO createUser(UserRegisterDTO userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("User with this email already exists");
        }
        var user = mapper.mapRegisterDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.addUserRole());
        userRepository.save(user);
        tokenService.setTokenForUser(user);
        mailFacade.sendActivationEmail(user);
        return userDto;
    }

    public void activateUserWithToken(String tokenValue) {
        var token = tokenService.findToken(tokenValue);
        User user = token.getUser();
        ActivateUser(user);
    }

    public void changePassword(ResetPasswordDTO passwordDto) {
        if (!validatePasswordMatching(passwordDto)) {
            throw new DifferentPasswordsException("Passwords are not the same");
        }
        var token = tokenService.findToken(passwordDto.getToken());
        var user = token.getUser();
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        userRepository.save(user);
    }


    public Intolerance addIntolerance(IntoleranceDTO intoleranceDto) {
        if (intoleranceDto.getProduct().equals("null")) {
            throw new IllegalArgumentException("add own exception here");
        }
        Intolerance intolerance = getIntolerance(intoleranceDto.getProduct());
        var user = getLoggedUser();

        if (!user.getIntolerances().contains(intolerance)) {
            user.addIntolerance(intolerance);
            userRepository.save(user);
        } else {
            throw new ItemDuplicateException("You already have this item on your list");
        }
        return intolerance;
    }

    public Intolerance removeIntoleranceById(Long id) {
        var user = getLoggedUser();
        var intolerance = intoleranceService.findById(id);
        var intolerances = user.getIntolerances();
        intolerances.remove(intolerance);
        userRepository.save(user);
        return intolerance;
    }

    public UserShowDTO verifyLogging() {
        User user;
        try {
            user = authenticationFacade.getPrincipal().getUser();
        } catch (UserNotLoggedException e) {
            throw new WrongCredentialsException("Wrong Credentials");
        }
        return mapper.mapUserToUserShowDto(user);
    }

    private boolean validatePasswordMatching(ResetPasswordDTO passwordDTO) {
        return passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword());
    }

    private Intolerance getIntolerance(String product) {
        Intolerance intolerance;
        if (intoleranceService.getIntoleranceByName(product).equals(Optional.empty())) {
            intolerance = intoleranceService.saveIntolerance(product);
        } else {
            intolerance = intoleranceService.getIntoleranceByName(product).get();
        }
        return intolerance;
    }

    private void ActivateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    private UserShowDTO userToShowDto(User user) {

        var userShowDTO = mapper.mapUserToUserShowDto(user);
        var intolerances = intoleranceService
                .intoleranceHashSetToTreeSet(userShowDTO.getIntolerances());

        userShowDTO.setIntolerances(intolerances);
        return userShowDTO;
    }


}