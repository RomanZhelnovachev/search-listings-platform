package ru.romzheln.search_service.util;

import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.PropertyEventCastException;
import ru.romzheln.search_service.exception.ReadModelCastException;
import ru.romzheln.search_service.model.read_model.ReadModel;

public class CastUtil {

    private CastUtil(){}

    public static <M extends ReadModel> M castReadModel(ReadModel model, Class<M> target) {
        if (!target.isInstance(model)) {
            throw new ReadModelCastException(model);
        }
        return target.cast(model);
    }

    public static <E extends PropertyEvent> E castPropertyEvent(PropertyEvent event, Class<E> target){
        if(!target.isInstance(event)) {
            throw new PropertyEventCastException(event);
        }
        return target.cast(event);
    }
}
