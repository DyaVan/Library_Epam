package com.diachuk.library.input.validators;

import com.diachuk.library.manage.Message;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VA-N_ on 05.02.2017.
 */
public class BookAddInputValidator extends InputValidator {
    private int maxFileSize = 10 * 1000 * 1024;
    private DiskFileItemFactory factory = new DiskFileItemFactory();
    private ServletFileUpload upload = new ServletFileUpload(factory);
    private List<FileItem> fileItems;
    private HashMap<String, String> parameters = new HashMap<>();
    private Iterator<FileItem> iterator;

    @Override
    public boolean validateInput(HttpServletRequest request) {
        try {
            fileItems = upload.parseRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
            //todo logging
            return inputValidationService.inputInvalidVSErrorMessage(Message.TOO_BIG_FILE_OR_SMTH);
        }

        iterator = fileItems.iterator();
        while (iterator.hasNext()) {
            FileItem fileItem = iterator.next();
            if (fileItem.isFormField()) {
                parameters.put(fileItem.getFieldName(), fileItem.getString());
            } else {
                if (fileItem.getSize() != 0 ) {
                    if (fileItem.getContentType().equals("image/jpeg")) {
                        request.setAttribute("imageFile", fileItem);
                    } else {
                        inputValidationService.appendErrorMessage(Message.getInstance().getMessage(Message.ONLY_JPEG_IMAGES));
                        inputValidationService.setSuccessFlag(false);
                    }
                }
            }
        }

        inputValidationService.validateBookAddParameters(parameters);
        if (inputValidationService.isInputValid()) {
            request.setAttribute("parametersMap",parameters);
            return true;
        }
        return false;
    }
}
