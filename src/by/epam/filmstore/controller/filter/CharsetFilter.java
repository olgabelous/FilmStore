package by.epam.filmstore.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter sets character encoding to servletRequest and servletResponse.
 * Encoding is init parameter of filter.
 *
 * @author Olga Shahray
 */
public class CharsetFilter implements Filter {

    private String encoding;
    private static final String CHARACTER_ENCODING = "characterEncoding";
    public static final Logger LOG = Logger.getLogger(CharsetFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHARACTER_ENCODING);
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
        //do nothing
    }


}
