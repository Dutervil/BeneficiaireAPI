package com.wd.api.listener;

import com.wd.api.domain.User;
import com.wd.api.domain.UserPrincipal;
import com.wd.api.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class AuthenticationSuccessListener {

    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationFailureBadCredentialsEvent event) throws ExecutionException {
        Object principal=event.getAuthentication().getPrincipal();
         if (principal instanceof UserPrincipal){
             UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
             loginAttemptService.evicUserFromLogingAttemptCache(user.getUsername());
         }
    }
}
