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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;

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
    Cidade cidade;
    Cliente cliente;

    @BeforeEach
    public void start(){
        cliente = ClienteBuilder.umCliente().constroi();
        cidade = CidadeBuilder.umaCidade().constroi();
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
    public void testaSalvarComPesoZero() throws Exception{
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, ()->{
            frete.setPeso(0);
            repository.save(frete);
        },"Deveria lançar um ConstraintViolationException");
        Assertions.assertTrue(exception.getMessage().contains("O peso precisa ter um valor acima de 0"));
    }
    @Test
    public void salvarUmFrete(){
        Assertions.assertDoesNotThrow(()->{
            frete.setCliente(ClienteBuilder.umCliente().constroi());
            repository.save(frete);
            assertEquals(1,repository.findAll().size());
            repository.deleteAll();
        });
    }
    @Test
    public void testaBuscarListarFretesDeUmClienteOrdenadosPorValor(){
        repository.save(frete);
        repository.save(FreteBuilder.umFrete(cliente,cidade).comProduto("Kindle",12.5).comValor(242.00).constroi());
        repository.save(FreteBuilder.umFrete(cliente,cidade).comProduto("Televisao",12.5).comValor(1000.50).constroi());
        Cliente cliente2 =ClienteBuilder.umCliente().comTelefone("982186942").comNome("Carol").constroi();
        clienteRepository.save(cliente2);
        repository.save(FreteBuilder.umFrete(cliente2, cidade).comProduto("Iphone",3300.00).comValor(20.00).constroi());
        List<Frete> fretes = repository.findByCliente(cliente, Sort.by("valor").ascending());
        fretes.stream().forEach(frete->{
            assertEquals("Davi",frete.getCliente().getNome());
        });
        assertEquals(2,fretes.get(0).getId());
    }
}
