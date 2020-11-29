package br.edu.ifma.dcomp.models;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Frete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name="cidade_id")
    private Cidade cidade;

    private String descricacao;
    @Positive(message = "O peso precisa ter um valor acima de 0")
    private double peso;
    @PositiveOrZero
    private double valor;

    public Frete() {
    }

    public Frete(Cliente cliente, Cidade cidade, String descricacao, double peso) {
        this.cliente = cliente;
        this.cidade = cidade;
        this.descricacao = descricacao;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getDescricacao() {
        return descricacao;
    }

    public void setDescricacao(String descricacao) {
        this.descricacao = descricacao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Frete{" +
                "cliente=" + cliente.getNome() +
                ", cidade=" + cidade.getNome()+"("+cidade.getUf().toUpperCase()+") " +
                ", descricacao='" + descricacao + '\'' +
                ", peso=" + peso +
                ", valor=" + valor +
                '}';
    }
}

