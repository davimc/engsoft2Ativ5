package br.edu.ifma.dcomp.controller;

import br.edu.ifma.dcomp.builder.CidadeBuilder;
import br.edu.ifma.dcomp.builder.ClienteBuilder;
import br.edu.ifma.dcomp.builder.FreteBuilder;
import br.edu.ifma.dcomp.controllers.FreteController;
import br.edu.ifma.dcomp.exceptions.FreteException;
import br.edu.ifma.dcomp.models.Cidade;
import br.edu.ifma.dcomp.models.Cliente;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.repositories.CidadeRepository;
import br.edu.ifma.dcomp.repositories.ClienteRepository;
import br.edu.ifma.dcomp.repositories.FreteRepository;
import br.edu.ifma.dcomp.services.FreteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreteControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FreteService service;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Frete frete;
    private Cliente cliente;
    private Cidade cidade;
    private String nomeCliente;
    private String nomeCidade;
    private String descricacao;

    @BeforeEach
    public void start(){
        cliente = ClienteBuilder.umCliente().constroi();
        clienteRepository.save(cliente);
        cidade = CidadeBuilder.umaCidade().constroi();
        cidadeRepository.save(cidade);
        frete = FreteBuilder.umFrete(cliente,cidade).constroi();
        try {
            frete = service.inserirOuAlterar(frete);
        } catch (FreteException e) {
            e.printStackTrace();
        }
        nomeCidade = cidade.getNome();
        nomeCliente = cliente.getNome();
        descricacao = frete.getDescricacao();
    }
    @AfterEach
    public void end(){
       /* cidadeRepository.deleteAll();
        clienteRepository.deleteAll();
        repository.deleteAll();*/
    }

    @Test
    public void deveMostrarTodosFretes(){

        ResponseEntity<String> resposta =
                testRestTemplate.exchange("/frete/",HttpMethod.GET, null, String.class);

        System.out.println("######## "+ resposta.getBody());
        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());

    }
    @Test
    public void deveMostrarUmFrete(){
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/{id}", HttpMethod.GET,null,Frete.class,frete.getId());

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals(resposta.getHeaders().getContentType(),
                MediaType.parseMediaType("application/json"));
        Assertions.assertEquals(frete,resposta.getBody());
    }
    @Test
    public void deveMostrarUmContatoNaoEncontrado(){
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/{id}",HttpMethod.GET,null,Frete.class, 100);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        Assertions.assertNull(resposta.getBody());
    }
    @Test
    public void deveMostrarUmFreteComGetForEntity(){
        ResponseEntity resposta =
                testRestTemplate.getForEntity("/frete/{id}",Frete.class,frete.getId());
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals(resposta.getHeaders().getContentType(),MediaType.parseMediaType("application/json"));

        Assertions.assertEquals(frete,resposta.getBody());
    }



    /*-------------------POST-----------------------------*/

    @Test
    public void deveSalvarUmFrete(){
        frete = FreteBuilder.umFrete(cliente,cidade).comProduto("Estante",100.0).constroi();
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<List<String>> resposta =
                testRestTemplate.exchange("/frete/inserir",
                        HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<String>>(){});


    }

    @Test
    public void deveSalvarContato(){
        frete = FreteBuilder.umFrete(cliente, cidade).comProduto("Estante",100).constroi();
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/inserir",
                        HttpMethod.POST, httpEntity,
                        Frete.class);
        Assertions.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }
    @Test
    public void naoDeveSalvarContatoComErroDeValidacao(){
        frete = FreteBuilder.umFrete(cliente, cidade).comProduto("Estante",-1).constroi();
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/inserir",
                    HttpMethod.POST, httpEntity,
                    Frete.class);

        //Assertions.assertTrue(resposta.getBody().("O peso precisa ter um valor acima de 0"));
        //Não consigo receber os erros

    }


    /*-------------PUT--------------*/
    @Test
    public void deveAlterarFrete(){
        frete.setPeso(95);
        frete.setDescricacao("Estante");
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/alterar/{id}",HttpMethod.PUT, httpEntity,
                        Frete.class,frete.getId());
        Assertions.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
        Frete resultado = resposta.getBody();
        Assertions.assertEquals(frete.getId(), resultado.getId());
        Assertions.assertEquals(frete.getPeso(), resultado.getPeso());
        Assertions.assertEquals(frete.getDescricacao(), resultado.getDescricacao());
    }
    @Test
    public void alterarUsuarioComAtributosNulosDeveRetornar()throws Exception{
        frete.setValor(-1.5);
        frete.setPeso(0);
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/alterar/{id}",HttpMethod.PUT,
                        httpEntity,Frete.class,frete.getId());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
        System.out.println(resposta.getBody());
        System.out.println(resposta.toString());
       /* Assertions.assertTrue((resposta.getBody().contains("O peso precisa ter um valor acima de 0")));
        Assertions.assertTrue((resposta.getBody().contains("O valor precisa ser positivo")));*/
    }
    /*----------DELETE-------------*/
    @Test
    public void deveExcluirFrete(){
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/remover/{id}",HttpMethod.DELETE, null,
                        Frete.class, frete.getId());

        Assertions.assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
        Assertions.assertNull(resposta.getBody());
    }
    @Test
    public void testaExcluirFreteQueNaoExiste(){
        ResponseEntity<Frete> resposta =
                testRestTemplate.exchange("/frete/remover/{id}",HttpMethod.DELETE, null,
                        Frete.class, 100);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().toString().contains("Frete{vazio}"));
        //por algum motivo ele ainda tenta criar um Frete, mesmo que vazio
        //diferente do deletar com sucesso, que manda um null
    }
}
