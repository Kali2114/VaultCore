package utils;

import java.util.UUID;


public class IdGenerator{
    public static String generate_id(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}