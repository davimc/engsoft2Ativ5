package br.edu.ifma.dcomp.repositories;

import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {

    @Query("from Frete")
    List<Frete> todos(Sort sort);
    List<Frete> findByCliente(Cliente cliente, Sort sort);
}
