package edu.sit.signal.feedback;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.parsers.NQLParser;
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
@WebServlet(name = "NqlApis", urlPatterns = {"/nql-apis"})
public class NqlApis extends HttpServlet {

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
        
        String signature = request.getParameter("signature");
        String nqlStatement = request.getParameter("nql");
        String callback = request.getParameter("callback");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DbApis dbApis = new DbApis();
        dbApis.connect();
        
        try {
            
            String accepted = dbApis.getNQLSignage("signage", signature);
            
            if(accepted != null){
                nqlStatement = URLDecoder.decode(nqlStatement, "UTF-8");

                NQLParser nqlParser = new NQLParser();
                String resultjSON = nqlParser.parseNQLfromDbtoJSON(nqlStatement);
                if(resultjSON == null){
                    out.println("{\"status\":500, \"message\":\"return null value\"}");
                }else{
                    
                    // force check a NULL callback
                    try{
                        if(callback.equals("SIGNAL"));
                    }catch(Exception e){
                        callback = "";
                    }
                    
                    if(callback.startsWith("jQuery")){
                        out.println(callback + "(" + resultjSON + ")");
                    }else{
                        out.println(resultjSON);
                    }
                }
            }else{
                out.println("{\"status\":401, \"message\":\"Unauthorized signature\"}");
            }
            
        } catch(Exception e) {
            out.println("{\"status\":500, \"message\":\"return null value\"}");
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
