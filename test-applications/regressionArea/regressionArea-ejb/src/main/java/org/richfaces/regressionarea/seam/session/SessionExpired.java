package org.richfaces.regressionarea.seam.session;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import org.jboss.seam.annotations.intercept.Interceptors;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Interceptors(SessionExpiredInterceptor.class)
public @interface SessionExpired {}
