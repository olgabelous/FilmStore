package by.epam.filmstore.controller.filter;

import by.epam.filmstore.controller.CommandHelper;
import by.epam.filmstore.controller.CommandName;
import by.epam.filmstore.domain.Role;
import by.epam.filmstore.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Shahray on 28.08.2016.
 */
public class AdminFilter implements Filter {
    private List<CommandName> adminCommands;
    private ServletContext context;
    private static final String COMMAND = "command";
    private static final String ERROR_PAGE = "error.jsp";

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

        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String commandName = request.getParameter(COMMAND);
        CommandName command  = CommandHelper.getInstance().getCommandName(commandName);
        if(adminCommands.contains(command)){
            HttpSession session = request.getSession(false);
            if (session != null){
                User loggedUser = (User)session.getAttribute("user");
                if (loggedUser != null && loggedUser.getRole().equals(Role.ADMIN)){
                    context.log("User is admin");
                    filterChain.doFilter(servletRequest, servletResponse);
                }
                else{
                    request.getRequestDispatcher(ERROR_PAGE).forward(servletRequest, servletResponse);
                }
            }
            else{
                request.getRequestDispatcher(ERROR_PAGE).forward(servletRequest, servletResponse);
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
