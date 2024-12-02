package expressivo;

import java.util.Map;

class Variable implements Expression {
    private final String var;
    
    
    public Variable(String var) {
        this.var = var;
        checkRep();
    }

   
    private void checkRep() {
        assert var.matches("[a-zA-Z]+");
    }
    
    @Override public String toString() {
        return var;
    }

    @Override public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) return false;
        Variable that = (Variable) thatObject;
        return this.var.equals(that.var);
    }

    @Override public int hashCode() {
        return var.hashCode();
    }

    @Override public Expression differentiate(String variable) {
        return var.equals(variable) ? new Number(1) : new Number(0);
    }

    @Override public Expression simplify(Map<String, Double> environment) {
        return environment.containsKey(var) ? new Number(environment.get(var)) : this;
    }

}
