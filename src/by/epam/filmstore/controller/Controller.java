package by.epam.filmstore.controller;

import by.epam.filmstore.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 23.07.2016.
 */
public class Controller extends HttpServlet {
    private static final String COMMAND = "command";
    private static final String ERROR_PAGE = "/WEB-INF/jsp/404.jsp";
    private static final String EXCEPTION = "exception";

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String commandName = req.getParameter(COMMAND);
            Command command = CommandHelper.getInstance().getCommand(commandName);
            if(command == null){
                LOG.warn("Command is null");
                req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            }
            else{
                command.execute(req, resp);
            }
        }
        catch (Exception e){
            LOG.error("Exception is caught", e);
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }

}
