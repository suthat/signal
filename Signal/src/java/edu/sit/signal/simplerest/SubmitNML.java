package edu.sit.signal.simplerest;

import com.google.gson.Gson;
import edu.sit.signal.apis.DbApis;
import edu.sit.signal.nml.NMLSchema;
import edu.sit.signal.parser.NMLParser;
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
@WebServlet(name = "SubmitNML", urlPatterns = {"/submit-nml"})
public class SubmitNML extends HttpServlet {

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

        String body = request.getParameter("body");
        String callback = request.getParameter("callback");

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            body = body.trim();
            if (body.equals("")) {
                out.println("{"
                        + "\"status\":500,"
                        + "\"message\":\"parsing NDL json failed - No Body\""
                        + "}");
            } else {
                NMLSchema nmlSchema = new Gson().fromJson("" + body, NMLSchema.class);

                DbApis dbApis = new DbApis();
                dbApis.connect();
                String accepted = dbApis.authorizedProvider(nmlSchema.getSignalnml().getAuth().getId(), nmlSchema.getSignalnml().getAuth().getToken());
                dbApis.disconnect();

                if (accepted != null) {
                    
                    //System.out.println(nmlSchema.getSignalnml().getNotification().getRequestapproach());
                    NMLParser parser = new NMLParser(nmlSchema);
                    parser.parseNDLfromJSONtoDb();
                    
                    out.println("{"
                            + "\"status\":200,"
                            + "\"message\":\"got it\""
                            + "}");
                } else {
                    out.println("{"
                            + "\"status\":401,"
                            + "\"message\":\"Unauthorized\""
                            + "}");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception PushWithNML.processRequest : " + e.getMessage());
            // 500
            out.println("{"
                    + "\"status\":500,"
                    + "\"message\":\"parsing NML json failed\""
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
