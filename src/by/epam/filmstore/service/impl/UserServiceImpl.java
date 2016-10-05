package by.epam.filmstore.service.impl;

import by.epam.filmstore.dao.DAOFactory;
import by.epam.filmstore.dao.IUserDAO;
import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.domain.dto.PagingListDTO;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.exception.ServiceAuthException;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceIncorrectParamLengthException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import by.epam.filmstore.service.util.ServiceValidation;
import by.epam.filmstore.util.DAOHelper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User authorize(String login, String password) throws ServiceException {
        if(!ServiceValidation.isLoginValid(login)){
            throw new ServiceAuthException("Wrong login!");
        }
        if(!ServiceValidation.isPasswordValid(password)){
            throw new ServiceAuthException("Wrong password!");
        }

        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();

        User user;
            try {
                user = DAOHelper.execute(() -> dao.authorize(login, password));
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
            if(user == null){
                throw new ServiceAuthException("Wrong login or password!");
            }
        return user;

    }

    @Override
    public User saveUser(String name, String email, String pass, String confPass, String phone) throws ServiceException {
        if(checkEmail(email) == 1){
            throw new ServiceAuthException("Email has already existed!");
        }
        if(name.length()> 50){
            throw new ServiceAuthException("Name too long");
        }
        if(!ServiceValidation.isLoginValid(email)){
            throw new ServiceAuthException("Invalid email!");
        }
        if(!ServiceValidation.isPasswordValid(pass)){
            throw new ServiceAuthException("Invalid password!");
        }
        if(!ServiceValidation.isNewPasswordValid(pass, confPass)){
            throw new ServiceAuthException("Passwords are not identical");
        }
        if (ServiceValidation.isNullOrEmpty(name, phone)){
            throw new ServiceAuthException("Fields must not be empty!");
        }
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        User user = new User(name,email,pass,phone,LocalDateTime.now(), Role.USER);
        try {
            final User finalUser = user;
            user = DAOHelper.transactionExecute(() -> dao.save(finalUser));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User get(int id) throws ServiceException {

        if(id <= 0){
            throw new ServiceValidationException("User id must be positive number!");
        }
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        try {
            return DAOHelper.execute(() -> dao.get(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User update(int id, String... params) throws ServiceException {
        // validation
        if(ServiceValidation.isNotPositive(id)){
            throw new ServiceException("User id must be positive number!");
        }
        if (params.length != 7){
            throw new ServiceIncorrectParamLengthException("Param length must be 7");
        }
        String name = params[0];
        String email = params[1];
        String pass = params[2];
        String phone = params[3];
        String photo = params[4];
        String date = params[5];
        String role = params[6];
        if(!ServiceValidation.isLoginValid(email)){
            throw new ServiceAuthException("Incorrect email");
        }
        if(!ServiceValidation.isPasswordValid(pass)){
            throw new ServiceAuthException("Password is not valid");
        }
        if (ServiceValidation.isNullOrEmpty(name, phone, date, role) ){
            throw new ServiceValidationException("Fields must not be empty");
        }
        LocalDateTime dateReg = LocalDateTime.parse(date);
        Role userRole;
        try{
            userRole = Role.valueOf(role.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new ServiceValidationException("Such role does not exist");
        }
        //end validation

        User user = new User(id, name, email, pass, phone, photo, dateReg, userRole);
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        try {
            final User finalUser = user;
            user = DAOHelper.transactionExecute(() -> dao.update(finalUser));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        if(id <= 0){
            throw new ServiceValidationException("User id must be positive number!");
        }
        try {
            return DAOHelper.execute(() -> dao.delete(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PagingListDTO<User> getAll(int offset, int count) throws ServiceException {
        if(offset < 0 || count < 0){
            throw new ServiceValidationException("Offset and count must not be negative");
        }
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        try {
            return DAOHelper.execute(() -> {
                List<User> list = dao.getAll(offset, count);
                int countUsers = dao.getCountUsers();
                return new PagingListDTO<User>(countUsers, list);
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int checkEmail(String email) throws ServiceException {
        IUserDAO dao = DAOFactory.getMySqlDAOFactory().getIUserDAO();
        if(ServiceValidation.isNullOrEmpty(email)){
            throw new ServiceValidationException("Invalid email");
        }
        try {
            return DAOHelper.execute(() -> dao.checkIfEmailExist(email));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserPass(User loggedUser, String newPass, String confirmPass) throws ServiceException{
        if(ServiceValidation.isNullOrEmpty(newPass, confirmPass)){
            throw new ServiceValidationException("Fields must not be empty");
        }
        if(!newPass.equals(confirmPass)){
            throw new ServiceValidationException("New password and confirm password must be identical");
        }
        update(loggedUser.getId(), loggedUser.getName(), loggedUser.getEmail(), newPass, loggedUser.getPhone(), loggedUser.getPhoto(),
                loggedUser.getDateRegistration().toString(), loggedUser.getRole().name());
    }
}
