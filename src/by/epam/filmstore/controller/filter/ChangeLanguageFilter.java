package by.epam.filmstore.controller.filter;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Olga Shahray on 05.09.2016.
 */
public class ChangeLanguageFilter implements Filter {
    private static final String LANG = "lang";
    private static final String LOCALE = "locale";

    private static final Logger LOG = LogManager.getLogger(ChangeLanguageFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO: 23.09.2016 проверить локаль, request accept lang
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String lang = request.getParameter(LANG);

        HttpSession session = request.getSession(true);
        String loc = (String)session.getAttribute(LOCALE);

        if(loc == null && (lang==null || lang.isEmpty())){
            session.setAttribute(LOCALE, "ru");
            LOG.info("Local was set: ru");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(loc != null && (lang==null || lang.isEmpty())){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            session.setAttribute(LOCALE, lang);
            LOG.info("Local was set: "+ lang);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
