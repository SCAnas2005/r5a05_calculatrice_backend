package fr.devavance.calculatrice.controller;


import fr.devavance.calculatrice.Addition;
import fr.devavance.calculatrice.Division;
import fr.devavance.calculatrice.Multiplication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.devavance.calculatrice.OperationInterface;
import fr.devavance.calculatrice.Soustraction;


import fr.devavance.calculatrice.exceptions.OperatorException;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author B. LEMAIRE
 * Controller pour la calculatrice
 * <p>
 * Ce code utilise volontairement des anti-patterns, il n'a pas un
 * bon "good smell"
 * Ce code doit être refactorisé  pour respecter les
 * principes du "good smell code"
 */


@WebServlet(urlPatterns = {"/calculate/*"})
public class CalculatorController extends HttpServlet {
    
    private Map<String, OperationInterface> operationMap; 
    private static final String URL_PARAM_OF_FIRST_OPERATOR = "operation";
    private static final String URL_PARAM_OF_FIRST_OPERANDE = "operande1";
    private static final String URL_PARAM_OF_SECOND_OPERANDE = "operande2";
    


    @Override
    public void init() throws ServletException {
        super.init();
        operationMap = new HashMap<>();
        operationMap.put("add", new Addition());
        operationMap.put("sub", new Soustraction());
        operationMap.put("mult", new Multiplication());
        operationMap.put("div", new Division());


    }

    //<editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        String operation = request.getParameter(CalculatorController.URL_PARAM_OF_FIRST_OPERATOR);
        String operande1 = request.getParameter(CalculatorController.URL_PARAM_OF_FIRST_OPERANDE);
        String operande2 = request.getParameter(CalculatorController.URL_PARAM_OF_SECOND_OPERANDE);

        if (operation == null
                || operation.isEmpty()
                || !operationMap.containsKey(operation))

            throw new OperatorException();


        int op1 = Integer.parseInt(operande1);
        int op2 = Integer.parseInt(operande2);
        

        OperationInterface op = operationMap.get(operation);
        double resultat = op.calculer(op1, op2);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {


            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Calculator</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div>");
            out.println("<p class=\"operande\">Operande 1 : " + op1 + "</p>");
            out.println("<p class=\"operande\">Operande 2 : " + op2 + "</p>");
            out.println("<p class=\"operation\">Operateur : " + operation + "</p>");
            out.println("<p class=\"resultat\">resultat : " + resultat + "</p>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();


        }

    }
}
     