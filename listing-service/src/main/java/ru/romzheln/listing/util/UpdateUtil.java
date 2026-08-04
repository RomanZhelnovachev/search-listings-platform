package ru.romzheln.listing.util;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public class UpdateUtil {

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }
}
