package model;

import expr.Environment;
import expr.ExprResult;

public class CircularCell implements Cell{
    private String expr;

    public CircularCell(String exp){
        this.expr = exp;
    }


    @Override
    public ExprResult check(Environment env) throws StackOverflowError {
        throw new StackOverflowError("CircularReference");
    }


}
