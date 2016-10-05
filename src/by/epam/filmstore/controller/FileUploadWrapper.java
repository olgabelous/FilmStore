package by.epam.filmstore.controller;

import by.epam.filmstore.service.IFileStoreService;
import by.epam.filmstore.service.ServiceFactory;
import by.epam.filmstore.service.exception.ServiceValidationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Olga Shahray on 06.09.2016.
 */
public class FileUploadWrapper extends HttpServletRequestWrapper {

    /**
     * Store regular params only. May be multivalued (hence the List).
     */
    private final Map<String, List<String>> regularParamsMap = new LinkedHashMap<>();

    /**
     * Store file params only.
     */
    private final Map<String, FileItem> fileParamsMap = new LinkedHashMap<>();
    private static final int FIRST_VALUE = 0;
    private ServletContext servletContext;
    private static final String FILE_STORE_PATH = "fileStorePath";
    private static final String POSTER_STORE_PATH = "posterStorePath";
    private static final String PHOTO_STORE_PATH = "photoStorePath";
    private static final String GENERAL_IMAGE_PATH = "generalImagePath";
    private static final String ENCODING_UTF8 = "UTF-8";

    /**
     * Constructor.
     */
    public FileUploadWrapper(HttpServletRequest request) throws IOException, ServiceValidationException {
        super(request);
        this.servletContext = request.getServletContext();
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            convertToMaps(fileItems);
        } catch (FileUploadException e) {
            throw new IOException("Cannot parse underlying request: " + e.toString());
        }
    }

    /**
     * Return all request parameter names, for both regular controls and file upload
     * controls.
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> allNames = new LinkedHashSet<>();
        allNames.addAll(regularParamsMap.keySet());
        allNames.addAll(fileParamsMap.keySet());
        return Collections.enumeration(allNames);
    }

    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        super.setCharacterEncoding(enc);
    }

    /**
     * Return the parameter value. Applies only to regular parameters, not to
     * file upload parameters.
     * <p>
     * <P>If the parameter is not present in the underlying request,
     * then <tt>null</tt> is returned.
     * <P>If the parameter is present, but has no  associated value,
     * then an empty string is returned.
     * <P>If the parameter is multivalued, return the first value that
     * appears in the request.
     */
    @Override
    public String getParameter(String name) {
        String result = null;
        List<String> values = regularParamsMap.get(name);
        if (values == null) {
            //you might try the wrappee, to see if it has a value
        } else if (values.isEmpty()) {
            //param name known, but no values present
            result = "";
        } else {
            //return first value in list
            result = values.get(FIRST_VALUE);
        }
        return result;
    }

    /**
     * Return the parameter values. Applies only to regular parameters,
     * not to file upload parameters.
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;
        List<String> values = regularParamsMap.get(name);
        if (values != null) {
            result = values.toArray(new String[values.size()]);
        }
        return result;
    }

    /**
     * Return a {@code Map<String, String[]>} for all regular parameters.
     * Does not return any file upload parameters at all.
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> pair : regularParamsMap.entrySet()) {
            List<String> list = pair.getValue();
            paramMap.put(pair.getKey(), list.toArray(new String[list.size()]));
        }
        return Collections.unmodifiableMap(paramMap);
    }

    /**
     * Return a {@code List<FileItem>}, in the same order as they appear
     * in the underlying request.
     */
    public List<FileItem> getFileItems() {
        return new ArrayList<>(fileParamsMap.values());
    }

    /**
     * Return the {@link FileItem} of the given name.
     * <P>If the name is unknown, then return <tt>null</tt>.
     */
    public FileItem getFileItem(String fieldName) {
        return fileParamsMap.get(fieldName);
    }


    /**
    * Сохраняет файл на сервере, в папке assets.
    * Сама папка должна быть уже создана.
    *
    * @param item
    * @throws Exception
    */
    private String saveAndGetPathUploadedFile(FileItem item) throws IOException, ServiceValidationException {
        if(item == null || item.getSize() == 0){
            return "";
        }
        if(!isImageFile(item)){
            throw new ServiceValidationException("Incorrect file type");
        }
        String ending = item.getName().substring(item.getName().lastIndexOf('.'));
        //String fileStorePath = servletContext.getInitParameter(FILE_STORE_PATH);
        String fileName = String.valueOf(System.currentTimeMillis()) + ending;
        String fileStorePath = "";
        String fieldName = item.getFieldName();
        if(fieldName.equals("poster")){
            fileStorePath =  servletContext.getRealPath(servletContext.getInitParameter(POSTER_STORE_PATH) + fileName);
        }
        else if(fieldName.equals("photo")){
            fileStorePath = servletContext.getRealPath(servletContext.getInitParameter(PHOTO_STORE_PATH) + fileName);
        }
        else{
            fileStorePath = servletContext.getRealPath(servletContext.getInitParameter(GENERAL_IMAGE_PATH) + fileName);
        }

        IFileStoreService fileStoreService = ServiceFactory.getInstance().getFileStoreService();

        //создаём файл
        //записываем в него данные
        return fileStoreService.save(item.get(), fileStorePath) ? fileName : "";

    }


    // PRIVATE
    private void convertToMaps(List<FileItem> fileItemList) throws IOException, ServiceValidationException {
        for (FileItem item : fileItemList) {
            if (isFileUploadField(item)) {
                fileParamsMap.put(item.getFieldName(), item);
                addPathOfFileItem(item.getFieldName(), saveAndGetPathUploadedFile(item));
            } else {
                if (alreadyHasValue(item)) {
                    addMultivaluedItem(item);
                } else {
                    addSingleValueItem(item);
                }
            }
        }
    }

    private boolean isFileUploadField(FileItem fileItem) {
        return !fileItem.isFormField();
    }

    private boolean alreadyHasValue(FileItem item) {
        return regularParamsMap.get(item.getFieldName()) != null;
    }

    private void addSingleValueItem(FileItem item) throws UnsupportedEncodingException {
        List<String> list = new ArrayList<>();
        list.add(item.getString(ENCODING_UTF8));
        regularParamsMap.put(item.getFieldName(), list);
    }

    private void addMultivaluedItem(FileItem item) throws UnsupportedEncodingException {
        List<String> values = regularParamsMap.get(item.getFieldName());
        values.add(item.getString(ENCODING_UTF8));
    }

    private void addPathOfFileItem(String fieldName, String path) {
        List<String> list = new ArrayList<>();
        list.add(path);
        regularParamsMap.put(fieldName, list);
    }

    private boolean isImageFile(FileItem item){
        String fileName = item.getName();
        return (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"));
    }
}
