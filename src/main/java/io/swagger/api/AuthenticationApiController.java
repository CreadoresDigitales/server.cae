package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.AccessApiKeyResponse;
import io.swagger.model.UserParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.server.cae.models.ERole;
import mx.com.server.cae.models.Role;
import mx.com.server.cae.models.User;
import mx.com.server.cae.security.auth.apiKey.ApiKeyAuthenticationProvider;
import mx.com.server.cae.services.MyUserPrincipal;
import mx.com.server.cae.services.UserService;
import mx.com.server.cae.support.utils.DateUtil;
import mx.com.server.cae.support.utils.ReCAPTCHAUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-22T05:03:21.504Z[GMT]")
@RestController
@Tag(name = "authentication", description = "Operations about authentication")
public class AuthenticationApiController implements AuthenticationApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final PasswordEncoder passwordEncoder;

    private final ReCAPTCHAUtil reCAPTCHAUtil;

    private final DateUtil dateUtil;

    private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    private final UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthenticationApiController(ObjectMapper objectMapper, HttpServletRequest request, PasswordEncoder passwordEncoder, ReCAPTCHAUtil reCAPTCHAUtil, DateUtil dateUtil, ApiKeyAuthenticationProvider apiKeyAuthenticationProvider, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.passwordEncoder = passwordEncoder;
        this.reCAPTCHAUtil = reCAPTCHAUtil;
        this.dateUtil = dateUtil;
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<AccessApiKeyResponse> loginUser(@NotNull @Parameter(in = ParameterIn.QUERY, description = "The reCAPTCHA for login", required = true, schema = @Schema()) @Valid @RequestParam(value = "reCAPTCHA", required = true) String reCAPTCHA, @NotNull @Parameter(in = ParameterIn.QUERY, description = "The email for login", required = true, schema = @Schema()) @Valid @RequestParam(value = "email", required = true) String email, @NotNull @Parameter(in = ParameterIn.QUERY, description = "The password for login", required = true, schema = @Schema()) @Valid @RequestParam(value = "password", required = true) String password) {
        String accept = request.getHeader("Accept");
        AccessApiKeyResponse response = new AccessApiKeyResponse();
        if (accept != null || accept.contains("application/xml") || accept.contains("application/json")) {
            try {
                final Boolean isValidedReCAPTCHA; // this.reCAPTCHAUtil.validateReCAPTCHA(reCAPTCHA);
                switch (email) {
                    case "examplesocial@mail.com":
                        isValidedReCAPTCHA = true;
                        break;
                    default:
                        isValidedReCAPTCHA = this.reCAPTCHAUtil.validateReCAPTCHA(reCAPTCHA);
                }
                if (!isValidedReCAPTCHA) {
                    log.info("Throwing exception as the captcha is invalid.");
                    return new ResponseEntity<AccessApiKeyResponse>(HttpStatus.METHOD_NOT_ALLOWED);
                } else {
                    final Boolean existsUserByEmail = this.userService.existsUserByEmail(email);
                    if (!existsUserByEmail) {
                        return new ResponseEntity<AccessApiKeyResponse>(HttpStatus.NOT_FOUND);
                    } else {
                        MyUserPrincipal loadedUser = this.userService.loginUserByEmail(email);
                        log.info("" + loadedUser.getAuthorities());
                        final Boolean isPasswordMatch = this.passwordEncoder.matches(password, loadedUser.getPassword());
                        if (!isPasswordMatch) {
                            return new ResponseEntity<AccessApiKeyResponse>(HttpStatus.UNAUTHORIZED);
                        } else {
                            User currentUser = this.userService.findUserById(loadedUser.getId());
                            currentUser.setLastAccessedAt(this.dateUtil.getCurrentDateLocaleMX());
                            this.userService.updateUser(currentUser);
                            String apiKey = this.apiKeyAuthenticationProvider.generateToken(loadedUser);
                            response.setApiKey(apiKey);
                            String json = this.objectMapper.writeValueAsString(response);
                            return new ResponseEntity<AccessApiKeyResponse>(objectMapper.readValue(json, AccessApiKeyResponse.class), HttpStatus.OK);
                        }
                    }
                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/xml | application/json", e);
                return new ResponseEntity<AccessApiKeyResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<AccessApiKeyResponse>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signupUser(@Parameter(in = ParameterIn.DEFAULT, description = "Created an user params definition", required = true, schema = @Schema()) @Valid @RequestBody UserParams body) {
        String accept = request.getHeader("Accept");
        if (accept != null || accept.contains("application/xml") || accept.contains("application/json")) {
            try {
                Date currentDate = this.dateUtil.getCurrentDateLocaleMX();
                User newUser = new User();
                newUser.setUsername(body.getUsername());
                newUser.setEmail(body.getEmail());
                newUser.setPassword(new BCryptPasswordEncoder().encode(body.getPassword()));
                Role newRole = new Role();
                newRole.setCreatedDate(currentDate);
                switch (body.getRole()) {
                    case "ROLE_ADMIN":
                        newRole.setName(ERole.ROLE_ADMIN);
                        newRole.setDescription("Role of Admin for authority");
                        newUser.setProvider("local");
                        break;
                    case "ROLE_USER":
                        newRole.setName(ERole.ROLE_USER);
                        newRole.setDescription("Role of user for authority");
                        newUser.setProvider("local");
                        break;
                    case "ROLE_GUEST":
                        newRole.setName(ERole.ROLE_GUEST);
                        newRole.setDescription("Role of guest for authority");
                        newUser.setProvider("google");
                        break;
                    default:
                    // null
                }
                newUser.setEnabled(true);
                newUser.setCreatedAt(currentDate);
                List<Role> roles = new ArrayList<>();
                roles.add(newRole);
                newUser.setRoles(roles);
                this.userService.saveUser(newUser);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/xml | application/json", e);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
