package com.materiais.materiais.service;

import com.materiais.materiais.model.Material;
import com.materiais.materiais.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //Método PUT
    public Optional<Material> update(Material newMaterial, String id) {
        return Optional.of(this.materialRepository.findById(id)
                .map(material -> {
                    material.setNome(newMaterial.getNome());
                    material.setMarca(newMaterial.getMarca());
                    material.setQuantidade(newMaterial.getQuantidade());
                    return materialRepository.save(newMaterial);
                    }).orElseGet(() -> materialRepository.save(newMaterial)));

    }

    //Método POST
    public Material create(Material material) {
        return this.materialRepository.insert(material);
    }

    //Método DELETE
    public void delete(String id) {
        this.materialRepository.deleteById(id);
    }
}
