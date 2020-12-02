package br.edu.ifma.dcomp.exceptions;

public class FreteException extends Exception{
    public FreteException(Exception e) throws FreteException{
        super(e);
    }
}
