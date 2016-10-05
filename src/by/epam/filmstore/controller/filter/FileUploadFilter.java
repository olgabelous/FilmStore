package by.epam.filmstore.controller.filter;

import by.epam.filmstore.controller.FileUploadWrapper;
import by.epam.filmstore.service.exception.ServiceValidationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter that wraps an underlying file upload request (before Servlet 3.0).
 *
 * <P>This filter should be configured only for those operations that use a
 * file upload request.
 */
public class FileUploadFilter implements Filter {
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    public void init(FilterConfig aConfig) throws ServletException {
        //do nothing
    }

    public void destroy() {
        //do nothing
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if ( isFileUploadRequest(request) ) {
            FileUploadWrapper wrapper = null;
            try {
                wrapper = new FileUploadWrapper(request);
            } catch (ServiceValidationException e) {
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(ERROR_PAGE).forward(request, servletResponse);
            }
            filterChain.doFilter(wrapper, servletResponse);
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isFileUploadRequest(HttpServletRequest request){
        return request.getMethod().equalsIgnoreCase("POST") &&
                        request.getContentType().startsWith("multipart/form-data");
    }
}
