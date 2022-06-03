package com.example.foodhelper.model.user_details;

import com.example.foodhelper.TestDataSample;
import com.example.foodhelper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest implements TestDataSample {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;



    @Test
    void should_load_user_details_by_username() {
        //given
        String email = "first@email.com";
        var user = getSampleDataForUsers().get(0);
        given(userRepository.findByEmail(email))
                .willReturn(java.util.Optional.ofNullable(user));

        //when
        var userDetails = userDetailsService.loadUserByUsername(email);

        //then
        assertThat(userDetails, instanceOf(UserDetails.class));
        assertThat(user.getName(), is("First"));

    }
}