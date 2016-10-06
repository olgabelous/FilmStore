package by.epam.filmstore.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Olga Shahray
 */
public interface Command {

     void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
