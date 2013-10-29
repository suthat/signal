/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sit.signal.controllers;

import edu.sit.signal.app.Config;
import edu.sit.signal.models.App;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Suthat
 */
@WebServlet(name = "UploadAPNsCer", urlPatterns = {"/upload-apns-cer"})
public class UploadAPNsCer extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {
        
        request.setCharacterEncoding("UTF-8");

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            System.out.println("Not MultipartContent!");
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> fields = upload.parseRequest(request);
            Iterator<FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                System.out.println("No Fields Found!");
            }
            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    // Pass
                } else {                    
                    HttpSession userSession = request.getSession();
                    App app = new App();
                    app.connect();
                    
                    HashMap appData = app.findByUser(userSession.getAttribute("id").toString());
                    
                    String tmp[] = appData.get("uuid").toString().split("-");
                    long unixTime = System.currentTimeMillis() / 1000L;
                    
                    Config config = new Config();
                    File disk = new File(config.apnsCerKeyPath+ "" +tmp[0]+"."+unixTime+".cer");
                    fileItem.write(disk);
                    
                    HashMap apns = app.getAPNs(appData.get("uuid").toString());
                    if(apns == null){
                        app.addAPNs("insert", appData.get("uuid").toString(), null, null);
                        app.addAPNs("update", appData.get("uuid").toString(), "certificate", tmp[0]+"."+unixTime+".cer");
                    }else{
                        app.addAPNs("update", appData.get("uuid").toString(), "certificate", tmp[0]+"."+unixTime+".cer");
                    }
                            
                    app.disconnect();
                }
            }
        } catch (FileUploadException e) {
            System.out.println("Exception : UploadAPNsCer.processRequest : " + e.getMessage());
        }

        response.sendRedirect("custom_ios");   
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(UploadAPNsCer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UploadAPNsCer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(UploadAPNsCer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UploadAPNsCer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
