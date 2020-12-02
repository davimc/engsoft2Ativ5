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


    /*----------DELETE-------------*/
}
