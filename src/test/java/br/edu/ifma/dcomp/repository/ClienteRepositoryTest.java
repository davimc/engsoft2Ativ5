package br.edu.ifma.dcomp.repository;

import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

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
    @Test
    public void salvaComEnderecoVazio() throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
            cliente.setEndereco("");
            repository.save(cliente);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O endereco deve ser preenchido"));
    }
    @Test
    public void testaSalvar(){
        repository.save(cliente);
        List<Cliente> clientes = repository.findAll();
        Assertions.assertEquals(1,clientes.size());
        repository.deleteAll();
    }
    @Test
    public void testaEncontrarPorTelefone(){
        repository.save(cliente);
        Optional<Cliente> cliente = repository.findByTelefone("982186943");
        Assertions.assertTrue(cliente.isPresent());
        repository.deleteAll();
    }
}
