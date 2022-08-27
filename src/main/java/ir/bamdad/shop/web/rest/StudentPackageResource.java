package ir.bamdad.shop.web.rest;

import ir.bamdad.shop.domain.StudentPackage;
import ir.bamdad.shop.repository.StudentPackageRepository;
import ir.bamdad.shop.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ir.bamdad.shop.domain.StudentPackage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StudentPackageResource {

    private final Logger log = LoggerFactory.getLogger(StudentPackageResource.class);

    private static final String ENTITY_NAME = "studentPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentPackageRepository studentPackageRepository;

    public StudentPackageResource(StudentPackageRepository studentPackageRepository) {
        this.studentPackageRepository = studentPackageRepository;
    }

    /**
     * {@code POST  /student-packages} : Create a new studentPackage.
     *
     * @param studentPackage the studentPackage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentPackage, or with status {@code 400 (Bad Request)} if the studentPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-packages")
    public ResponseEntity<StudentPackage> createStudentPackage(@RequestBody StudentPackage studentPackage) throws URISyntaxException {
        log.debug("REST request to save StudentPackage : {}", studentPackage);
        if (studentPackage.getId() != null) {
            throw new BadRequestAlertException("A new studentPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentPackage result = studentPackageRepository.save(studentPackage);
        return ResponseEntity
            .created(new URI("/api/student-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-packages/:id} : Updates an existing studentPackage.
     *
     * @param id the id of the studentPackage to save.
     * @param studentPackage the studentPackage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentPackage,
     * or with status {@code 400 (Bad Request)} if the studentPackage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentPackage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-packages/{id}")
    public ResponseEntity<StudentPackage> updateStudentPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StudentPackage studentPackage
    ) throws URISyntaxException {
        log.debug("REST request to update StudentPackage : {}, {}", id, studentPackage);
        if (studentPackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentPackage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StudentPackage result = studentPackageRepository.save(studentPackage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentPackage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /student-packages/:id} : Partial updates given fields of an existing studentPackage, field will ignore if it is null
     *
     * @param id the id of the studentPackage to save.
     * @param studentPackage the studentPackage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentPackage,
     * or with status {@code 400 (Bad Request)} if the studentPackage is not valid,
     * or with status {@code 404 (Not Found)} if the studentPackage is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentPackage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/student-packages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudentPackage> partialUpdateStudentPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StudentPackage studentPackage
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudentPackage partially : {}, {}", id, studentPackage);
        if (studentPackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentPackage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudentPackage> result = studentPackageRepository
            .findById(studentPackage.getId())
            .map(existingStudentPackage -> {
                if (studentPackage.getPackageName() != null) {
                    existingStudentPackage.setPackageName(studentPackage.getPackageName());
                }
                if (studentPackage.getGrade() != null) {
                    existingStudentPackage.setGrade(studentPackage.getGrade());
                }

                return existingStudentPackage;
            })
            .map(studentPackageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentPackage.getId().toString())
        );
    }

    /**
     * {@code GET  /student-packages} : get all the studentPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentPackages in body.
     */
    @GetMapping("/student-packages")
    public List<StudentPackage> getAllStudentPackages() {
        log.debug("REST request to get all StudentPackages");
        return studentPackageRepository.findAll();
    }

    /**
     * {@code GET  /student-packages/:id} : get the "id" studentPackage.
     *
     * @param id the id of the studentPackage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentPackage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-packages/{id}")
    public ResponseEntity<StudentPackage> getStudentPackage(@PathVariable Long id) {
        log.debug("REST request to get StudentPackage : {}", id);
        Optional<StudentPackage> studentPackage = studentPackageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentPackage);
    }

    /**
     * {@code DELETE  /student-packages/:id} : delete the "id" studentPackage.
     *
     * @param id the id of the studentPackage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-packages/{id}")
    public ResponseEntity<Void> deleteStudentPackage(@PathVariable Long id) {
        log.debug("REST request to delete StudentPackage : {}", id);
        studentPackageRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
