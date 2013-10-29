package edu.sit.signal.simplerest;

import edu.sit.signal.models.Mapper;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "IO_APIs", urlPatterns = {"/io-apis"})
public class IO_APIs extends HttpServlet {

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

        String accessToken = request.getParameter("access_token");

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Mapper mapperModel = new Mapper();
            mapperModel.connect();
            HashMap mapper = mapperModel.getMapper(accessToken, null);
            mapperModel.disconnect();
            
            if (mapper != null) {
                
                HashMap mapRequest = new HashMap();
                String maps[] = mapper.get("maps").toString().split(",");
                String tmp[];
                String reqVar;
                String reqMap;
                String custom = "";
                for(int i=0;i<maps.length;i++){
                    tmp = maps[i].split("AS");
                    reqVar = tmp[0].trim();
                    reqMap = tmp[1].trim();
                 
                    if(reqMap.equalsIgnoreCase("notification.data.topic")){
                        mapRequest.put("topic", request.getParameter(reqVar));
                    }else if(reqMap.equalsIgnoreCase("notification.data.message")){
                        mapRequest.put("message", request.getParameter(reqVar));
                    }else{
                        custom = custom + "\"" + reqMap.substring(25, reqMap.length()).concat("\":\"").concat(request.getParameter(reqVar)).concat("\",");
                    }     
                }
                custom = custom.substring(0, custom.length()-1);
                mapRequest.put("custom", custom);
                
                //System.out.println(mapRequest.get("topic").toString());
                //System.out.println(mapRequest.get("message").toString());
                //System.out.println(mapRequest.get("custom").toString());
                
                // 401
                out.println("{"
                        + "\"status\":200,"
                        + "\"message\":\"successful\""
                        + "}");
                
            } else {
                // 401
                out.println("{"
                        + "\"status\":401,"
                        + "\"message\":\"unauthorized - invalid access token\""
                        + "}");
            }
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
            // 500
            out.println("{"
                    + "\"status\":500,"
                    + "\"message\":\"invalid request\""
                    + "}");
        } finally {
            out.close();
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
