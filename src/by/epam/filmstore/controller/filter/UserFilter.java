package by.epam.filmstore.controller.filter;

import by.epam.filmstore.controller.CommandHelper;
import by.epam.filmstore.controller.CommandName;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter checks access right of logged user to user commands.
 *
 * @author Olga Shahray
 */
public class UserFilter implements Filter {
    private List<CommandName> userCommands;

    private static final String COMMAND = "command";
    private static final String USER = "user";
    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String ACCESS_DENIED_ERROR_ADMIN = "accessDeniedErrorAdmin";
    private static final String MESSAGE = "Please log in";

    private static final Logger LOG = LogManager.getLogger(UserFilter.class);

    /**
     * Method initializes list of user commands.
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userCommands = new ArrayList<>();

        userCommands.add(CommandName.USER_PAY_ORDER);
        userCommands.add(CommandName.USER_DELETE_ORDER);
        userCommands.add(CommandName.USER_ADD_TO_CART);
        userCommands.add(CommandName.USER_CART);
        userCommands.add(CommandName.USER_GET_DISCOUNT);
        userCommands.add(CommandName.USER_DELETE_FAVORITE_FILM);
        userCommands.add(CommandName.USER_ADD_FAVORITE_FILM);
        userCommands.add(CommandName.USER_GET_FAVORITE_FILMS);
        userCommands.add(CommandName.USER_GET_COMMENTS);
        userCommands.add(CommandName.USER_GET_ORDERS);
        userCommands.add(CommandName.USER_ADD_COMMENT);
        userCommands.add(CommandName.USER_WATCH_FILM);
    }

    /**
     * <p>Method checks access right. If user has access right, filterChain calls doChain(servletRequest, servletResponse).
     * If user does not have access right, filter sends error message and forwards to home page.</p>
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String commandName = request.getParameter(COMMAND);
        if(commandName == null || commandName.isEmpty()){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        CommandName command  = CommandHelper.getInstance().getCommandName(commandName);
        if(userCommands.contains(command)){
            HttpSession session = request.getSession(false);
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                if (loggedUser != null) {
                    if (loggedUser.getRole().equals(Role.USER)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    else{
                        LOG.warn("Access denied to command " + commandName + ". Role of logged user is not 'USER'");
                        request.setAttribute(ACCESS_DENIED_ERROR_ADMIN, MESSAGE);
                        request.getRequestDispatcher(INDEX_PAGE).forward(request, servletResponse);
                    }
                }
                else{
                    LOG.warn("Access denied to command " + commandName + ". Logged user is null");
                    request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                    request.getRequestDispatcher(INDEX_PAGE).forward(request, servletResponse);
                }
            }
            else{
                LOG.warn("Access denied to command " + commandName + ". Session is null");
                request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                request.getRequestDispatcher(INDEX_PAGE).forward(request, servletResponse);
            }
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
