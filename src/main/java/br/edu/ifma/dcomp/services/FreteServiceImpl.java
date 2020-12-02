package br.edu.ifma.dcomp.services;

import br.edu.ifma.dcomp.exceptions.FreteException;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreteServiceImpl implements FreteService{
    private final double VALOR_FIXO = 10.00;
    @Autowired
    private FreteRepository repository;



//    public Cidade maiorDestinataria(){
//        repository.findAll().stream().
//    }

    @Override
    public List<Frete> buscarFretes() {
        return repository.findAll();
    }

    @Override
    public Optional<Frete> buscarFrete(int id) {
        return repository.findById(id);
    }

    public Frete inserirOuAlterar(Frete frete) {
        frete.setValor(calcula(frete));
        return repository.save(frete);
    }

    @Override
    public void remover(int id) {
        repository.deleteById(id);
    }
    public Frete maiorFrete(){
        List<Frete> fretes = repository.findAll();
        Frete maiorFrete = null;
        for (Frete frete : fretes) {
            if(maiorFrete==null) {
                maiorFrete = frete;
                continue;
            }
            maiorFrete = maiorFrete.getValor()<frete.getValor()?frete:maiorFrete;
        }
        return maiorFrete;
    }
    public double calcula(Frete frete){
        return frete.getPeso()*VALOR_FIXO+frete.getCidade().getTaxa();
    }
}
