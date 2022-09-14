package com.materiais.materiais.service;

import com.materiais.materiais.configuration.exception.IdNotFoundException;
import com.materiais.materiais.model.Material;
import com.materiais.materiais.repository.MaterialRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
@NoArgsConstructor
public class MaterialService {

    private MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    //Método GET todos
    public List<Material> findAll() {
        return this.materialRepository.findAll();
    }

    //Método GEt por ID
    public Material findById(String id) {
        return this.materialRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID não encontrado"));
                //adicionar a excessao
    }

    //Method PUT
    public Material update(Material newMaterial,String id) {
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
        return materialRepository.save(material);
    }

    //Método DELETE
    public void delete(String id) {
        findById(id);
        this.materialRepository.deleteById(id);
    }

    public List<Material> findByNome(String nome) {
        return materialRepository.findByNome(nome);
    }

    public List<Material> fullSearch(String nome, String marca) {
        return materialRepository.fullSearch(nome, marca);
    }

    public void deleteAllById(List<String> ids){
        materialRepository.deleteAllById(ids);
    }

}
