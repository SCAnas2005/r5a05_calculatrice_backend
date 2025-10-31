/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.devavance.calculatrice;

/**
 *
 * @author joraw
 */
public class Multiplication implements OperationInterface{

    @Override
    public double calculer(int operande1, int operande2) {
        return operande1 * operande2;
    }
    
}
