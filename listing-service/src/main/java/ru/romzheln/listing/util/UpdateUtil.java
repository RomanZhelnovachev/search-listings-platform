package ru.romzheln.listing.util;

import java.util.function.Consumer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpdateUtil {

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }
}
