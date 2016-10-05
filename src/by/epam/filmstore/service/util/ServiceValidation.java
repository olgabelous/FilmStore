package by.epam.filmstore.service.util;

import by.epam.filmstore.domain.Profession;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceValidationException;

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
        if(password == null || password.isEmpty() || password.length() < 6){
            return false;
        }
        /*Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();*/
        return  true;

    }

    public static boolean isNameValid(String name){
        Pattern pattern = Pattern.compile("^[a-zA-ZА-Яа-я]+$");
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();

    }

    public static boolean isNewPasswordValid(String password, String repeatPass){

        return password.equals(repeatPass);

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
        return d<0;
    }

    public static Profession getProfession(String prof) throws ServiceException {
        try{
            return Profession.valueOf(prof.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new ServiceValidationException("Such profession does not exist");
        }
    }

}
