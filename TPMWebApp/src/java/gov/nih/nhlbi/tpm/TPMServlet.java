/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nih.nhlbi.tpm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author trairak
 */
public class TPMServlet extends HttpServlet {

    private int globalCount;
    private String uploadedFolderPath;
    private String outputFolderPath;

    @Override
    public void init() throws ServletException {
        super.init();
        globalCount = 0; // initialize the instance variable
        uploadedFolderPath = getServletContext().getRealPath("/uploadedFolder");
        outputFolderPath = getServletContext().getRealPath("/outputFolder");
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {

        HttpSession session = request.getSession();
        synchronized (session) {
            if (session.getAttribute("waitPage") == null) {

                // Check that we have a file upload request
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);

                if (isMultipart) {
                    // Create a factory for disk-based file items
                    FileItemFactory factory = new DiskFileItemFactory();

                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    // Parse the request
                    session.setAttribute("items", upload.parseRequest(request));
                }

                session.setAttribute("waitPage", Boolean.TRUE);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                try {
                    request.getRequestDispatcher("/includes/header.html").include(request, response);
                    out.println("<meta http-equiv='Refresh' content='0'>");
                    request.getRequestDispatcher("/includes/title.html").include(request, response);
                    out.println("<h2 style='color: blue; margin-left: 50px'>Your request is being processed<br>Please wait...</h2><br>");
                    request.getRequestDispatcher("/includes/footer.html").include(request, response);
                } finally {
                    out.close();
                }
            } else {
                session.removeAttribute("waitPage");
                List /* FileItem */ items = (List) session.getAttribute("items");

                // Process the uploaded items
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) { // Handle file uploads differently from regular form fields
                        String name = item.getFieldName();
                        String value = item.getString();
                    } else { // Process a file upload
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();

                        if (fileName != null) {
                            fileName = FilenameUtils.getName(fileName);
                        }

                        String uploadedFilePath = uploadedFolderPath + "/" + fileName;
                        File uploadedFile = new File(uploadedFilePath);
                        item.write(uploadedFile);

                        String outputSubFolder = "output_" + globalCount;
                        String outputFileName = outputSubFolder + ".txt";
                        boolean mkdir = new File(outputFolderPath + "/" + outputSubFolder).mkdir();
//                        System.out.println("mkdir = " + mkdir);
                        File file = new File(outputFolderPath + "/" + outputSubFolder + "/" + outputFileName);

                        String outputFilePath = file.getPath();
                        boolean success = dynamic.dynomite(uploadedFilePath, outputFilePath);
                        if (success) {
                            TreeSet<String> graphSet = Graph.getGraph(outputFilePath, outputFolderPath + "/" + outputSubFolder);

                            request.setAttribute("outputFileName", outputSubFolder + "/" + outputFileName);
                            request.setAttribute("outputSubFolder", outputSubFolder);
                            request.setAttribute("graphSet", graphSet);
                            // forward request and response objects to output.jsp page
                            RequestDispatcher dispatcher =
                                    getServletContext().getRequestDispatcher("/output.jsp");
                            dispatcher.forward(request, response);
                        } else {
                            request.setAttribute("message", "Invalid input format: please see the example and resubmit");
                            // forward request and response objects to output.jsp page
                            RequestDispatcher dispatcher =
                                    getServletContext().getRequestDispatcher("/index.jsp");
                            dispatcher.forward(request, response);
                        }

//                    response.setContentType("text/html;charset=UTF-8");
//                    PrintWriter out = response.getWriter();
//                    try {
//                        out.println("<html>");
//                        out.println("<head>");
//                        out.println("<title>Servlet TPMServlet</title>");
//                        out.println("</head>");
//                        out.println("<body>");
//                        out.println("<h1>outputFileName = " + outputFileName + "</h1>");
//                        out.println("<h1>outputSubFolder = " + outputSubFolder + "</h1>");
//                        out.println("<h1>outputFilePath = " + outputFilePath + "</h1>");
//                        out.println("</body>");
//                        out.println("</html>");
//                    } finally {
//                        out.close();
//                    }

                        globalCount++;
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        } catch (FileUploadException ex) {
            Logger.getLogger(TPMServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TPMServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
        } catch (FileUploadException ex) {
            Logger.getLogger(TPMServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TPMServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
