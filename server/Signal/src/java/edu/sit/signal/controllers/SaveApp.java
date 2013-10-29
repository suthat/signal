/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sit.signal.controllers;

import edu.sit.signal.models.App;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Suthat
 */
@WebServlet(name = "SaveApp", urlPatterns = {"/save-app"})
public class SaveApp extends HttpServlet {

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
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if(action.equals("android")){
            String uuid = request.getParameter("uuid");
            String googleAPIsKey = request.getParameter("googleapiskey");
            
            App app = new App();
            app.connect();
            HashMap gcm = app.getCGM(uuid);
            if(gcm == null){
                app.addGCM("insert", uuid, googleAPIsKey.trim());
            }else{
                app.addGCM("update", uuid, googleAPIsKey.trim());
            }
            app.disconnect();
            
            response.sendRedirect("custom_android");
        }
        
        if(action.equals("ios-password")){
            String uuid = request.getParameter("uuid");
            String password = request.getParameter("password");
            
            App app = new App();
            app.connect();
            HashMap apns = app.getAPNs(uuid);
            if(apns == null){
                app.addAPNs("insert", uuid, null, null);
                app.addAPNs("update", uuid, "password", password.trim());
            }else{
                app.addAPNs("update", uuid, "password", password.trim());
            }
            app.disconnect();
            
            response.sendRedirect("custom_ios");
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
