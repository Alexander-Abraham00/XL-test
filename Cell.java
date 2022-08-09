package model;

import expr.Environment;
import expr.ExprResult;

public interface Cell {
    ExprResult check(Environment env) throws Error;

}
