package by.epam.filmstore.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public interface Command {

     void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
