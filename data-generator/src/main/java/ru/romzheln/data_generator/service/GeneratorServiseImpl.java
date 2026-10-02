package ru.romzheln.data_generator.service;

import org.springframework.stereotype.Service;
import ru.romzheln.data_generator.dto.request.GenerateRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GeneratorServiseImpl implements GeneratorService{

    private static final Integer POOL_SIZE = 10;

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(POOL_SIZE);

    private static final int DEVELOPER_PERCENT = 20;

    @Override
    public boolean generate(GenerateRequest request) {
        return false;
    }

    private int getDeveloperCount(int total, int apartmentPercent){
       int result = ((total * 100 / apartmentPercent) * 100) / DEVELOPER_PERCENT;
       if(result < 1){
           return 1;
    }
       return result;
       }

    private void generateDeveloper(int developers){
        for(int i = 0; i < developers; i++) {

        }
    }
}
