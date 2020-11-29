package br.edu.ifma.dcomp.repository;

import br.edu.ifma.dcomp.builder.CidadeBuilder;
import br.edu.ifma.dcomp.builder.ClienteBuilder;
import br.edu.ifma.dcomp.builder.FreteBuilder;
import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.CidadeRepository;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import br.edu.ifma.dcomp.repositories.FreteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FreteRepositoryTest {

    @Autowired
    private FreteRepository repository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private Frete frete;

    @BeforeEach
    public void start(){
        Cidade cidade = CidadeBuilder.umaCidade().constroi();
        Cliente cliente = ClienteBuilder.umCliente().constroi();
        cidadeRepository.save(cidade);
        clienteRepository.save(cliente);
        cidade = cidadeRepository.findByNome("São Luís");
        cliente = clienteRepository.findByTelefone("982186943").get();
        frete = FreteBuilder.umFrete(cliente, cidade).constroi();
    }


    @Test
    public void salvaCidadeNulo()throws Exception{
        Assertions.assertThrows(DataIntegrityViolationException.class,()->{
           frete.setCidade(null);
           repository.save(frete);
        },"Deveria lançar um DataIntegrytiViolationException");
    }
    @Test
    public void salvaComClienteNulo() throws Exception{
        Assertions.assertThrows(DataIntegrityViolationException.class,()->{
            frete.setCliente(null);
            repository.save(frete);
        },"Deveria lançar um DataIntegrytiViolationException");
    }

    @Test
    public void testaSalvarComPesoZero(){
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, ()->{
            frete.setPeso(0);
            repository.save(frete);
        },"Deveria lançar um ConstraintViolationException");
        System.out.println("Aqui"+exception.getMessage()+"Aqui");
        Assertions.assertTrue(exception.getMessage().contains("O peso precisa ter um valor acima de 0"));

    }
    @Test
    public void salvarUmFrete(){
        Assertions.assertDoesNotThrow(()->{
            repository.save(frete);
            assertEquals(1,repository.findAll().size());
            repository.deleteAll();
        });
    }

}
