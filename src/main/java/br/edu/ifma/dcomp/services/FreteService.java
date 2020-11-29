package br.edu.ifma.dcomp.services;

import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreteService {
    private final double VALOR_FIXO = 10.00;
    @Autowired
    private FreteRepository repository;

    public void cadastra(Frete frete) throws FreteException {
        try{
            frete.setValor(calcula(frete));
            repository.save(frete);
        }catch (InvalidDataAccessApiUsageException e){
            throw new FreteException(e);
        }
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
