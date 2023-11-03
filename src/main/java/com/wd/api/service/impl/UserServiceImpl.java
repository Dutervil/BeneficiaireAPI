package com.wd.api.service.impl;

import com.wd.api.EmailUtils;
import com.wd.api.domain.User;
import com.wd.api.domain.UserPrincipal;
import com.wd.api.enumeration.Role;
import com.wd.api.exception.domain.EmailExistException;
import com.wd.api.exception.domain.EmailNotFoundException;
import com.wd.api.exception.domain.UserNotFoundException;
import com.wd.api.exception.domain.UsernameExistException;
import com.wd.api.repository.UserRepository;
import com.wd.api.service.EmailService;
import com.wd.api.service.LoginAttemptService;
import com.wd.api.service.UserService;
import com.wd.api.constant.UserImplConstant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.wd.api.constant.FileConstant.*;
import static com.wd.api.constant.UserImplConstant.NO_USER_FOUND_BY_EMAIL;
import static com.wd.api.enumeration.Role.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
   private LoginAttemptService loginAttemptService;

    private final EmailUtils emailUtils;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService, EmailUtils emailUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;

        this.emailUtils = emailUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(UserImplConstant.NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(UserImplConstant.NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(UserImplConstant.FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(User user)  {
        if (user.isNotLocked()){
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())){
                user.setNotLocked(false);
            }else{
                user.setNotLocked(true);
            }
        }else{
            loginAttemptService.evicUserFromLogingAttemptCache(user.getUsername());
        }
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword("admin");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_SUPER_ADMIN.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailUtils.confirmationEmail(email,password);
        return user;
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username,String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, MessagingException {
       validateNewUsernameAndEmail(EMPTY,username,email);
//        String password = "admin";
        String password = generatePassword();
        User user = new User();
        user.setUserId(generateUserId());
        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(encodePassword("admin"));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);
        user.setJoinDate(new Date());
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        saveProfileImage(user,profileImage);
//        TODO: Send generated password to use
        LOGGER.info("New user password: " + password);
        emailUtils.confirmationEmail(email,password);
        return user;
    }



    @Override
    public User updateNewUser(Long id,String currentUsername, String newFirstName, String newLastName,String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        validateNewUsernameAndEmail(currentUsername,newUsername,newEmail);
        String password = generatePassword();
        User currentUser = new User();
        currentUser.setUserId(generateUserId());
        currentUser.setUserId(generateUserId());
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setJoinDate(new Date());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setId(id);
        userRepository.save(currentUser);
        saveProfileImage(currentUser,profileImage);
        return currentUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
     User user=userRepository.findUserByEmail(email);
     if (user ==null){
         throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL+email);
     }
     String password=generatePassword();
     user.setPassword(encodePassword(password));
     userRepository.save(user);
//     emailService.sendNewPasswordEmail(user.getFirstName(),password,user.getEmail());
    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        User user=validateNewUsernameAndEmail(username,null,null);
        saveProfileImage(user,profileImage);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH +  username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException(UserImplConstant.NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(UserImplConstant.USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(UserImplConstant.EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(UserImplConstant.USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailExistException(UserImplConstant.EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }


    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {

        if (profileImage !=null){
            Path userFolder= Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED);
            }
            Files.deleteIfExists(Paths.get(userFolder+user.getUsername()+DOT+JPG_EXTENTION));
            Files.copy(profileImage.getInputStream(),userFolder.resolve(user.getUsername()+DOT+JPG_EXTENTION),REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM+profileImage.getOriginalFilename());
            LOGGER.info(DIRECTORY_CREATED + userFolder);
        }
    }

    private String setProfileUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH +  username+FORWARD_SLASH
        +username + DOT + JPG_EXTENTION).toUriString();

    }


    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

}
