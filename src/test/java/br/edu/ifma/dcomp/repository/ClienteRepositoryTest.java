package br.edu.ifma.dcomp.repository;

import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ConstraintViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    private Cliente cliente;

    @BeforeEach
    public void start(){
        cliente = new Cliente("Davi","rua dos prazeres","982186943");
    }

    @Test
    public void salvaComNomeNulo()throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
           cliente.setNome(null);
           repository.save(cliente);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O nome deve ser preenchido"));
    }
}
