package ir.bamdad.shop.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ir.bamdad.shop.IntegrationTest;
import ir.bamdad.shop.domain.StudentPackage;
import ir.bamdad.shop.domain.enumeration.Grade;
import ir.bamdad.shop.repository.StudentPackageRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StudentPackageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentPackageResourceIT {

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE = Grade.FIRST;
    private static final Grade UPDATED_GRADE = Grade.SECOND;

    private static final String ENTITY_API_URL = "/api/student-packages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentPackageRepository studentPackageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentPackageMockMvc;

    private StudentPackage studentPackage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentPackage createEntity(EntityManager em) {
        StudentPackage studentPackage = new StudentPackage().packageName(DEFAULT_PACKAGE_NAME).grade(DEFAULT_GRADE);
        return studentPackage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentPackage createUpdatedEntity(EntityManager em) {
        StudentPackage studentPackage = new StudentPackage().packageName(UPDATED_PACKAGE_NAME).grade(UPDATED_GRADE);
        return studentPackage;
    }

    @BeforeEach
    public void initTest() {
        studentPackage = createEntity(em);
    }

    @Test
    @Transactional
    void createStudentPackage() throws Exception {
        int databaseSizeBeforeCreate = studentPackageRepository.findAll().size();
        // Create the StudentPackage
        restStudentPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isCreated());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeCreate + 1);
        StudentPackage testStudentPackage = studentPackageList.get(studentPackageList.size() - 1);
        assertThat(testStudentPackage.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testStudentPackage.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    void createStudentPackageWithExistingId() throws Exception {
        // Create the StudentPackage with an existing ID
        studentPackage.setId(1L);

        int databaseSizeBeforeCreate = studentPackageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudentPackages() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        // Get all the studentPackageList
        restStudentPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].packageName").value(hasItem(DEFAULT_PACKAGE_NAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())));
    }

    @Test
    @Transactional
    void getStudentPackage() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        // Get the studentPackage
        restStudentPackageMockMvc
            .perform(get(ENTITY_API_URL_ID, studentPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentPackage.getId().intValue()))
            .andExpect(jsonPath("$.packageName").value(DEFAULT_PACKAGE_NAME))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStudentPackage() throws Exception {
        // Get the studentPackage
        restStudentPackageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudentPackage() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();

        // Update the studentPackage
        StudentPackage updatedStudentPackage = studentPackageRepository.findById(studentPackage.getId()).get();
        // Disconnect from session so that the updates on updatedStudentPackage are not directly saved in db
        em.detach(updatedStudentPackage);
        updatedStudentPackage.packageName(UPDATED_PACKAGE_NAME).grade(UPDATED_GRADE);

        restStudentPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudentPackage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStudentPackage))
            )
            .andExpect(status().isOk());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
        StudentPackage testStudentPackage = studentPackageList.get(studentPackageList.size() - 1);
        assertThat(testStudentPackage.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testStudentPackage.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    void putNonExistingStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentPackage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPackage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentPackageWithPatch() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();

        // Update the studentPackage using partial update
        StudentPackage partialUpdatedStudentPackage = new StudentPackage();
        partialUpdatedStudentPackage.setId(studentPackage.getId());

        partialUpdatedStudentPackage.packageName(UPDATED_PACKAGE_NAME);

        restStudentPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentPackage))
            )
            .andExpect(status().isOk());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
        StudentPackage testStudentPackage = studentPackageList.get(studentPackageList.size() - 1);
        assertThat(testStudentPackage.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testStudentPackage.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    void fullUpdateStudentPackageWithPatch() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();

        // Update the studentPackage using partial update
        StudentPackage partialUpdatedStudentPackage = new StudentPackage();
        partialUpdatedStudentPackage.setId(studentPackage.getId());

        partialUpdatedStudentPackage.packageName(UPDATED_PACKAGE_NAME).grade(UPDATED_GRADE);

        restStudentPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentPackage))
            )
            .andExpect(status().isOk());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
        StudentPackage testStudentPackage = studentPackageList.get(studentPackageList.size() - 1);
        assertThat(testStudentPackage.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testStudentPackage.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    void patchNonExistingStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentPackage() throws Exception {
        int databaseSizeBeforeUpdate = studentPackageRepository.findAll().size();
        studentPackage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPackageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(studentPackage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentPackage in the database
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentPackage() throws Exception {
        // Initialize the database
        studentPackageRepository.saveAndFlush(studentPackage);

        int databaseSizeBeforeDelete = studentPackageRepository.findAll().size();

        // Delete the studentPackage
        restStudentPackageMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentPackage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentPackage> studentPackageList = studentPackageRepository.findAll();
        assertThat(studentPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
