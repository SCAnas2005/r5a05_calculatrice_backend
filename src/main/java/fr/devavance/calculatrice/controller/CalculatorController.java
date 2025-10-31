package fr.devavance.calculatrice.controller;


import fr.devavance.calculatrice.Operation;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.devavance.calculatrice.Calculator;
import fr.devavance.calculatrice.exceptions.OperatorException;

import java.util.ArrayList;


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

    

    private static ArrayList<String> permittedOperators = null;

    @Override
    public void init() throws ServletException {
        super.init();
        this.permittedOperators = new ArrayList<>();
        this.permittedOperators.add(Calculator.ADD_LABEL);
        this.permittedOperators.add(Calculator.SUBSTRACTION_LABEL);
        this.permittedOperators.add(Calculator.MULTIPLICATION_LABEL);
        this.permittedOperators.add(Calculator.DIVISION_LABEL);
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
                || !this.permittedOperators.contains(operator))

            throw new OperatorException();

        int operande1Int = Integer.parseInt(operande1);
        int operande2Int = Integer.parseInt(operande2);
        
        operation.setOperator(operator);
        operation.setOperande1(operande1Int);
        operation.setOperande2(operande2Int);
    }

    private void proceedCalculation(Operation operation) throws ServletException {
        double operationResult;

        switch (operation.getOperator()) {
            case Calculator.ADD_LABEL:
                operationResult = Calculator.addition(operation.getOperande1(), operation.getOperande2());
                break;
            case Calculator.SUBSTRACTION_LABEL:
                operationResult = Calculator.soustraction(operation.getOperande1(), operation.getOperande2());
                break;
            case Calculator.DIVISION_LABEL:
                operationResult = Calculator.division(operation.getOperande1(), operation.getOperande2());
                break;
            case Calculator.MULTIPLICATION_LABEL:
                operationResult = Calculator.multiplication(operation.getOperande1(), operation.getOperande2());
                break;
            default:
                throw new ServletException(CalculatorController.INVALID_OPERATION_ERROR_LABEL);
        }

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
     