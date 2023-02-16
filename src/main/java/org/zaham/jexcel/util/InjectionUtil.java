package org.zaham.jexcel.util;



import java.util.*;

import org.burningwave.core.classes.FieldCriteria;
import org.zaham.jexcel.annotation.inject.Autowired;
import org.zaham.jexcel.annotation.inject.Qualifier;
import org.zaham.jexcel.injectors.Injector;

import java.lang.reflect.Field;

import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public class InjectionUtil {
    /**
     * Perform injection recursively, for each service inside the Client class
     */
    public static void autowire(Injector injector, Class<?> classz, Object classInstance)
            throws InstantiationException, IllegalAccessException {
        Collection<Field> fields = Fields.findAllAndMakeThemAccessible(
                FieldCriteria.forEntireClassHierarchy().allThoseThatMatch(field ->
                        field.isAnnotationPresent(Autowired.class)
                ),
                classz
        );
        for (Field field : fields) {
            String qualifier = field.isAnnotationPresent(Qualifier.class)
                    ? field.getAnnotation(Qualifier.class).value()
                    : null;
            Object fieldInstance = injector.getBeanInstance(field.getType(), field.getName(), qualifier);
            Fields.setDirect(classInstance, field, fieldInstance);
            autowire(injector, fieldInstance.getClass(), fieldInstance);
        }
    }

}
