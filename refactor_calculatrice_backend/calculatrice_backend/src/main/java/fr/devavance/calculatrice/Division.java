/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.devavance.calculatrice;

/**
 *
 * @author joraw
 */
public class Division implements OperationInterface{

    @Override
    public double calculer(int operande1, int operande2) {
        if (operande2 == 0) throw new ArithmeticException("division par 0");
        return operande1/operande2;
    }
    
}
