package br.edu.ifma.dcomp.models;

import javax.persistence.*;

@Entity
public class Frete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="cidade_id")
    private Cidade cidade;

    private String descricacao;
    private double peso;
    private double valor;

    public Frete() {
    }

    public Frete(Cliente cliente, Cidade cidade, String descricacao, double peso, double valor) {
        this.cliente = cliente;
        this.cidade = cidade;
        this.descricacao = descricacao;
        this.peso = peso;
        this.valor = valor;
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
}

