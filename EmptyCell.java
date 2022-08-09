package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

public class EmptyCell implements Cell {


    public String toString() {

        return "";
    }

    @Override
    public ExprResult check(Environment env) throws Error {
        return new ErrorResult("Invalid comment");

    }
}
