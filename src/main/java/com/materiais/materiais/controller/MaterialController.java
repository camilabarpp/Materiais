package com.materiais.materiais.controller;

import com.materiais.materiais.model.Material;
import com.materiais.materiais.service.MaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    //Método GET todos
    @GetMapping
    public ResponseEntity<List<Material>> findAll() {
        log.info("Mostrandos todos os materiais");
        return ResponseEntity.ok().body(materialService.findAll());
    }

    //Método GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable String id) {
        log.info(id + " encontrado!");
        return ResponseEntity.ok().body(materialService.findById(id));
    }

    //Método PUT
    @PutMapping("/{id}")
    public Optional<Material> update(@RequestBody Material newMaterial, @PathVariable String id) {
        log.info("Material alterado!");
        return this.materialService.update(newMaterial, id);
    }

    //Método POST
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody Material material) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(material.getId()).toUri();
        log.info("Material: " + material.getId() + ", " + material.getNome());
        return ResponseEntity.created(uri).body(materialService.create(material));
    }


    //Método DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Material deletado");
        this.materialService.delete(id);
        return ResponseEntity.noContent().build();
    }

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

        log.info("Adicionando cookies");
        return "Novo cookie criado";
    }

    //Mostrar todos os cookies
    @GetMapping("/cookies/get")
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + ": " + c.getValue()).collect(Collectors.joining("\n"));
        }
        log.info("Mostrando todos os cookies!");
        return "Nenhum cookie encontrado!";
    }
    /*
     *
     * ExceptionHandler
     *
     */
    @GetMapping("/")
    public void exceptionShot() {
        throw new NullPointerException();
    }

}
