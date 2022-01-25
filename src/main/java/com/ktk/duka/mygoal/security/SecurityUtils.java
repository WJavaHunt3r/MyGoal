package com.ktk.duka.mygoal.security;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

@UtilityClass
public class SecurityUtils {

    static boolean isInternalRequest(HttpServletRequest request) {
        return Stream.of(HandlerHelper.RequestType.values()).anyMatch(requestType -> requestType.getIdentifier()
                .equals(request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER)));
    }

    static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
        }
        return false;
    }

    static String encryptSecret(String secret) {
        return encrypt(secret);
    }

    public static String encrypt(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            digest.update(secret.getBytes());
            return new String(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("An exception occurred during encryption.");
        }
    }
}
