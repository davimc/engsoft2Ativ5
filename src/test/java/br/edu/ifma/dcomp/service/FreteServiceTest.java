package br.edu.ifma.dcomp.service;

import br.edu.ifma.dcomp.builder.CidadeBuilder;
import br.edu.ifma.dcomp.builder.ClienteBuilder;
import br.edu.ifma.dcomp.builder.FreteBuilder;
import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.CidadeRepository;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import br.edu.ifma.dcomp.services.FreteException;
import br.edu.ifma.dcomp.services.FreteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void cadastraFrete(){
        Assertions.assertDoesNotThrow(()->{
            service.cadastra(frete);
        });
    }
    @Test
    public void cadastraFreteComClienteNaoSalvo()throws Exception{
        Assertions.assertThrows(FreteException.class,()->{
           frete.setCliente(ClienteBuilder.umCliente().constroi());
           service.cadastra(frete);
        },"deveria lançar um FreteException porque o cliente não está cadastrado");
    }
    @Test
    public void cadastraFreteComCidadeNaoSalva()throws Exception{
        Assertions.assertThrows(FreteException.class,()->{
            frete.setCidade(CidadeBuilder.umaCidade().constroi());
            service.cadastra(frete);
        },"deveria lançar um FreteException porque o cliente não está cadastrado");
    }
    @Test
    public void testaMaiorFrete(){
        try {
            service.cadastra(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Televisao",8).constroi();
            service.cadastra(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Kindle",1).constroi();
            service.cadastra(frete);
            frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Iphone",1).constroi();
            service.cadastra(frete);
            frete = service.maiorFrete();
            Assertions.assertEquals("Geladeira",frete.getDescricacao());
        }catch(FreteException e){
            e.getMessage();
        }


    }


}
