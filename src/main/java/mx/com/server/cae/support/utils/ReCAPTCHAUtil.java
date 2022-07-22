package mx.com.server.cae.support.utils;

import java.util.Objects;
import mx.com.server.cae.dto.RecaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/* Add module security of recaptcha. We found the documentation from link
    https://developers.google.com/recaptcha/intro */
@Component("reCAPTCHAUtil")
public class ReCAPTCHAUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate template;
    @Value("${google.recaptcha.verification.endpoint}")
    String recaptchaEndpoint;
    @Value("${google.v2.recaptcha.checkbox.secret}")
    String recaptchaCheckboxSecret;

    public ReCAPTCHAUtil(final RestTemplateBuilder templateBuilder) {
        this.template = templateBuilder.build();
    }

    // method validate the captcha response coming from the client
    // and return either true or false after the validation.
    // reference url - https://developers.google.com/recaptcha/docs/verify
    public boolean validateReCAPTCHA(final String captchaResponse) {
        log.info("Going to validate the captcha response = {}", captchaResponse);
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // "secret" is a required param and it represents the shared key between your site 
        // and the recaptcha.
        params.add("secret", recaptchaCheckboxSecret);
        // "response" is a required param and it represents the user token provided
        // by the recaptcha client-side integration on your site.
        params.add("response", captchaResponse);

        RecaptchaResponse apiResponse = null;
        try {
            apiResponse = template.postForObject(recaptchaEndpoint, params, RecaptchaResponse.class);
        } catch (final RestClientException e) {
            log.error("Some exception occurred while binding to the recaptcha endpoint.", e);
        }

        if (Objects.nonNull(apiResponse) && apiResponse.isSuccess()) {
            log.info("Captcha API response = {}", apiResponse.toString());
            return true;
        } else {
            return false;
        }
    }
}
