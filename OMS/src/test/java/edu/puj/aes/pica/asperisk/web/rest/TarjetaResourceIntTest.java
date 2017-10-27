package edu.puj.aes.pica.asperisk.web.rest;

import edu.puj.aes.pica.asperisk.OmsApp;

import edu.puj.aes.pica.asperisk.domain.Tarjeta;
import edu.puj.aes.pica.asperisk.repository.TarjetaRepository;
import edu.puj.aes.pica.asperisk.service.dto.TarjetaDTO;
import edu.puj.aes.pica.asperisk.service.mapper.TarjetaMapper;
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
 * Test class for the TarjetaResource REST controller.
 *
 * @see TarjetaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsApp.class)
public class TarjetaResourceIntTest {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TarjetaMapper tarjetaMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTarjetaMockMvc;

    private Tarjeta tarjeta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TarjetaResource tarjetaResource = new TarjetaResource(tarjetaRepository, tarjetaMapper);
        this.restTarjetaMockMvc = MockMvcBuilders.standaloneSetup(tarjetaResource)
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
    public static Tarjeta createEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta()
            .tipo(DEFAULT_TIPO)
            .numero(DEFAULT_NUMERO);
        return tarjeta;
    }

    @Before
    public void initTest() {
        tarjeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarjeta() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTarjeta.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createTarjetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta with an existing ID
        tarjeta.setId(1L);
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setTipo(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setNumero(null);

        // Create the Tarjeta, which fails.
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarjetas() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get all the tarjetaList
        restTarjetaMockMvc.perform(get("/api/tarjetas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarjeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }

    @Test
    @Transactional
    public void getTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", tarjeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarjeta.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTarjeta() throws Exception {
        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta
        Tarjeta updatedTarjeta = tarjetaRepository.findOne(tarjeta.getId());
        updatedTarjeta
            .tipo(UPDATED_TIPO)
            .numero(UPDATED_NUMERO);
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(updatedTarjeta);

        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTarjeta.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Create the Tarjeta
        TarjetaDTO tarjetaDTO = tarjetaMapper.toDto(tarjeta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjetaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);
        int databaseSizeBeforeDelete = tarjetaRepository.findAll().size();

        // Get the tarjeta
        restTarjetaMockMvc.perform(delete("/api/tarjetas/{id}", tarjeta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarjeta.class);
        Tarjeta tarjeta1 = new Tarjeta();
        tarjeta1.setId(1L);
        Tarjeta tarjeta2 = new Tarjeta();
        tarjeta2.setId(tarjeta1.getId());
        assertThat(tarjeta1).isEqualTo(tarjeta2);
        tarjeta2.setId(2L);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
        tarjeta1.setId(null);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarjetaDTO.class);
        TarjetaDTO tarjetaDTO1 = new TarjetaDTO();
        tarjetaDTO1.setId(1L);
        TarjetaDTO tarjetaDTO2 = new TarjetaDTO();
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
        tarjetaDTO2.setId(tarjetaDTO1.getId());
        assertThat(tarjetaDTO1).isEqualTo(tarjetaDTO2);
        tarjetaDTO2.setId(2L);
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
        tarjetaDTO1.setId(null);
        assertThat(tarjetaDTO1).isNotEqualTo(tarjetaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tarjetaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tarjetaMapper.fromId(null)).isNull();
    }
}
