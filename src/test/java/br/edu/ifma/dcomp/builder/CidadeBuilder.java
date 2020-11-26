package br.edu.ifma.dcomp.builder;

import br.edu.ifma.dcomp.models.Cidade;

public class CidadeBuilder {
    private Cidade cidade;

    public CidadeBuilder(){
    }
    public static CidadeBuilder umaCidade(){
        CidadeBuilder builder = new CidadeBuilder();
        builder.cidade = new Cidade();
        builder.cidade.setNome("São Luís");
        builder.cidade.setUf("MA");
        builder.cidade.setTaxa(13.5);

        return builder;
    }
    public CidadeBuilder comNome(String nome){
        cidade.setNome(nome);
        return this;
    }
    public CidadeBuilder comUf(String uf){
        cidade.setUf(uf);
        return this;
    }
    public CidadeBuilder comTaxa(double taxa){
        cidade.setTaxa(taxa);
        return this;
    }
    public Cidade constroi(){
        return cidade;
    }
}
