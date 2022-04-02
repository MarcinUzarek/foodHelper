package com.example.foodhelper;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

public interface TestDataSample {

    default List<User> getSampleDataForUsersTable() {
        User user1 = new User("First", "first@email.com", "first password");
        User user2 = new User("Second", "second@email.com", "second password");
        User user3 = new User("Third", "third@email.com", "third password");
        User user4 = new User("Fourth", "fourth@email.com", "fourth password");
        User user5 = new User("Fifth", "fith@email.com", "fifth password");
        return Arrays.asList(user1, user2, user3, user4, user5);
    }

    default List<Intolerance> getSampleDataForIntolerancesTable() {
        Intolerance milk = new Intolerance("milk");
        Intolerance eggs = new Intolerance("eggs");
        Intolerance dairy = new Intolerance("dairy");
        Intolerance nuts = new Intolerance("nuts");
        return Arrays.asList(milk, eggs, dairy, nuts);
    }

    default List<Role> getSampleDataForRolesTable() {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        Role moderator = new Role("MODERATOR");
        return Arrays.asList(admin, user, moderator);
    }

    default Token createSampleToken(UserRepository userRepository) {
        User user = new User("username", "testemail@gmail.com", "password");
        userRepository.save(user);
        Token token = new Token();
        token.setValue("Q@wertyuiop");
        token.setUser(user);
        return token;
    }


}
