package com.materiais.materiais.service;

import com.materiais.materiais.model.Material;
import com.materiais.materiais.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@RequiredArgsConstructor
class MaterialServiceTest {

    @InjectMocks
    private MaterialService service;
    @Mock
    private MaterialRepository repository;

    @BeforeEach
    void setUp() {
        this.service = new MaterialService(repository);
    }

    @Test
    void update() {
        String id = "1";

        Material atualizar = Material.builder().id(id).build();

        Material atualizada = createValidMaterial();

        when(repository.save(atualizar)).thenReturn(atualizada);

        Material actual = service.update(atualizar, id);

        assertNotNull(actual);
    }

    @Test
    void create() {
        Material material1 = createValidMaterial();

       when(repository.save(any(Material.class))).thenReturn(material1);

       var response = service.create(material1);

       assertNotNull(response);

    }
    private Material createValidMaterial() {
        return Material.builder().id("1")
                .nome("Caneta")
                .marca("Leo")
                .quantidade(10)
                .build();
    }
}