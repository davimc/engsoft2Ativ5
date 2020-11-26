package br.edu.ifma.dcomp.builder;

import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;

public class FreteBuilder {
    private Frete frete;

    public FreteBuilder(){

    }
    public static FreteBuilder umFrete(){
        FreteBuilder builder = new FreteBuilder();
        builder.frete = new Frete();
        builder.frete.setCidade(CidadeBuilder.umaCidade().constroi());
        builder.frete.setCliente(ClienteBuilder.umCliente().constroi());
        builder.frete.getDescricacao("Geladeira");
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
    public Frete constroi(){
        return frete;
    }
}
