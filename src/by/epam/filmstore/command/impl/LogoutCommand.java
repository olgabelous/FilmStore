package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.PageName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>Command implements a request of user o logout from system.
 * It invalidates session and sends user to home page.</p>
 *
 * @author Olga Shahray
 */
public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        response.sendRedirect(PageName.INDEX_PAGE);
    }
}
