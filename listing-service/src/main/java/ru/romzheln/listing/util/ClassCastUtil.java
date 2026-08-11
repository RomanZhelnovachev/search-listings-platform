package ru.romzheln.listing.util;

import lombok.experimental.UtilityClass;
import ru.romzheln.listing.exception.serverError.InvalidCastException;

@UtilityClass
public class ClassCastUtil {

    public static  <T> T requireType(Object source, Class<T> targetType) {
        if (!targetType.isInstance(source)) {
            throw new InvalidCastException(targetType.getSimpleName(), source.getClass().getSimpleName());
        }
        return targetType.cast(source);
    }
}
