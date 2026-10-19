package ru.romzheln.data_generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapperUtil {

    private MapperUtil(){
    }

    public static <T> void putOnceToMap(Map<String, List<String>> params, String key, T value){
        if(value == null){
            return;
        }
        params.put(key, List.of(value.toString()));
    }

    public static <T> void putSetToMap(Map<String, List<String>> params, String key, Set<T> value){
        if(value == null || value.isEmpty()){
            return;
        }
        List<String> valueToList = new ArrayList<>();
        for(T t : value){
            valueToList.add(t.toString());
        }
        params.put(key, valueToList);
    }
}
