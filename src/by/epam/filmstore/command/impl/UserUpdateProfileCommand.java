package by.epam.filmstore.command.impl;

import by.epam.filmstore.command.Command;
import by.epam.filmstore.command.ParameterAndAttributeName;
import by.epam.filmstore.domain.User;
import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.IUserService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceException;
import by.epam.filmstore.service.exception.ServiceIncorrectParamLengthException;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * <p>Command implements a request of user to save changes in personal
 * information of user.  Command checks access right.</p>
 *
 * @author Olga Shahray
 */
public class UserUpdateProfileCommand implements Command {

    private static final String USER_PAGE = "Controller?command=user-personal-info";
    private static final String INDEX_PAGE = "Controller?command=load-main-page";
    private static final int ERROR_STATUS = 404;
    private static final String ACCESS_DENIED_ERROR = "accessDeniedError";
    private static final String MESSAGE = "Please log in";

    private static final Logger LOG = LogManager.getLogger(UserUpdateProfileCommand.class);

    /**
     * <p>Method gets parameters from request and send its to service layer.
     * Field "photo" is a name of uploaded picture. If user updates his photo, old photo
     * will be deleted.</p>
     *
     * @param request is wrapped request by class FileUploadWrapper
     *                @see by.epam.filmstore.controller.filter.FileUploadFilter
     *                @see by.epam.filmstore.controller.FileUploadWrapper
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loggedUser = (User) session.getAttribute(ParameterAndAttributeName.USER);
            if (loggedUser != null) {

                String name = request.getParameter(ParameterAndAttributeName.NAME);
                String email = request.getParameter(ParameterAndAttributeName.EMAIL);
                String phone = request.getParameter(ParameterAndAttributeName.PHONE);
                String photo = request.getParameter(ParameterAndAttributeName.PHOTO);
                photo = (photo == null || photo.isEmpty())? loggedUser.getPhoto() : photo;
                String oldUserPhoto = loggedUser.getPhoto();

                IUserService service = ServiceFactory.getInstance().getUserService();
                IFileStoreService fileStoreService = ServiceFactory.getInstance().getFileStoreService();
                try{
                    int id = loggedUser.getId();
                    User updatedUser = service.update(id, name, email, loggedUser.getPass(), phone, photo, loggedUser.getDateRegistration().toString(), loggedUser.getRole().name());
                    if(oldUserPhoto != null && !oldUserPhoto.isEmpty() && !oldUserPhoto.equals(photo)){
                        String photoStorePath = request.getServletContext().getInitParameter(ParameterAndAttributeName.PHOTO_STORE_PATH);
                        fileStoreService.delete(request.getServletContext().getRealPath(photoStorePath + oldUserPhoto));
                    }
                    session.setAttribute(ParameterAndAttributeName.USER, updatedUser);
                    response.sendRedirect(USER_PAGE);

                }catch (ServiceIncorrectParamLengthException e){
                    LOG.warn("Incorrect param length", e);
                    response.sendError(ERROR_STATUS);
                }
                catch (ServiceValidationException | NoSuchFileException e) {
                    LOG.warn("Data is not valid", e);
                    response.sendError(ERROR_STATUS);
                } catch (ServiceException e) {
                    LOG.error("Exception is caught", e);
                    response.sendError(ERROR_STATUS);
                }

            } else {
                LOG.warn("Access denied to command user-personal-info. Logged user is null");
                request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
                request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
            }
        } else {
            LOG.warn("Access denied to command user-personal-info. Session is null");
            request.setAttribute(ACCESS_DENIED_ERROR, MESSAGE);
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        }
    }
}
