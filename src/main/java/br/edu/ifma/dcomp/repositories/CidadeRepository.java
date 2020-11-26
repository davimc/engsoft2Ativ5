package br.edu.ifma.dcomp.repositories;

import br.edu.ifma.dcomp.models.Cidade;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Cidade findByNome(String nome);
    @Query(value="From Cidade")
    List<Cidade> todos(Sort sort);
}
