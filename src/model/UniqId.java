package model;

import java.util.Random;

public class UniqId {
    
    public static String generate(){
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        return String.format("%d%06d", timestamp, randomInt);
    }
    
}
