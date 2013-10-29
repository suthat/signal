package edu.sit.signal.controllers;

import edu.sit.signal.models.App;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Suthat
 */
@WebServlet(name = "Build", urlPatterns = {"/build"})
public class Build extends HttpServlet {

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
            throws ServletException, IOException, NoSuchAlgorithmException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        String ios = request.getParameter("ios");
        String android = request.getParameter("android");
        String cli = request.getParameter("cli");
        String html5 = request.getParameter("html5");

        if (name.equals("") || description.equals("")) {
            System.out.println("fulfill request!");
        }
        if (ios == null) ios = "";
        if (android == null) android = "";
        if (cli == null) cli = "";
        if (html5 == null) html5 = "";
        
        HttpSession userSession = request.getSession(); 
        int user = Integer.parseInt(userSession.getAttribute("id").toString());
        
        String uuid = UUID.randomUUID().toString();
        String secret = UUID.randomUUID().toString();

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(secret.getBytes(), 0, secret.length());
        secret = new BigInteger(1, md5.digest()).toString(16);

        App app = new App();
        app.connect();
        HashMap appData = new HashMap();
        appData.put("user", user);
        appData.put("uuid", uuid);
        appData.put("secret", secret);
        appData.put("name", name);
        appData.put("description", description);
        
        appData.put("ios", ios);
        appData.put("android", android);
        appData.put("cli", cli);
        appData.put("html5", html5);
        
        boolean result = app.add(appData);
        app.disconnect();

        if (result) {
            response.sendRedirect("dashboard?created=" + result);
        } else {
            System.out.println("cannot add app!");
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
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