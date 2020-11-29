package br.edu.ifma.dcomp.services;

public class FreteException extends Exception{
    public FreteException(Exception e) throws FreteException{
        super(e);
    }
}
