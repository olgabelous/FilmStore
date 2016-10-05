package by.epam.filmstore.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Olga Shahray on 12.08.2016.
 */
public class CharsetFilter implements Filter {

    private String encoding;
    private ServletContext context;
    private static final String CHARACTER_ENCODIND = "characterEncoding";
    public static final Logger LOG=Logger.getLogger(CharsetFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHARACTER_ENCODIND);
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        //LOG.info("Charset was set.");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }


}
