package com.materiais.materiais.controller;

import com.materiais.materiais.configuration.exception.IdNotFoundException;
import com.materiais.materiais.model.Material;
import com.materiais.materiais.service.MaterialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.materiais.materiais.service.MaterialServiceTest.createValidMaterial;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MaterialControllerTest {

    @InjectMocks
    private MaterialController controller;

    @Mock
    private MaterialService service;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve mostrar todos os materiais")
    void shoudShowAllMateriais() {
        List<Material> expect = new ArrayList<>();

        when(service.findAll()).thenReturn(expect);

        var actual = controller.findAll();

        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Deve procurar um material pelo id com sucesso.")
    void shouldShowAMaterialByID() {
        String id = "1";
        Material expect = createValidMaterial();

        when(service.findById(id)).thenReturn(expect);

        var actual = this.controller.findById(id);

        assertEquals(expect.getId(), actual.getId());
        assertEquals(expect.getNome(), actual.getNome());
        assertEquals(expect.getMarca(), actual.getMarca());
        assertEquals(expect.getQuantidade(), actual.getQuantidade());

        verify(this.service, atLeastOnce()).findById(id);
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar procurar um material pelo ID inexistente")
    void shouldThowsExceptionWhenFindByInvalidID() {
        String id = "10";

        when(service.findById(id)).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> controller.findById(id));
    }

    @Test
    @DisplayName("Deve procurar um material por nome com sucesso")
    void shouldFindAMaterialByNameWithSuccess() {
        String name = "Lapiseira";
        List<Material> material = singletonList(createValidMaterial());

        when(service.findByNome(name)).thenReturn(material);

        var actual = controller.findByNome(name);

        assertNotNull(actual);
        assertEquals(material.contains(name), actual.contains(name));
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar procurar um material por um nome inexistente no banco")
    void shouldThownsExceptionWhenFindByInvalidName() {
        String name = "1";

        when(service.findByNome(name)).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> controller.findByNome("Lapiseira"));
    }

    @Test
    @DisplayName("Deve procurar um material por nome e marca com sucesso")
    void shouldFindByNameAndBrandWithSuccess () {
        String name = "Lapiseira";
        String brand = "Leo";
        List<Material> materials = singletonList(createValidMaterial());

        when(service.fullSearch(name, brand)).thenReturn(materials);

        var actual = controller.fullSearch(name, brand);

        assertNotNull(actual);
        assertEquals(materials.contains(name), actual.contains(name));
        assertEquals(materials.contains(brand), actual.contains(brand));
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar procurar um material por nome e marca inexistente no banco")
    void shouldThrowsExceptionwhenFindByInvalidNameAndBrand() {
        String name = "1";
        String brand = "2";

        when(service.fullSearch(name, brand)).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> controller.fullSearch("Caderno", "Lico"));
    }

    @Test
    @DisplayName("Deve criar um material com sucesso")
    void shouldCreateAMaterialWithSuccess() {
        Material material = createValidMaterial();

        when(service.create(material)).thenReturn(material);

        var actual = controller.create(material);

        assertNotNull(actual);
        assertEquals(material.getId(), actual.getId());
        assertEquals(material.getNome(), actual.getNome());
        assertEquals(material.getMarca(), actual.getMarca());
        assertEquals(material.getQuantidade(), actual.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar cadastrar um material nulo")
    void shouldThowsExceptionWhenCreateAInvalidMaterial() {
        Material material = new Material();

        when(service.create(material)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class,
                () -> controller.create(material));
    }

    @Test
    @DisplayName("Deve alterar um material com sucesso")
    void shouldUpdateAMaterialWithsuccess() {
        String id = "1";
        Material material = createValidMaterial();

        when(service.update(any(), any())).thenReturn(material);

        var actual = controller.update(material, id);

        assertNotNull(actual);
        assertEquals(material.getId(), actual.getId());
        assertEquals(material.getNome(), actual.getNome());
        assertEquals(material.getMarca(), actual.getMarca());
        assertEquals(material.getQuantidade(), actual.getQuantidade());
    }

    @Test
    @DisplayName("Deve lanãr excessão quando tentar atualizar um material nulo")
    void shouldThrowsexceptionWhenUpdateAInvalidMaterial() {
        Material material = createValidMaterial();

        when(service.update(material, null)).thenThrow(IdNotFoundException.class);

        assertThrows(IdNotFoundException.class,
                () -> controller.update(material, null));
    }

    @Test
    @DisplayName("Deve deletar um material com sucesso")
    void shouldDeleteAMaterialwithSuccess() {
        String id = "1";

        doNothing()
                .when(this.service).delete(id);

        controller.delete(id);

        verify(this.service, atLeastOnce()).delete(id);
    }

    @Test
    @DisplayName("Deve deletar uma lista de material com sucesso")
    void shouldDeleteAListOfMaterialwithSuccess() {
        List<String> ids = Arrays.asList("1","2");

        doNothing()
                .when(this.service).deleteAllById(ids);

        controller.deleteEmployeesByIDs(ids);

        verify(this.service, atLeastOnce()).deleteAllById(ids);
    }

    @Test
    @DisplayName("Deve criar um cookie")
    void shouldCreateACookie() throws Exception {
        Cookie cookie = new Cookie("A", "B");

        mvc.perform(post("/materiais/cookies/" + cookie)
                        .cookie(cookie)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar todos os cookies")
    void shouldGetACookie() throws Exception {
        Cookie cookie = new Cookie("A", "V");

        mvc.perform(get("/materiais/cookies/get")
                        .cookie(cookie)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve testar se o cookie existe")
    void testNotExists() throws Exception {
        mvc.perform(get("/materiais/cookies/get"))
                .andExpect(cookie().doesNotExist("unknownCookie"));

    }
}
