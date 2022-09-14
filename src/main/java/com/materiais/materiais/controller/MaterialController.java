package com.materiais.materiais.controller;

import com.materiais.materiais.model.Material;
import com.materiais.materiais.service.MaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    //Method GET all
    @GetMapping
    public ResponseEntity<List<Material>> findAll() {
        log.info("Mostrandos todos os materiais");
        return ResponseEntity.ok().body(materialService.findAll());
    }

    //Method GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable String id) {
        log.info(id + " encontrado!");
        return ResponseEntity.ok().body(materialService.findById(id));
    }

    //todo nao usar o responseentity
    //todo mapper, response e request(javax.validations) e entities

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Material update(@RequestBody Material newMaterial, @PathVariable String id) {
        log.info("Material alterado!");
        return this.materialService.update(newMaterial, id);
    }

    //Method POST
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody Material material) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(material.getId()).toUri();
        log.info("Material: " + material.getId() + ", " + material.getNome());

        if (material.getNome() == null || material.getMarca() == null || material.getQuantidade() == null) {
            throw new NullPointerException("Valores nulos ou em branco!");
        }
        return ResponseEntity.created(uri).body(materialService.create(material));
    }


    //Method DELETE
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Material deletado");
        this.materialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeesByIDs(@RequestParam List<String> ids){
        materialService.deleteAllById(ids);
    }

    //todo deleteall

    /*
       *
       * Cookies
       *
     */

    //Cadastrando cookies
   @PostMapping("/cookies/{Material}")
    public String setCookie(HttpServletResponse response, @PathVariable("Material") String username) {
        //create a cookie
        Cookie cookie = new Cookie("Material", username);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        //add cookie to response
        response.addCookie(cookie);

        return "Novo cookie criado";
    }

    //Mostrar todos os cookies
    @GetMapping("/cookies/get")
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + ": " +
                            c.getValue()).collect(Collectors.joining("\n"));
        }
        log.info("Mostrando todos os cookies!");
        return "Nenhum cookie encontrado!";
    }

    /*
       *
       *  Queries
       *
     */
    //http://localhost:8081/materiais/nome?q=pont
    @GetMapping("/nome")
    public ResponseEntity<List<Material>> findByNome(@RequestParam(value="q") String nome) {
        nome = URLEncoder.encode(nome, StandardCharsets.UTF_8);
        List<Material> list = materialService.findByNome(nome);

        if (list.isEmpty()) {
            throw new NullPointerException();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Material>> fullSearch(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String marca) {
        nome = URLEncoder.encode(nome, StandardCharsets.UTF_8);
        marca = URLEncoder.encode(marca, StandardCharsets.UTF_8);
        List<Material> listaDeMateriais = materialService.fullSearch(nome, marca);

        if (listaDeMateriais.isEmpty()) {
            throw new NullPointerException();
        }

        return ResponseEntity.ok().body(listaDeMateriais);
    }
}
