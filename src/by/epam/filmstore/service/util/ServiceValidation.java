package by.epam.filmstore.service.util;

import by.epam.filmstore.domain.Profession;
import by.epam.filmstore.service.exception.ServiceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class ServiceValidation {

    private ServiceValidation() {}

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

    public static boolean isNullOrEmpty(String s){

         return (s==null || s.isEmpty());
    }

    public static boolean isNullOrEmpty(String... ss){

        for (String s: ss) {
            if(s==null || s.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static boolean isNotPositive(int... nn) {
        for (int n: nn) {
            if(n <= 0){
                return true;
            }
        }
        return false;
    }
    public static boolean isNotPositive(double d) {
        return d<=0;
    }

    public static Profession getProfession(String prof) throws ServiceException {
        try{
            return Profession.valueOf(prof.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new ServiceException("Such profession does not exist");
        }
    }

}
