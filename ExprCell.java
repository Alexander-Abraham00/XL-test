package model;

import expr.*;

import java.io.IOException;

public class ExprCell implements Cell{

    Expr expr;
    String exprString;

    public ExprCell(String expr){
        exprString = expr;
        try{
            this.expr = new ExprParser().build(expr);

        }catch (IOException e) {
            this.expr = new ErrorExpr(e.getMessage());
        }
    }

    public String toString(){

        return exprString;
    }

    @Override
    public ExprResult check(Environment env){
        return this.expr.value(env);
    }
}
