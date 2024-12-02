package expressivo;

import java.util.Map;

class Number implements Expression {
    private final double n;
    
  
    public Number(double n) {
        this.n = n;
        checkRep();
    }

  
    public double getNumber() {
        return n;
    }

    private void checkRep() {
        assert n >= 0;
    }
    
    @Override public String toString() {
        return String.valueOf(n);
    }

    @Override public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) return false;
        Number that = (Number) thatObject;
        return this.n == that.n;
    }

    @Override public int hashCode() {
        return Double.hashCode(n);
    }

    @Override public Expression differentiate(String variable) {
        return new Number(0);
    }

    @Override public Expression simplify(Map<String, Double> environment) {
        return this;
    }

}
