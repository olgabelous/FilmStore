package by.epam.filmstore.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class ServiceHelper {

    private ServiceHelper() {}

    public static boolean isLoginValid(String login){

        if(login == null || login.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(login);

       return matcher.matches();

    }

    public static boolean isPasswordValid(String password){

        return !(password == null || password.isEmpty() || password.length() < 5);

    }
}
