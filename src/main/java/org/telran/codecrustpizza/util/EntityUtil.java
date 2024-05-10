package org.telran.codecrustpizza.util;

import org.telran.codecrustpizza.exception.EntityException;

import java.util.function.Consumer;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_IS_NULL;

public class EntityUtil {

    private EntityUtil() {
    }

    public static <One, Many> void removeBidirectionalRef(Consumer<Many> adder, Consumer<One> setter, Many element) {
        if (element == null) throw new EntityException(ENTITY_IS_NULL.getCustomMessage("")); // TODO add message
        adder.accept(element);
        setter.accept(null);
    }

    public static <One, Another> void changeManyToManyRef(Consumer<Another> adderAnother, Consumer<One> addOne, Another elementAnother, One elementOne) {
        if (elementOne == null) throw new EntityException("message"); // TODO add message
        if (elementAnother == null) throw new EntityException("message"); // TODO add message
        addOne.accept(elementOne);
        adderAnother.accept(elementAnother);
    }
}