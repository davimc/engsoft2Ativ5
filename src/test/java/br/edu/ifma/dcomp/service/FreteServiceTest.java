package br.edu.ifma.dcomp.service;

import br.edu.ifma.dcomp.builder.CidadeBuilder;
import br.edu.ifma.dcomp.builder.ClienteBuilder;
import br.edu.ifma.dcomp.builder.FreteBuilder;
import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.CidadeRepository;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import br.edu.ifma.dcomp.exceptions.FreteException;
import br.edu.ifma.dcomp.services.FreteService;
import br.edu.ifma.dcomp.services.FreteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

@SpringBootTest
public class FreteServiceTest {

    @Autowired
    private FreteService service;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    private Frete frete;
    private Cidade cidade;
    private Cliente cliente;
    @BeforeEach
    public void start(){
        cliente = ClienteBuilder.umCliente().constroi();
        clienteRepository.save(cliente);
        cidade = CidadeBuilder.umaCidade().constroi();
        cidadeRepository.save(cidade);
        frete = FreteBuilder.umFrete(cliente,cidade).constroi();
    }

    @Test
    public void inserirOuAlterarFrete(){
        Assertions.assertDoesNotThrow(()->{
            service.inserirOuAlterar(frete);
        });
    }
    @Test
    public void inserirOuAlterarFreteComClienteNaoSalvo()throws Exception{
        Assertions.assertThrows(FreteException.class,()->{
           frete.setCliente(ClienteBuilder.umCliente().constroi());
           service.inserirOuAlterar(frete);
        },"deveria lançar um FreteException porque o cliente não está inserirOuAlterardo");
    }

    @Test
    public void inserirOuAlterarFreteComCidadeNaoSalva()throws Exception{
        Assertions.assertThrows(FreteException.class,()->{
            frete.setCidade(CidadeBuilder.umaCidade().constroi());
            service.inserirOuAlterar(frete);
        },"deveria lançar um FreteException porque o cliente não está inserirOuAlterardo");
    }
    @Test
    public void inserirOuAlterarFreteComPesoNegativo()throws Exception{
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            frete.setPeso(-1);
            service.inserirOuAlterar(frete);
        },"deveria lançar um ConstraintViolation porque o peso está negativo");
    }
    @Test
    public void testaMaiorFrete(){
        try {
            service.inserirOuAlterar(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Televisao",8).constroi();
            service.inserirOuAlterar(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Kindle",1).constroi();
            service.inserirOuAlterar(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Iphone",1).constroi();
            service.inserirOuAlterar(frete);
            frete = service.maiorFrete();
            Assertions.assertEquals("Geladeira",frete.getDescricacao());
        }catch(FreteException e){
            e.getMessage();
        }


    }


}
