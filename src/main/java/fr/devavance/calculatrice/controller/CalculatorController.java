package fr.devavance.calculatrice.controller;


import fr.devavance.calculatrice.Operation;
import fr.devavance.calculatrice.Soustraction;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.devavance.calculatrice.Addition;
import fr.devavance.calculatrice.Division;
import fr.devavance.calculatrice.IOperation;
import fr.devavance.calculatrice.Multiplication;
import fr.devavance.calculatrice.exceptions.OperatorException;

import java.util.ArrayList;
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
    public static final String URL_PARAMETER_OF_OPERATOR= "operation";
    public static final String URL_PARAMETER_OF_FIRST_OPERANDE = "operande1";
    public static final String URL_PARAMETER_OF_SECOND_OPERANDE = "operande2";
    
    public static final String INVALID_OPERATION_ERROR_LABEL = "Opération Invalide !";

    public static final String ADD_LABEL = "add";
    public static final String SUBSTRACTION_LABEL = "sub";
    public static final String MULTIPLICATION_LABEL = "mul";
    public static final String DIVISION_LABEL = "div";


    
    private Map<String, IOperation> operationsMap;
    @Override
    public void init() throws ServletException {
        super.init();
        operationsMap = Map.of(
            CalculatorController.ADD_LABEL, new Addition(),
            CalculatorController.SUBSTRACTION_LABEL, new Soustraction(),
            CalculatorController.MULTIPLICATION_LABEL, new Multiplication(),
            CalculatorController.DIVISION_LABEL, new Division()
        );
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
        Operation operation = new Operation();
        extractAndCheckParamsFromURLParamaters(request, operation);
        proceedCalculation(operation);
        proceedView(response, operation);
    }
    
    private void extractAndCheckParamsFromURLParamaters(HttpServletRequest request, Operation operation){
        String operator = request.getParameter(CalculatorController.URL_PARAMETER_OF_OPERATOR);
        String operande1 = request.getParameter(CalculatorController.URL_PARAMETER_OF_FIRST_OPERANDE);
        String operande2 = request.getParameter(CalculatorController.URL_PARAMETER_OF_SECOND_OPERANDE);

        if (operator == null
                || operator.isEmpty()
                || !this.operationsMap.containsKey(operator))

            throw new OperatorException();

        int operande1Int = Integer.parseInt(operande1);
        int operande2Int = Integer.parseInt(operande2);
        
        operation.setOperator(operator);
        operation.setOperande1(operande1Int);
        operation.setOperande2(operande2Int);
    }

    private void proceedCalculation(Operation operation) throws ServletException {
        double operationResult;
        IOperation ioperation = this.operationsMap.get(operation.getOperator());
        operationResult = ioperation.calculate(operation.getOperande1(), operation.getOperande2());
        operation.setResult(operationResult);
    }   

    private void proceedView(HttpServletResponse response, Operation operation) throws ServletException, IOException {
    {
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
            out.println("<p class=\"operande\">Operande 1 : " + operation.getOperande1() + "</p>");
            out.println("<p class=\"operande\">Operande 2 : " + operation.getOperande2() + "</p>");
            out.println("<p class=\"operation\">Operateur : " + operation.getOperator() + "</p>");
            out.println("<p class=\"resultat\">resultat : " + operation.getResult() + "</p>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
}
     