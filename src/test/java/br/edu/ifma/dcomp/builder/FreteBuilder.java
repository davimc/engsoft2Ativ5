package br.edu.ifma.dcomp.builder;

import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;

public class FreteBuilder {
    private Frete frete;

    public FreteBuilder(){

    }
    public static FreteBuilder umFrete(Cliente cliente, Cidade cidade){
        FreteBuilder builder = new FreteBuilder();
        builder.frete = new Frete();
        builder.frete.setCidade(cidade);
        builder.frete.setCliente(cliente);
        builder.frete.setDescricacao("Geladeira");
        builder.frete.setPeso(15);
        builder.frete.setValor(1500);

        return builder;
    }
    public FreteBuilder comCliente(Cliente cliente){
        frete.setCliente(cliente);
        return this;
    }
    public FreteBuilder comCidade(Cidade cidade){
        frete.setCidade(cidade);
        return this;
    }
    public FreteBuilder comProduto(String descricacao, double peso){
        frete.setDescricacao(descricacao);
        frete.setPeso(peso);


        return this;
    }
    public FreteBuilder comValor(double valor){
        frete.setValor(valor);
        return this;
    }
    public Frete constroi(){
        return frete;
    }
}
