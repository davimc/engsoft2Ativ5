package br.edu.ifma.dcomp.controllers;

import br.edu.ifma.dcomp.exceptions.FreteException;
import br.edu.ifma.dcomp.models.Frete;
import br.edu.ifma.dcomp.services.FreteService;
import br.edu.ifma.dcomp.services.FreteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/frete")
public class FreteController {

    @Autowired
    private FreteService service;

    @GetMapping("/")
    public ResponseEntity<List<Frete>> fretes(){
        List<Frete> fretes = service.buscarFretes();
        return ResponseEntity.ok(fretes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Frete> frete(@PathVariable int id){
        return service.buscarFrete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/inserir")
    public ResponseEntity<Frete> inserir(@RequestBody @Valid Frete frete) throws URISyntaxException, FreteException {
        frete = service.inserirOuAlterar(frete);
        return new ResponseEntity<>(frete, HttpStatus.CREATED);
    }
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Frete> alterar(@PathVariable int id, @RequestBody @Valid Frete frete) throws URISyntaxException, FreteException {
        frete = service.inserirOuAlterar(frete);
        return new ResponseEntity<>(frete, HttpStatus.CREATED);
    }
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Frete> remover(@PathVariable int id){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
