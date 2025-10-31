package fr.devavance.calculatrice;

public class Division implements IOperation{
    /**
     * Division
     * @param s_operande_1 : première opérande
     * @param s_operande_2 : seconde opérande
     * @return division des deux opérances
     * @throws ArithmeticException 
     */
    @Override
    public double calculate(Integer operande1, Integer operande2)  {
        if (operande2.intValue()==0) throw new ArithmeticException();
        return Double.valueOf(operande1 / (float) operande2);
    }
}
