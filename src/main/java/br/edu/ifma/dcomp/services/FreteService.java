package br.edu.ifma.dcomp.services;

import br.edu.ifma.dcomp.exceptions.FreteException;
import br.edu.ifma.dcomp.models.Frete;

import java.util.List;
import java.util.Optional;

public interface FreteService {

    Frete maiorFrete();

    List<Frete> buscarFretes();

    Optional<Frete> buscarFrete(int id);

    Frete inserirOuAlterar(Frete frete) throws FreteException;

    void remover(int id);
}
