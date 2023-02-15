package org.zaham.jexcel.annotation.inject;

import java.lang.annotation.*;

/**
 * Client class should use this annotation (inspired from Spring)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

}
