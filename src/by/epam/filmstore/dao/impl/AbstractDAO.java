package by.epam.filmstore.dao.impl;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;
import by.epam.filmstore.util.DAOHelper;

import java.sql.Connection;


/**
 * Created by Olga Shahray on 17.07.2016.
 *
 * Абстрактный класс AbstractDAO имеет метод получения Connection из DAOHelper.
 * Создан для того, чтобы все DAO, наследующие данный класс, могли получить Connection для реализации всех
 * своих методов
 *
 */
public abstract class AbstractDAO {

    //получаем Connection либо exception в случае если соединения не существует
    protected Connection getConnection() throws ConnectionPoolException, DAOException {
        if(DAOHelper.getCurrentConnection() == null){
            throw new DAOException("Connection doesn't exist");
        }
        else{
            return DAOHelper.getCurrentConnection();
        }
    }
}
