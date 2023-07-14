package com.wd.api.resource;

import com.wd.api.domain.HttpResponse;
import com.wd.api.domain.User;
import com.wd.api.domain.UserPrincipal;
import com.wd.api.exception.ExceptionHandling;
import com.wd.api.exception.domain.EmailExistException;
import com.wd.api.exception.domain.EmailNotFoundException;
import com.wd.api.exception.domain.UserNotFoundException;
import com.wd.api.exception.domain.UsernameExistException;
import com.wd.api.service.UserService;
import com.wd.api.utility.JWTTokenProvider;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wd.api.constant.FileConstant.*;
import static com.wd.api.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/", "/user"})
@CrossOrigin("*")
public class UserResource extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password sent to ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserResource(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        String token =jwtTokenProvider.generateJwtToken(userPrincipal);
//        LoginResponse response=new LoginResponse(token,loginUser);
        Map<String,Object> response=new HashMap<>();
        response.put("token",token);
        response.put("user",loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(response, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User>addNewUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNonLocked") String isNonLocked,
            @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
    User newUser=userService.addNewUser(
            firstName,
            lastName,
            username,
            email,
            role,
            Boolean.parseBoolean(isNonLocked),
            Boolean.parseBoolean(isActive),
            profileImage
            );
    return new ResponseEntity<>(newUser,OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User>updateUser(
            @RequestParam("currentUsername") String currentUsername,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNonLocked") String isNonLocked,
            @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User updateUser=userService.updateNewUser(
                currentUsername,
                firstName,
                lastName,
                username,
                email,
                role,
                Boolean.parseBoolean(isNonLocked),
                Boolean.parseBoolean(isActive),
                profileImage
        );
        return new ResponseEntity<>(updateUser,OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.findById(id),OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String emai) throws EmailNotFoundException, MessagingException {
       userService.resetPassword(emai);
        return response(OK, EMAIL_SENT  +emai);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);
    }
    @PutMapping("/updateProfileImage")
    public ResponseEntity<User>updateProfileImage(
            @RequestParam("username") String curremtUsername,
            @RequestParam("profileImage") MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User updateUser=userService.updateProfileImage(curremtUsername, profileImage);

        return new ResponseEntity<>(updateUser,OK);
    }
    @GetMapping(path ="/image/{username}/{fileName}",produces = {IMAGE_JPEG_VALUE})
    public byte[] getProfileImage( @PathVariable("username") String username,
                                   @PathVariable("fileName") String fileName) throws IOException {
      return Files.readAllBytes(Paths.get(USER_FOLDER+username+FORWARD_SLASH+fileName));
    }

    @GetMapping(path ="/image/profile/{username}",produces = {IMAGE_JPEG_VALUE})
    public byte[] getTempProfileImage( @PathVariable("username") String username) throws IOException {
        URL url=new URL(TEMP_PROFILE_IAMGE_BASE_URL+username);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try(InputStream inputStream=url.openStream()) {
            int bytesRead;
            byte[] chunk=new byte[1024];
            while ((bytesRead=inputStream.read(chunk))>0){
                byteArrayOutputStream.write(chunk,0,bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),message.toUpperCase()),httpStatus);
    }


    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),OK);
    }
    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


    class LoginResponse{
        String token;
        User user;
        LoginResponse(String token,User user){
            this.user=user;
            this.token=token;
        }
    }
}
