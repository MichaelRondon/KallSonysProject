package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.OmsApp;

import edu.puj.aes.pica.asperisk.domain.ProveedorProducto;
import edu.puj.aes.pica.asperisk.repository.ProveedorProductoRepository;
import edu.puj.aes.pica.asperisk.service.ProveedorProductoService;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProveedorProductoDTO;
import edu.puj.aes.pica.asperisk.service.mapper.ProveedorProductoMapper;
import edu.puj.aes.pica.asperisk.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProveedorProductoResource REST controller.
 *
 * @see ProveedorProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsApp.class)
public class ProveedorProductoResourceIntTest {

    private static final Long DEFAULT_ID_PRODUCTO_EN_PROVEEDOR = 1L;
    private static final Long UPDATED_ID_PRODUCTO_EN_PROVEEDOR = 2L;

    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;

    @Autowired
    private ProveedorProductoMapper proveedorProductoMapper;

    @Autowired
    private ProveedorProductoService proveedorProductoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProveedorProductoMockMvc;

    private ProveedorProducto proveedorProducto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProveedorProductoResource proveedorProductoResource = new ProveedorProductoResource(proveedorProductoService);
        this.restProveedorProductoMockMvc = MockMvcBuilders.standaloneSetup(proveedorProductoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProveedorProducto createEntity(EntityManager em) {
        ProveedorProducto proveedorProducto = new ProveedorProducto()
            .idProductoEnProveedor(DEFAULT_ID_PRODUCTO_EN_PROVEEDOR);
        return proveedorProducto;
    }

    @Before
    public void initTest() {
        proveedorProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProveedorProducto() throws Exception {
        int databaseSizeBeforeCreate = proveedorProductoRepository.findAll().size();

        // Create the ProveedorProducto
        ProveedorProductoDTO proveedorProductoDTO = proveedorProductoMapper.toDto(proveedorProducto);
        restProveedorProductoMockMvc.perform(post("/api/proveedor-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProveedorProducto in the database
        List<ProveedorProducto> proveedorProductoList = proveedorProductoRepository.findAll();
        assertThat(proveedorProductoList).hasSize(databaseSizeBeforeCreate + 1);
        ProveedorProducto testProveedorProducto = proveedorProductoList.get(proveedorProductoList.size() - 1);
        assertThat(testProveedorProducto.getIdProductoEnProveedor()).isEqualTo(DEFAULT_ID_PRODUCTO_EN_PROVEEDOR);
    }

    @Test
    @Transactional
    public void createProveedorProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proveedorProductoRepository.findAll().size();

        // Create the ProveedorProducto with an existing ID
        proveedorProducto.setId(1L);
        ProveedorProductoDTO proveedorProductoDTO = proveedorProductoMapper.toDto(proveedorProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorProductoMockMvc.perform(post("/api/proveedor-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProveedorProducto> proveedorProductoList = proveedorProductoRepository.findAll();
        assertThat(proveedorProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProveedorProductos() throws Exception {
        // Initialize the database
        proveedorProductoRepository.saveAndFlush(proveedorProducto);

        // Get all the proveedorProductoList
        restProveedorProductoMockMvc.perform(get("/api/proveedor-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedorProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProductoEnProveedor").value(hasItem(DEFAULT_ID_PRODUCTO_EN_PROVEEDOR.intValue())));
    }

    @Test
    @Transactional
    public void getProveedorProducto() throws Exception {
        // Initialize the database
        proveedorProductoRepository.saveAndFlush(proveedorProducto);

        // Get the proveedorProducto
        restProveedorProductoMockMvc.perform(get("/api/proveedor-productos/{id}", proveedorProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proveedorProducto.getId().intValue()))
            .andExpect(jsonPath("$.idProductoEnProveedor").value(DEFAULT_ID_PRODUCTO_EN_PROVEEDOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedorProducto() throws Exception {
        // Get the proveedorProducto
        restProveedorProductoMockMvc.perform(get("/api/proveedor-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedorProducto() throws Exception {
        // Initialize the database
        proveedorProductoRepository.saveAndFlush(proveedorProducto);
        int databaseSizeBeforeUpdate = proveedorProductoRepository.findAll().size();

        // Update the proveedorProducto
        ProveedorProducto updatedProveedorProducto = proveedorProductoRepository.findOne(proveedorProducto.getId());
        updatedProveedorProducto
            .idProductoEnProveedor(UPDATED_ID_PRODUCTO_EN_PROVEEDOR);
        ProveedorProductoDTO proveedorProductoDTO = proveedorProductoMapper.toDto(updatedProveedorProducto);

        restProveedorProductoMockMvc.perform(put("/api/proveedor-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorProductoDTO)))
            .andExpect(status().isOk());

        // Validate the ProveedorProducto in the database
        List<ProveedorProducto> proveedorProductoList = proveedorProductoRepository.findAll();
        assertThat(proveedorProductoList).hasSize(databaseSizeBeforeUpdate);
        ProveedorProducto testProveedorProducto = proveedorProductoList.get(proveedorProductoList.size() - 1);
        assertThat(testProveedorProducto.getIdProductoEnProveedor()).isEqualTo(UPDATED_ID_PRODUCTO_EN_PROVEEDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProveedorProducto() throws Exception {
        int databaseSizeBeforeUpdate = proveedorProductoRepository.findAll().size();

        // Create the ProveedorProducto
        ProveedorProductoDTO proveedorProductoDTO = proveedorProductoMapper.toDto(proveedorProducto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProveedorProductoMockMvc.perform(put("/api/proveedor-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProveedorProducto in the database
        List<ProveedorProducto> proveedorProductoList = proveedorProductoRepository.findAll();
        assertThat(proveedorProductoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProveedorProducto() throws Exception {
        // Initialize the database
        proveedorProductoRepository.saveAndFlush(proveedorProducto);
        int databaseSizeBeforeDelete = proveedorProductoRepository.findAll().size();

        // Get the proveedorProducto
        restProveedorProductoMockMvc.perform(delete("/api/proveedor-productos/{id}", proveedorProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProveedorProducto> proveedorProductoList = proveedorProductoRepository.findAll();
        assertThat(proveedorProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorProducto.class);
        ProveedorProducto proveedorProducto1 = new ProveedorProducto();
        proveedorProducto1.setId(1L);
        ProveedorProducto proveedorProducto2 = new ProveedorProducto();
        proveedorProducto2.setId(proveedorProducto1.getId());
        assertThat(proveedorProducto1).isEqualTo(proveedorProducto2);
        proveedorProducto2.setId(2L);
        assertThat(proveedorProducto1).isNotEqualTo(proveedorProducto2);
        proveedorProducto1.setId(null);
        assertThat(proveedorProducto1).isNotEqualTo(proveedorProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorProductoDTO.class);
        ProveedorProductoDTO proveedorProductoDTO1 = new ProveedorProductoDTO();
        proveedorProductoDTO1.setId(1L);
        ProveedorProductoDTO proveedorProductoDTO2 = new ProveedorProductoDTO();
        assertThat(proveedorProductoDTO1).isNotEqualTo(proveedorProductoDTO2);
        proveedorProductoDTO2.setId(proveedorProductoDTO1.getId());
        assertThat(proveedorProductoDTO1).isEqualTo(proveedorProductoDTO2);
        proveedorProductoDTO2.setId(2L);
        assertThat(proveedorProductoDTO1).isNotEqualTo(proveedorProductoDTO2);
        proveedorProductoDTO1.setId(null);
        assertThat(proveedorProductoDTO1).isNotEqualTo(proveedorProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proveedorProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proveedorProductoMapper.fromId(null)).isNull();
    }
}
