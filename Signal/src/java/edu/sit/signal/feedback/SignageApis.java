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
@WebServlet(name = "SignageApis", urlPatterns = {"/signage-apis"})
public class SignageApis extends HttpServlet {

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
        
        String uuid = request.getParameter("uuid");
        String secret = request.getParameter("secret");
        String uxt = request.getParameter("uxt");
        String callback = request.getParameter("callback");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DbApis dbApis = new DbApis();
        dbApis.connect();
        
        try {
            
            if(uuid != null && secret != null && uxt != null){
                
                String accepted = dbApis.authorizedProvider(uuid, secret);
                
                if(accepted != null){
                    // check whether a signature is existed or not
                    String signature = dbApis.getNQLSignage("uuid", uuid);
                    
                    if(signature == null){
                        Helpers helpers = new Helpers();
                        signature = helpers.randomString();
                        signature = helpers.generateToken(signature);
                        dbApis.createNQLSignage(uuid, signature);
                    }

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
                                + "\"message\":\"Signature has been generated\","
                                + "\"signature\":\""+signature+"\""
                                + "})");
                    else
                        out.println("{"
                                + "\"status\":200,"
                                + "\"message\":\"Signature has been generated\","
                                + "\"signature\":\""+signature+"\""
                                + "}");
                }else{
                    // 401
                    out.println("{"
                                + "\"status\":401,"
                                + "\"message\":\"Unauthorized provider\""
                                + "}");
                }
            }else{
                // 500
                out.println("{"
                            + "\"status\":500,"
                            + "\"message\":\"Requires 3 params such uuid, secret, and unix time\""
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
