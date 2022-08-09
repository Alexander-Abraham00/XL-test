package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

public class CommentCell implements Cell {
    private String comment;

    public CommentCell(String comment){
        this.comment = comment;
    }

    public String toString(){

        return comment;
    }

    @Override
    public ExprResult check(Environment env) throws Error {
        return new ErrorResult("Comment Reference");
    }
}
