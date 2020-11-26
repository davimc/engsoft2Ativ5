package br.edu.ifma.dcomp.builder;

import br.edu.ifma.dcomp.models.Cliente;

public class ClienteBuilder {
    private Cliente cliente;

    public ClienteBuilder() {
    }
    public static ClienteBuilder umCliente(){
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente();
        builder.cliente.setNome("Davi");
        builder.cliente.setEndereco("Rua dos prazeres");
        builder.cliente.setTelefone("982186943");

        return builder;
    }
    public ClienteBuilder comTelefone(String telefone){
        cliente.setTelefone(telefone);
        return this;
    }
    public Cliente constroi(){
        return cliente;
    }
}
