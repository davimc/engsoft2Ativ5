package br.edu.ifma.dcomp.repository;

import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.repositories.CidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository repository;

    private Cidade Cidade;

    @BeforeEach
    public void start(){
        Cidade = new Cidade("São Luís","MA",13.5);
    }

    @Test
    public void salvaComNomeNulo()throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
           Cidade.setNome(null);
           repository.save(Cidade);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O nome deve ser preenchido"));
    }
    @Test
    public void salvaComEnderecoVazio() throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
            //Cidade.setEndereco("");
            repository.save(Cidade);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O endereco deve ser preenchido"));
    }
    @Test
    public void testaSalvar(){
        repository.save(Cidade);
        List<Cidade> Cidades = repository.findAll();
        Assertions.assertEquals(1,Cidades.size());
        repository.deleteAll();
    }

}
