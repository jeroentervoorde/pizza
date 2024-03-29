/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agoncal.application.petstore.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author jtervoorde
 */
@InterceptorBinding
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface Cacheable {
}
