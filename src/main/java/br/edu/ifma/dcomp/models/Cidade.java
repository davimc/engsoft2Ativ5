package br.edu.ifma.dcomp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message="O nome deve ser preenchido")
    private String nome;
    @NotEmpty(message="O estado deve ser preenchido")
    private String uf;
    @PositiveOrZero(message = "A taxa deve ser maior ou igual a zero")
    private double taxa;

    public Cidade() {
    }

    public Cidade(String nome, String uf, double taxa) {
        this.nome = nome;
        this.uf = uf;
        this.taxa = taxa;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }
}
