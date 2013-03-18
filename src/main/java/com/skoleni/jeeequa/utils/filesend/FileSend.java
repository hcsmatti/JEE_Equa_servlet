/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skoleni.jeeequa.utils.filesend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
public class FileSend extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String path = request.getParameter("file");
        if (path == null || path.equals("")) {
            response.sendError(400, "No file parameter sent to server.");
        }
        try {
            File dir = new File(path);
            response.setContentType("application/zip");
            File toSend = new File(dir.getPath());
            response.setContentLength((int) toSend.length());
            response.setHeader("Content-Disposition", "attachment;filename=\"" + path + "\"");
            try {
                File f = new File(path);
                byte[] arBytes = new byte[(int) f.length()];
                FileInputStream is = new FileInputStream(f);
                is.read(arBytes);
                ServletOutputStream op = response.getOutputStream();
                op.write(arBytes);
                op.flush();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        } catch (Exception e) {
        }
        
        //response.sendRedirect(path);
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
