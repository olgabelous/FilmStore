package by.epam.filmstore.controller.filter;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter changes local according to required language.
 * Default local is init parameter of filter.
 *
 * @author Olga Shahray
 */
public class ChangeLanguageFilter implements Filter {

    private String defaultLocal;
    private static final String DEFAULT_LOCAL = "defaultLocal";
    private static final String LANG = "lang";
    private static final String LOCALE = "locale";
    private static final Logger LOG = LogManager.getLogger(ChangeLanguageFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocal = filterConfig.getInitParameter(DEFAULT_LOCAL);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String lang = request.getParameter(LANG);

        HttpSession session = request.getSession(true);
        String loc = (String)session.getAttribute(LOCALE);

        if(loc == null && (lang==null || lang.isEmpty())){
            session.setAttribute(LOCALE, defaultLocal);
            LOG.info("Local was set: " + defaultLocal);
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
        //do nothing
    }
}
