package by.epam.filmstore.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Olga Shahray on 05.09.2016.
 */
public class ChangeLanguageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String lang = request.getParameter("lang");
        if(lang==null || lang.isEmpty()){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            HttpSession session = request.getSession(true);
            session.setAttribute("locale", lang);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
