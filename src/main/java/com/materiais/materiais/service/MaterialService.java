package com.materiais.materiais.service;

import com.materiais.materiais.model.Material;
import com.materiais.materiais.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    //Método GET todos
    public List<Material> findAll() {
        return this.materialRepository.findAll();
    }

    //Método GEt por ID
    public Material findById(String id) {
        return this.materialRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Material inexistente"));
                //adicionar a excessao
    }

    //Method PUT
    @PutMapping("/employees/{id}")
    public Material update(@RequestBody Material newMaterial, @PathVariable String id) {
        return materialRepository.findById(id)
                .map(material -> {
                    material.setNome(newMaterial.getNome());
                    material.setMarca(newMaterial.getMarca());
                    material.setQuantidade(newMaterial.getQuantidade());
                    return materialRepository.save(material);
                })
                .orElseGet(() -> {
                    newMaterial.setId(id);
                    return materialRepository.save(newMaterial);
                });
    }

    //Método POST
    public Material create(Material material) {
        return this.materialRepository.insert(material);
    }

    //Método DELETE
    public void delete(String id) {
        findById(id);
        this.materialRepository.deleteById(id);
    }

    public List<Material> findByNome(String nome) {
        return materialRepository.findByNomeContainsIgnoreCase(nome);
    }

    public List<Material> findByMarca(String marca) {
        return materialRepository.findByMarcaContainsIgnoreCase(marca);
    }
}
