package edu.sit.signal.feedback;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Suthat
 */
@WebServlet(name = "OtpApis", urlPatterns = {"/otp-apis"})
public class OtpApis extends HttpServlet {

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
        
        String token = request.getParameter("token");
        String fbref = request.getParameter("fbref");
        String uxt = request.getParameter("uxt");
        String callback = request.getParameter("callback");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DbApis dbApis = new DbApis();
        dbApis.connect();
        
        try {
            
            if(token != null && fbref != null && uxt != null){
                
                boolean accepted = dbApis.authorizedAckRequest(token);
                
                if(accepted){
                    Helpers helpers = new Helpers();
                    String code = helpers.randomString();
                    code = helpers.generateToken(code);

                    dbApis.createFbOTP(code, token, fbref);

                    // force check a NULL callback
                    try{
                        if(callback.equals("SIGNAL"));
                    }catch(Exception e){
                        callback = "";
                    }
                    
                    // 200
                    if(callback.startsWith("jQuery"))
                        out.println(callback+"({"
                                + "\"status\":200,"
                                + "\"message\":\"OTP Access code has been generated\","
                                + "\"code\":\""+code+"\""
                                + "})");
                    else
                        out.println("{"
                                + "\"status\":200,"
                                + "\"message\":\"OTP Access code has been generated\","
                                + "\"code\":\""+code+"\""
                                + "}");
                }else{
                    // 401
                    out.println("{"
                                + "\"status\":401,"
                                + "\"message\":\"Unauthorized token, fbref, or unix time\""
                                + "}");
                }
            }else{
                // 500
                out.println("{"
                            + "\"status\":500,"
                            + "\"message\":\"Requires 3 params such token, fbref, and unix time\""
                            + "}");
            }
            
        } finally {            
            out.close();
            dbApis.disconnect();
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
