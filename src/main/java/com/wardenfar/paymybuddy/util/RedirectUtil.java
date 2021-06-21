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
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(path);

        if (error != null) {
            builder.queryParam("error", error);
        }
        if (msg != null) {
            builder.queryParam("msg", msg);
        }

        String redirectUri = builder.toUriString();

        if(error != null){
            log.error("Redirect {} : {}", path, error);
        }else{
            log.info("Redirect {} : {}", path, msg);
        }
        log.debug("Redirect to {}", redirectUri);

        return new RedirectView(redirectUri);
    }
}
