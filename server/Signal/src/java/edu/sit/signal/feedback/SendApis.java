package edu.sit.signal.feedback;

import edu.sit.signal.apis.DbApis;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Suthat
 */
@WebServlet(name = "SendApis", urlPatterns = {"/send-apis"})
public class SendApis extends HttpServlet {

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
        
        String code = request.getParameter("code");
        String token = request.getParameter("token");
        String fbref = request.getParameter("fbref");
        String data = request.getParameter("data");
        String callback = request.getParameter("callback");
       
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DbApis dbApis = new DbApis();
        dbApis.connect();
        
        try {
            
            if(code != null && token != null && fbref != null && data != null){
                
                boolean accepted = dbApis.getFbOTP(code, token, fbref);
                
                try{

                    if(accepted){

                        data = URLDecoder.decode(data, "UTF-8");
                        String dataSet[] = data.split("\\$\\.");

                        Commit commit = new Commit(token, fbref, dataSet);
                        Thread worker = new Thread(commit);
                        worker.start();

                        // !IMPORTANT - Make sure it is an OTP, remove a code after a job was finished
                        dbApis.removeFbOTP(code, token, fbref);

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
                                + "\"message\":\"Feedback committed\""
                                + "})");
                        else
                            out.println("{"
                                + "\"status\":200,"
                                + "\"message\":\"Feedback committed\""
                                + "}");
                    }else{
                        // 401
                        out.println("{"
                            + "\"status\":401,"
                            + "\"message\":\"Unauthorized code\""
                            + "}");
                    }
                    
                }catch(Exception e){
                    // 401
                    out.println("{"
                                + "\"status\":500,"
                                + "\"message\":\"Invalid arguments\""
                                + "}");
                }
                
            }else{
                // 500
                out.println("{"
                            + "\"status\":500,"
                            + "\"message\":\"Requires code, token, fbref, and data\""
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
