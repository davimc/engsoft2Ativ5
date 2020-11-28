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

    private Cidade cidade;

    @BeforeEach
    public void start(){
        cidade = new Cidade("São Luís","MA",13.5);
    }

    @Test
    public void salvaComNomeNulo()throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
           cidade.setNome(null);
           repository.save(cidade);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O nome deve ser preenchido"));
    }
    @Test
    public void salvaComEnderecoVazio() throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
            cidade.setUf("");
            repository.save(cidade);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O estado deve ser preenchido"));
    }
    @Test
    public void salvaComTaxaNegativa()throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,()->{
            cidade.setTaxa(-13.5);
            repository.save(cidade);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("A taxa deve ser maior ou igual a zero"));
    }
    @Test
    public void testaSalvar(){
        repository.save(cidade);
        List<Cidade> cidades = repository.findAll();
        Assertions.assertEquals(1,cidades.size());
        repository.deleteAll();
    }

    @Test
    public void testaDeletar(){
        repository.save(cidade);
        Assertions.assertDoesNotThrow(()->{
            repository.delete(cidade);
            Assertions.assertEquals(0,repository.findAll().size());
        });
    }
}
