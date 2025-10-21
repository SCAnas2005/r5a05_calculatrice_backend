/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.devavance.calculatrice;

/**
 *
 * @author kauth_6
 */
public class Operation {
    private String operator;
    private Integer operande1;
    private Integer operande2;
    private Double result;
    
    public Operation(String operator, Integer operande1, Integer operande2, Double result){
        this.operator = operator;
        this.operande1 = operande1;
        this.operande2 = operande2;
        this.result = result;
    }
    
    public Operation() {
        this("", 0,0, 0.0);
    }
    
    
    public String getOperator() { return this.operator; }
    public Integer getOperande1() { return this.operande1; }
    public Integer getOperande2() { return this.operande2; }
    public Double getResult() { return this.result; } 
    
    
}
