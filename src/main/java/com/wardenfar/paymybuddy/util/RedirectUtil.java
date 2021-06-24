package com.wardenfar.paymybuddy.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
public class RedirectUtil {

    /**
     * Util method to redirect with message and error
     */
    public static RedirectView redirectTo(String path, String msg, String error) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replacePath(path);
        return processURI(builder, msg, error);
    }

    /**
     * Add query parameters
     */
    public static RedirectView processURI(UriComponentsBuilder builder, String msg, String error) {
        // Remove all query params
        builder.query("");

        // Add only not null params
        if (error != null) {
            builder.replaceQueryParam("error", error);
        }
        if (msg != null) {
            builder.replaceQueryParam("msg", msg);
        }

        // Build the uri
        String redirectUri = builder.toUriString();

        // Logs
        if (error != null) {
            log.error("Redirect {} : {}", error, redirectUri);
        } else {
            log.info("Redirect {} : {}", msg, redirectUri);
        }
        log.debug("Redirect to {}", redirectUri);

        // Return the view
        return new RedirectView(redirectUri);
    }
}
