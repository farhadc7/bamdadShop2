package ir.bamdad.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ir.bamdad.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentPackageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentPackage.class);
        StudentPackage studentPackage1 = new StudentPackage();
        studentPackage1.setId(1L);
        StudentPackage studentPackage2 = new StudentPackage();
        studentPackage2.setId(studentPackage1.getId());
        assertThat(studentPackage1).isEqualTo(studentPackage2);
        studentPackage2.setId(2L);
        assertThat(studentPackage1).isNotEqualTo(studentPackage2);
        studentPackage1.setId(null);
        assertThat(studentPackage1).isNotEqualTo(studentPackage2);
    }
}
