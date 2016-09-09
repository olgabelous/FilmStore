package by.epam.filmstore.controller;

import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga Shahray on 07.09.2016.
 */
public class ImageController extends HttpServlet {
    private static final String FILE_STORE_PATH = "fileStorePath";
    private static final String IMG = "img";
    private static final String CONTENT_TYPE = "image/*";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IFileStoreService service = ServiceFactory.getInstance().getFileStoreService();
        String fileStorePath = req.getServletContext().getInitParameter(FILE_STORE_PATH);
        byte[] image = service.get(fileStorePath, req.getParameter(IMG));
        resp.setContentType(CONTENT_TYPE);
        resp.getOutputStream().write(image);//
        //IOUtils.toByteArray(image)
    }
}
