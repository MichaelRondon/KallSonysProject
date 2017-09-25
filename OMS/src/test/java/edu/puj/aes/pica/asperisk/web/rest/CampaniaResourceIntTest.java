package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.OmsApp;

import edu.puj.aes.pica.asperisk.domain.Campania;
import edu.puj.aes.pica.asperisk.repository.CampaniaRepository;
import edu.puj.aes.pica.asperisk.service.CampaniaService;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.CampaniaDTO;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.puj.aes.pica.asperisk.oms.utilities.enumeration.Estado;
import edu.puj.aes.pica.asperisk.service.mapper.CampaniaMapper;
/**
 * Test class for the CampaniaResource REST controller.
 *
 * @see CampaniaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsApp.class)
public class CampaniaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Autowired
    private CampaniaMapper campaniaMapper;

    @Autowired
    private CampaniaService campaniaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampaniaMockMvc;

    private Campania campania;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CampaniaResource campaniaResource = new CampaniaResource(campaniaService);
        this.restCampaniaMockMvc = MockMvcBuilders.standaloneSetup(campaniaResource)
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
    public static Campania createEntity(EntityManager em) {
        Campania campania = new Campania()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        return campania;
    }

    @Before
    public void initTest() {
        campania = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampania() throws Exception {
        int databaseSizeBeforeCreate = campaniaRepository.findAll().size();

        // Create the Campania
        CampaniaDTO campaniaDTO = campaniaMapper.toDto(campania);
        restCampaniaMockMvc.perform(post("/api/campanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaniaDTO)))
            .andExpect(status().isCreated());

        // Validate the Campania in the database
        List<Campania> campaniaList = campaniaRepository.findAll();
        assertThat(campaniaList).hasSize(databaseSizeBeforeCreate + 1);
        Campania testCampania = campaniaList.get(campaniaList.size() - 1);
        assertThat(testCampania.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCampania.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCampania.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCampania.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testCampania.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    public void createCampaniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaniaRepository.findAll().size();

        // Create the Campania with an existing ID
        campania.setId(1L);
        CampaniaDTO campaniaDTO = campaniaMapper.toDto(campania);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaniaMockMvc.perform(post("/api/campanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Campania> campaniaList = campaniaRepository.findAll();
        assertThat(campaniaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCampanias() throws Exception {
        // Initialize the database
        campaniaRepository.saveAndFlush(campania);

        // Get all the campaniaList
        restCampaniaMockMvc.perform(get("/api/campanias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campania.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }

    @Test
    @Transactional
    public void getCampania() throws Exception {
        // Initialize the database
        campaniaRepository.saveAndFlush(campania);

        // Get the campania
        restCampaniaMockMvc.perform(get("/api/campanias/{id}", campania.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campania.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampania() throws Exception {
        // Get the campania
        restCampaniaMockMvc.perform(get("/api/campanias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampania() throws Exception {
        // Initialize the database
        campaniaRepository.saveAndFlush(campania);
        int databaseSizeBeforeUpdate = campaniaRepository.findAll().size();

        // Update the campania
        Campania updatedCampania = campaniaRepository.findOne(campania.getId());
        updatedCampania
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        CampaniaDTO campaniaDTO = campaniaMapper.toDto(updatedCampania);

        restCampaniaMockMvc.perform(put("/api/campanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaniaDTO)))
            .andExpect(status().isOk());

        // Validate the Campania in the database
        List<Campania> campaniaList = campaniaRepository.findAll();
        assertThat(campaniaList).hasSize(databaseSizeBeforeUpdate);
        Campania testCampania = campaniaList.get(campaniaList.size() - 1);
        assertThat(testCampania.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCampania.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCampania.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCampania.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testCampania.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCampania() throws Exception {
        int databaseSizeBeforeUpdate = campaniaRepository.findAll().size();

        // Create the Campania
        CampaniaDTO campaniaDTO = campaniaMapper.toDto(campania);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCampaniaMockMvc.perform(put("/api/campanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaniaDTO)))
            .andExpect(status().isCreated());

        // Validate the Campania in the database
        List<Campania> campaniaList = campaniaRepository.findAll();
        assertThat(campaniaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCampania() throws Exception {
        // Initialize the database
        campaniaRepository.saveAndFlush(campania);
        int databaseSizeBeforeDelete = campaniaRepository.findAll().size();

        // Get the campania
        restCampaniaMockMvc.perform(delete("/api/campanias/{id}", campania.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Campania> campaniaList = campaniaRepository.findAll();
        assertThat(campaniaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campania.class);
        Campania campania1 = new Campania();
        campania1.setId(1L);
        Campania campania2 = new Campania();
        campania2.setId(campania1.getId());
        assertThat(campania1).isEqualTo(campania2);
        campania2.setId(2L);
        assertThat(campania1).isNotEqualTo(campania2);
        campania1.setId(null);
        assertThat(campania1).isNotEqualTo(campania2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaniaDTO.class);
        CampaniaDTO campaniaDTO1 = new CampaniaDTO();
        campaniaDTO1.setId(1L);
        CampaniaDTO campaniaDTO2 = new CampaniaDTO();
        assertThat(campaniaDTO1).isNotEqualTo(campaniaDTO2);
        campaniaDTO2.setId(campaniaDTO1.getId());
        assertThat(campaniaDTO1).isEqualTo(campaniaDTO2);
        campaniaDTO2.setId(2L);
        assertThat(campaniaDTO1).isNotEqualTo(campaniaDTO2);
        campaniaDTO1.setId(null);
        assertThat(campaniaDTO1).isNotEqualTo(campaniaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campaniaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campaniaMapper.fromId(null)).isNull();
    }
}
