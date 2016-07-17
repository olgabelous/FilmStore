package by.epam.filmstore.start;

import by.epam.filmstore.dao.exception.DAOException;
import by.epam.filmstore.dao.poolconnection.ConnectionPoolException;

/**
 * Created by Olga Shahray on 18.06.2016.
 */
public class Main {
    public static void main(String[] args) throws DAOException, ConnectionPoolException {
        /*List<Discount> discountList = new ArrayList<>();
        discountList.add(new Discount(1, 100, 5));
        discountList.add(new Discount(2, 200, 10));
        discountList.add(new Discount(3, 500, 15));
        discountList.add(new Discount(4, 800, 20));

        ListIterator<Discount> iter = discountList.listIterator(1);
        while(iter.hasNext()) {
            Discount d = iter.previous();
            double v1 = d.getSumFrom();
            iter.next();
            double v2 = iter.next().getSumFrom();
            System.out.println(v1+" "+ v2);
            if(950 > v1 && 950 < v2){
                System.out.println(d.getValue());
                break;
            }

        }
        System.out.println("end");*/



    }
}
