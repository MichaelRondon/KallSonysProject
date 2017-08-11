package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.OmsApp;

import edu.puj.aes.pica.asperisk.domain.DatoContacto;
import edu.puj.aes.pica.asperisk.repository.DatoContactoRepository;
import edu.puj.aes.pica.asperisk.service.dto.DatoContactoDTO;
import edu.puj.aes.pica.asperisk.service.mapper.DatoContactoMapper;
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

import edu.puj.aes.pica.asperisk.domain.enumeration.TipoDatoContacto;
/**
 * Test class for the DatoContactoResource REST controller.
 *
 * @see DatoContactoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsApp.class)
public class DatoContactoResourceIntTest {

    private static final TipoDatoContacto DEFAULT_TIPO_DATO_CONTACTO = TipoDatoContacto.TELEFONO;
    private static final TipoDatoContacto UPDATED_TIPO_DATO_CONTACTO = TipoDatoContacto.DIRECCION;

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private DatoContactoRepository datoContactoRepository;

    @Autowired
    private DatoContactoMapper datoContactoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDatoContactoMockMvc;

    private DatoContacto datoContacto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DatoContactoResource datoContactoResource = new DatoContactoResource(datoContactoRepository, datoContactoMapper);
        this.restDatoContactoMockMvc = MockMvcBuilders.standaloneSetup(datoContactoResource)
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
    public static DatoContacto createEntity(EntityManager em) {
        DatoContacto datoContacto = new DatoContacto()
            .tipoDatoContacto(DEFAULT_TIPO_DATO_CONTACTO)
            .valor(DEFAULT_VALOR);
        return datoContacto;
    }

    @Before
    public void initTest() {
        datoContacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatoContacto() throws Exception {
        int databaseSizeBeforeCreate = datoContactoRepository.findAll().size();

        // Create the DatoContacto
        DatoContactoDTO datoContactoDTO = datoContactoMapper.toDto(datoContacto);
        restDatoContactoMockMvc.perform(post("/api/dato-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datoContactoDTO)))
            .andExpect(status().isCreated());

        // Validate the DatoContacto in the database
        List<DatoContacto> datoContactoList = datoContactoRepository.findAll();
        assertThat(datoContactoList).hasSize(databaseSizeBeforeCreate + 1);
        DatoContacto testDatoContacto = datoContactoList.get(datoContactoList.size() - 1);
        assertThat(testDatoContacto.getTipoDatoContacto()).isEqualTo(DEFAULT_TIPO_DATO_CONTACTO);
        assertThat(testDatoContacto.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createDatoContactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datoContactoRepository.findAll().size();

        // Create the DatoContacto with an existing ID
        datoContacto.setId(1L);
        DatoContactoDTO datoContactoDTO = datoContactoMapper.toDto(datoContacto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatoContactoMockMvc.perform(post("/api/dato-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datoContactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DatoContacto> datoContactoList = datoContactoRepository.findAll();
        assertThat(datoContactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDatoContactos() throws Exception {
        // Initialize the database
        datoContactoRepository.saveAndFlush(datoContacto);

        // Get all the datoContactoList
        restDatoContactoMockMvc.perform(get("/api/dato-contactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datoContacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDatoContacto").value(hasItem(DEFAULT_TIPO_DATO_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }

    @Test
    @Transactional
    public void getDatoContacto() throws Exception {
        // Initialize the database
        datoContactoRepository.saveAndFlush(datoContacto);

        // Get the datoContacto
        restDatoContactoMockMvc.perform(get("/api/dato-contactos/{id}", datoContacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datoContacto.getId().intValue()))
            .andExpect(jsonPath("$.tipoDatoContacto").value(DEFAULT_TIPO_DATO_CONTACTO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatoContacto() throws Exception {
        // Get the datoContacto
        restDatoContactoMockMvc.perform(get("/api/dato-contactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatoContacto() throws Exception {
        // Initialize the database
        datoContactoRepository.saveAndFlush(datoContacto);
        int databaseSizeBeforeUpdate = datoContactoRepository.findAll().size();

        // Update the datoContacto
        DatoContacto updatedDatoContacto = datoContactoRepository.findOne(datoContacto.getId());
        updatedDatoContacto
            .tipoDatoContacto(UPDATED_TIPO_DATO_CONTACTO)
            .valor(UPDATED_VALOR);
        DatoContactoDTO datoContactoDTO = datoContactoMapper.toDto(updatedDatoContacto);

        restDatoContactoMockMvc.perform(put("/api/dato-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datoContactoDTO)))
            .andExpect(status().isOk());

        // Validate the DatoContacto in the database
        List<DatoContacto> datoContactoList = datoContactoRepository.findAll();
        assertThat(datoContactoList).hasSize(databaseSizeBeforeUpdate);
        DatoContacto testDatoContacto = datoContactoList.get(datoContactoList.size() - 1);
        assertThat(testDatoContacto.getTipoDatoContacto()).isEqualTo(UPDATED_TIPO_DATO_CONTACTO);
        assertThat(testDatoContacto.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDatoContacto() throws Exception {
        int databaseSizeBeforeUpdate = datoContactoRepository.findAll().size();

        // Create the DatoContacto
        DatoContactoDTO datoContactoDTO = datoContactoMapper.toDto(datoContacto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDatoContactoMockMvc.perform(put("/api/dato-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datoContactoDTO)))
            .andExpect(status().isCreated());

        // Validate the DatoContacto in the database
        List<DatoContacto> datoContactoList = datoContactoRepository.findAll();
        assertThat(datoContactoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDatoContacto() throws Exception {
        // Initialize the database
        datoContactoRepository.saveAndFlush(datoContacto);
        int databaseSizeBeforeDelete = datoContactoRepository.findAll().size();

        // Get the datoContacto
        restDatoContactoMockMvc.perform(delete("/api/dato-contactos/{id}", datoContacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DatoContacto> datoContactoList = datoContactoRepository.findAll();
        assertThat(datoContactoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatoContacto.class);
        DatoContacto datoContacto1 = new DatoContacto();
        datoContacto1.setId(1L);
        DatoContacto datoContacto2 = new DatoContacto();
        datoContacto2.setId(datoContacto1.getId());
        assertThat(datoContacto1).isEqualTo(datoContacto2);
        datoContacto2.setId(2L);
        assertThat(datoContacto1).isNotEqualTo(datoContacto2);
        datoContacto1.setId(null);
        assertThat(datoContacto1).isNotEqualTo(datoContacto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatoContactoDTO.class);
        DatoContactoDTO datoContactoDTO1 = new DatoContactoDTO();
        datoContactoDTO1.setId(1L);
        DatoContactoDTO datoContactoDTO2 = new DatoContactoDTO();
        assertThat(datoContactoDTO1).isNotEqualTo(datoContactoDTO2);
        datoContactoDTO2.setId(datoContactoDTO1.getId());
        assertThat(datoContactoDTO1).isEqualTo(datoContactoDTO2);
        datoContactoDTO2.setId(2L);
        assertThat(datoContactoDTO1).isNotEqualTo(datoContactoDTO2);
        datoContactoDTO1.setId(null);
        assertThat(datoContactoDTO1).isNotEqualTo(datoContactoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datoContactoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datoContactoMapper.fromId(null)).isNull();
    }
}
