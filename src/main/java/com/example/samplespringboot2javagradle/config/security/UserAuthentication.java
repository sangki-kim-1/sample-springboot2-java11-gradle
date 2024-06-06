package com.example.samplespringboot2javagradle.config.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.core.annotation.CurrentSecurityContext;

/**
 *
 *
 * <h3>User Authentication</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression = "authentication.principal")
public @interface UserAuthentication {}
