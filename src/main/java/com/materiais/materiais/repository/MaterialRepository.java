package com.materiais.materiais.repository;

import com.materiais.materiais.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {

    List<Material> findByNomeContainsIgnoreCase(String nome);

    List<Material> findByMarcaContainsIgnoreCase(String marca);

}
