package utils;

import java.util.UUID;


public class IdGenerator{
    private static int next_account_number = 100;

    public static String generate_id(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generate_account_id() {
        next_account_number++;
        return "ACC-" + next_account_number;
    }
}