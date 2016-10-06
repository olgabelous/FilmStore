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
 * Filter checks access right of logged user to admin commands.
 *
 * @author Olga Shahray
 */
public class AdminFilter implements Filter {

    private List<CommandName> adminCommands;
    private static final String COMMAND = "command";
    private static final String USER = "user";
    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String ACCESS_DENIED_ERROR_USER = "accessDeniedErrorUser";
    private static final String MESSAGE = "Please log in";

    private static final Logger LOG = LogManager.getLogger(AdminFilter.class);

    /**
     * Method initializes list of admin commands.
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminCommands = new ArrayList<>();
        adminCommands.add(CommandName.ADMIN_GET_USERS);
        adminCommands.add(CommandName.ADMIN_GET_FILMS);
        adminCommands.add(CommandName.ADMIN_GET_GENRES);
        adminCommands.add(CommandName.ADMIN_GET_COUNTRIES);
        adminCommands.add(CommandName.ADMIN_GET_DISCOUNTS);
        adminCommands.add(CommandName.ADMIN_GET_FILM_MAKERS);
        adminCommands.add(CommandName.ADMIN_GET_COMMENTS);
        adminCommands.add(CommandName.ADMIN_ADD_PAGE_FILM);
        adminCommands.add(CommandName.ADMIN_SAVE_FILM);
        adminCommands.add(CommandName.ADMIN_SAVE_GENRE);
        adminCommands.add(CommandName.ADMIN_SAVE_COUNTRY);
        adminCommands.add(CommandName.ADMIN_SAVE_DISCOUNT);
        adminCommands.add(CommandName.ADMIN_SAVE_FILM_MAKER);
        adminCommands.add(CommandName.ADMIN_UPDATE_USER);
        adminCommands.add(CommandName.ADMIN_UPDATE_COMMENT);
        adminCommands.add(CommandName.ADMIN_DELETE_USER);
        adminCommands.add(CommandName.ADMIN_DELETE_FILM);
        adminCommands.add(CommandName.ADMIN_DELETE_GENRE);
        adminCommands.add(CommandName.ADMIN_DELETE_COUNTRY);
        adminCommands.add(CommandName.ADMIN_DELETE_DISCOUNT);
        adminCommands.add(CommandName.ADMIN_DELETE_FILM_MAKER);
        adminCommands.add(CommandName.ADMIN_DELETE_COMMENT);

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
        if(adminCommands.contains(command)){
            HttpSession session = request.getSession(false);
            if (session != null){
                User loggedUser = (User)session.getAttribute(USER);
                if (loggedUser != null) {
                    if (loggedUser.getRole().equals(Role.ADMIN)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    else{
                        LOG.warn("Access denied to command " + commandName + ". Role of logged user is not 'ADMIN'");
                        request.setAttribute(ACCESS_DENIED_ERROR_USER, MESSAGE);
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
        //do nothing
    }
}
