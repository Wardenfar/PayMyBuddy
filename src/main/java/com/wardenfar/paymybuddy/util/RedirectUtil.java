package com.wardenfar.paymybuddy.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

public class RedirectUtil {

    /**
     * Util method to redirect with message and error
     */
    public static RedirectView redirectTo(String path, String msg, String error) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(path);

        if (error != null) {
            builder.queryParam("error", error);
        }
        if (msg != null) {
            builder.queryParam("msg", msg);
        }

        String redirectUri = builder.toUriString();
        return new RedirectView(redirectUri);
    }
}
