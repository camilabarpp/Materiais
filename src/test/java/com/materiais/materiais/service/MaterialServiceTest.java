package com.materiais.materiais.service;

import com.materiais.materiais.configuration.exception.IdNotFoundException;
import com.materiais.materiais.model.Material;
import com.materiais.materiais.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@RequiredArgsConstructor
public
class MaterialServiceTest {

    @InjectMocks
    private MaterialService service;
    @MockBean
    private MaterialRepository repository;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Deve mostrar todos os materiais com sucesso")
    void shouldFindAllMaterials() {
        List<Material> materiais = new ArrayList<>();

        when(repository.findAll()).thenReturn(materiais);

        var actual = service.findAll();

        assertEquals(materiais, actual);

        verify(repository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("Deve procurar um material por ID")
    void shouldFindAMaterialByID() {
        String id = "1";
        Optional<Material> expect = ofNullable(createValidMaterial());

        when(repository.findById(id)).thenReturn(expect);

        var actual = ofNullable(service.findById(id));

        assertEquals(expect.isPresent(), actual.isPresent());
        assertEquals(expect.getClass(), actual.getClass());
    }

    @Test
    @DisplayName("Deve lançar IdNotFoundException quando procurar um ID inexistente")
    void shouldThrowsIdNotFoundExceptionWhenIDNotExists() {
        String id = "10";

        when(repository.findById(id)).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> service.findById(id));
    }

    @Test
    @DisplayName("Deve procuar um material por nome")
    void shouldAMaterialByName() {
        String name = "Lapiseira";
        List<Material> expect = singletonList(createValidMaterial());

        when(repository.findByNome(name)).thenReturn(expect);

        var actual = service.findByNome(name);

        assertNotNull(actual);
        assertEquals(expect.contains(name), actual.contains(name));
    }

    @Test
    @DisplayName("Não deve procurar um material por nome inexistente")
    void shouldNotShowAMaterialByName() {
        String name = "Lapiseira";

        when(repository.findByNome(name)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> service.findByNome(name));
    }

    @Test
    @DisplayName("Deve procuar um material por uma lista de nomes")
    void shouldAMaterialByNameAndBrand() {
        String name = "Lapiseira";
        String marca = "Leo";

        List<Material> expect = singletonList(createValidMaterial());

        when(repository.fullSearch(name, marca)).thenReturn(expect);

        var actual = service.fullSearch(name, marca);

        assertNotNull(actual);
        assertEquals(expect.contains(name), actual.contains(name));
    }

    @Test
    @DisplayName("Não deve procurar um material por nome e marca inexistente")
    void shouldNotShowAMaterialByNameAndBrand() {
        String name = "Lapiseira";
        String marca = "Leo";

        when(repository.fullSearch(name, marca)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> service.fullSearch(name, marca));
    }

    @Test
    @DisplayName("Deve atualizar um material por ID")
    void shouldUpdateAMaterialByID() {
        String id = "1";
        Material atualizada = createValidMaterial();

        when(repository.findById(id)).thenReturn(ofNullable(atualizada));
        when(repository.save(any())).thenReturn(atualizada);

        var actual = service.update(atualizada, id);

        assertNotNull(actual);
        assert atualizada != null;
        assertEquals(atualizada.getId(), actual.getId());
        assertEquals(atualizada.getNome(), actual.getNome());
        assertEquals(atualizada.getMarca(), actual.getMarca());
        assertEquals(atualizada.getQuantidade(), actual.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar IdNotFoundException ao tentar atualizar um material inexistente")
    void shouldNotUpdateAMaterialInvalid() {
        var material = new Material();

        when(repository.save(any())).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> service.update(null, null));

        verify(repository, never()).save(material);
    }

    @Test
    @DisplayName("Deve criar um material com sucesso")
    void shouldCreateAMaterial() {
       Material material = createValidMaterial();

       when(repository.save(any(Material.class))).thenReturn(material);

       var actual = service.create(material);

       assertNotNull(actual);
       assertEquals(material.getId(), actual.getId());
       assertEquals(material.getNome(), actual.getNome());
       assertEquals(material.getMarca(), actual.getMarca());
       assertEquals(material.getQuantidade(), actual.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar criar um material com dados insuficientes")
    void shouldNotCreateAMaterial() {
        var material = new Material();

        when(repository.save(material)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class,
                () -> service.create(material));
    }

    @Test
    @DisplayName("Deve deletar um material por ID")
    void shouldDeleteAMaterialByID() {
        String id = "1";
        assertDoesNotThrow( () -> repository.deleteById(id));
        service.delete(id);
        verify(repository, atLeastOnce()).deleteById(id);
    }

    @Test
    @DisplayName("Deve deletar um material por uma lista de IDs")
    void shouldDeleteAMaterialByIDs() {
        List<String> ids = Arrays.asList("1", "2");
        assertDoesNotThrow( () -> repository.deleteAllById(ids));
        service.deleteAllById(ids);
        verify(repository, atLeastOnce()).deleteAllById(ids);
    }

    public static Material createValidMaterial() {
        return Material.builder().id("1")
                .nome("Caneta")
                .marca("Leo")
                .quantidade(10)
                .build();
    }
}