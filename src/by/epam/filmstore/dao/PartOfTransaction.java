package by.epam.filmstore.dao;

/**
 * Created by Olga Shahray on 02.09.2016.
 *
 * Показывает, что метод является частью транзакции
 * В каждом методе используется Connection, полученный из DAOHelper (см. AbstractDAO и DAOHelper).
 * Возврат Connection в пул происходит в DAOHelper
 */
public @interface PartOfTransaction {
}
