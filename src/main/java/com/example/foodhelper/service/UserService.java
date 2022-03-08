package com.example.foodhelper.service;

import com.example.foodhelper.authenticated_user.AuthenticationFacade;
import com.example.foodhelper.exception.DifferentPasswordsException;
import com.example.foodhelper.exception.EmailAlreadyExists;
import com.example.foodhelper.exception.ItemDuplicateException;
import com.example.foodhelper.mail.MailFacade;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ResetPasswordDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, TokenService tokenService, RoleService roleService, PasswordEncoder passwordEncoder, MailFacade mailFacade, AuthenticationFacade authenticationFacade, IntoleranceService intoleranceService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailFacade = mailFacade;
        this.authenticationFacade = authenticationFacade;
        this.intoleranceService = intoleranceService;
        this.modelMapper = modelMapper;
    }

    public User getLoggedUser() {
        var id = authenticationFacade.getPrincipal().getUser().getId();
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with such Id"));
    }

    public UserShowDTO getLoggedUserAsDto() {
        var user = getLoggedUser();
        return userToShowDto(user);
    }

    public void createUser(UserRegisterDTO userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("User with this email already exists");
        }
        var user = registerDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.setUserRole());
        userRepository.save(user);
        mailFacade.sendActivationEmail(user);
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


    public void addIntolerance(String product) {
        if (product.equals("null")) {
            return;
        }
        Intolerance intolerance = getIntolerance(product);
        var user = getLoggedUser();

        if (!user.getIntolerances().contains(intolerance)) {
            user.addIntolerance(intolerance);
            userRepository.save(user);
        } else {
            throw new ItemDuplicateException("You already have this item on your list");
        }
    }

    public void removeIntolerance(Long id) {
        var user = getLoggedUser();
        var intolerance = intoleranceService.findById(id);
        var intolerances = user.getIntolerances();
        intolerances.remove(intolerance);
        userRepository.save(user);
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

    private User registerDtoToUser(UserRegisterDTO userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserShowDTO userToShowDto(User user) {
        return modelMapper.map(user, UserShowDTO.class);
    }
}