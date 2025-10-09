package com.shahrohit.hashcodex.properties;

import com.shahrohit.hashcodex.enums.Profile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for Application Profile.
 * <p>Binds properties with the prefix {@code spring.profiles} from application.properties.</p>
 */
@Validated
@ConfigurationProperties(prefix = "spring.profiles")
public record ProfileProperties(
    Profile active
) {}
