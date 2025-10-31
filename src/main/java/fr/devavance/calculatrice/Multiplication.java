package fr.devavance.calculatrice;

public class Multiplication implements IOperation{
    /**
     * Multiplication
     * @param operande1 : première opérande
     * @param operande2 : seconde opérande
     * @return Multiplication des deux opérances
     */
    @Override
    public double calculate(Integer operande1, Integer operande2) {
        // TODO Auto-generated method stub
        return Double.valueOf(operande1 * operande2);
    }
}
