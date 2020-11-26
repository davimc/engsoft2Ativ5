package br.edu.ifma.dcomp.repositories;

import br.edu.ifma.dcomp.models.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByTelefone(String telefone);
    @Query(value="From Cliente")
    List<Cliente> todos(Sort sort);
}
