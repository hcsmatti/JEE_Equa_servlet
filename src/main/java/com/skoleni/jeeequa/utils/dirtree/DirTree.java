/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skoleni.jeeequa.utils.dirtree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.IIOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author User
 */
public class DirTree extends HttpServlet {

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
            throws ServletException, IOException, FileNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String err = "";

        String path = request.getParameter("path");
        if (path == null || path.equals("")) {
            path = this.getServletContext().getRealPath("/");
        }
        try {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                //err = "Given path is wrong, you sent to Home<br><br>";
                response.sendError(400, "<br>Given path " + StringEscapeUtils.escapeHtml(path) + " is wrong");
                //throw new UnknownHostException("<br>Given path " + StringEscapeUtils.escapeHtml(path) + " is wrong");

            }


            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DirTree</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(err);

            out.println("<a href=\"DirTree\">Get me Home!</a><br/><br>");
            out.println("Current path : " + StringEscapeUtils.escapeHtml(path) + "<br/>");
            out.println(upperDirLink(path));
            // show directory and file list
            for (File entry : listFileTree(path)) {
                out.println(makeLink(entry, response, request) + "<br/>");
            }

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    /**
     * Liest of directories and files at given path
     *
     * @param directory String
     * @return Array of Files
     */
    public static File[] listFileTree(String directory) {
        File[] fileTree = null;

        File dir = new File(directory);
        if (dir.isFile()) {
            dir.getName();
        } else {
            fileTree = dir.listFiles();
        }
        sortFileList(fileTree);
        return fileTree;
    }

    /**
     * Make link from input file/directory
     *
     * @param dir File
     * @return link
     */
    public static String makeLink(File dir,HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        String link;

        if (dir.isDirectory()) {
            link = "Dir : <a href=\"DirTree?path=" + URLEncoder.encode(dir.getPath(), "UTF-8") + "\">" + StringEscapeUtils.escapeHtml(dir.getName()) + "</a>";
        } else {
            link = "File : <a href=\"./FileSend?file=" + URLEncoder.encode(dir.getPath(), "UTF-8") + "\">" + StringEscapeUtils.escapeHtml(dir.getName()) + "</a>";
        }
        return link;
    }

    /**
     * Return link to upper directory
     *
     * @param directory String
     * @return String contains link to upper Directory if available
     */
    public static String upperDirLink(String directory) {
        String link;
        // Test of slash char used by filesystem
        if (directory.lastIndexOf("/") != -1) {
            directory = directory.substring(0, directory.lastIndexOf("/"));
            if (directory.lastIndexOf("/") == -1) {
                directory = directory + "/";
            }
        } else if (directory.lastIndexOf("\\") != -1) {
            directory = directory.substring(0, directory.lastIndexOf("\\"));

            if (directory.lastIndexOf("\\") == -1) {
                directory = directory + "\\";
            }
        }

        File dir = new File(directory);
        if (dir.canRead()) {
            link = "Root : <a href=\"DirTree?path=" + directory + "\">..</a><br/>";
        } else {
            link = "Acces Denied to upper directory.";
        }
        return link;
    }

    /**
     * Sort File Array, directory first
     *
     * @param fileTree File[]
     */
    public static void sortFileList(File[] fileTree) {
        Arrays.sort(fileTree, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory()) {
                    return o2.isDirectory() ? o1.compareTo(o2) : -1;
                } else if (o2.isDirectory()) {
                    return 1;
                }

                return o1.compareTo(o2);
            }
        });
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
