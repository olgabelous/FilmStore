package by.epam.filmstore.controller;

import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that get requested image from specified path and send it to jsp page.
 *
 * @author Olga Shahray
 */
public class ImageController extends HttpServlet {

    private static final String POSTER_STORE_PATH = "posterStorePath";
    private static final String PHOTO_STORE_PATH = "photoStorePath";
    private static final String GENERAL_IMAGE_PATH = "generalImagePath";
    private static final String IMG = "img";
    private static final String TYPE = "type";
    private static final String CONTENT_TYPE = "image/*";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IFileStoreService service = ServiceFactory.getInstance().getFileStoreService();
        String type = req.getParameter(TYPE);
        String fileStorePath = "";

        if("poster".equals(type)){
            String posterStorePath = req.getServletContext().getInitParameter(POSTER_STORE_PATH);
            fileStorePath = req.getServletContext().getRealPath(posterStorePath + req.getParameter(IMG));
        }
        else if("photo".equals(type)){
            String photoStorePath = req.getServletContext().getInitParameter(PHOTO_STORE_PATH);
            fileStorePath = req.getServletContext().getRealPath(photoStorePath + req.getParameter(IMG));
        }
        else{
            String generalImagePath = req.getServletContext().getInitParameter(GENERAL_IMAGE_PATH);
            fileStorePath = req.getServletContext().getRealPath(generalImagePath + req.getParameter(IMG));
        }
        byte[] image = service.get(fileStorePath);
        resp.setContentType(CONTENT_TYPE);
        resp.getOutputStream().write(image);
    }
}
