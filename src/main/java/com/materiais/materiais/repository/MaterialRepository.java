package com.materiais.materiais.repository;

import com.materiais.materiais.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {

    //Pesquisa Query Param com 1 parâmeto
    @Query("{ 'nome': { $regex: ?0, $options:  'i' } }")
    List<Material> findByNome(String nome);

    //Pesquisa Query Param com mais parâmetos
    @Query("{ $and: [ " +
            "{ 'nome': { $regex: ?0, $options: 'i' } }," +
            "{ 'marca': { $regex: ?1, $options: 'i' } }]}")
    List<Material> fullSearch(String nome, String marca);

}
