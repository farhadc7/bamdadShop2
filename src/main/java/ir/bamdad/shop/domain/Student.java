package ir.bamdad.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.bamdad.shop.domain.enumeration.Grade;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "national_code")
    private String nationalCode;
    @NotNull
    @Column(name = "mobile_number")
    private String mobileNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;
    @NotNull
    @ManyToMany
    @JoinTable(
        name = "rel_student__student_package",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "student_package_id")
    )
    @JsonIgnoreProperties(value = { "students" }, allowSetters = true)
    private Set<StudentPackage> studentPackages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Student firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Student lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return this.nationalCode;
    }

    public Student nationalCode(String nationalCode) {
        this.setNationalCode(nationalCode);
        return this;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Student mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public Student grade(Grade grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Set<StudentPackage> getStudentPackages() {
        return this.studentPackages;
    }

    public void setStudentPackages(Set<StudentPackage> studentPackages) {
        this.studentPackages = studentPackages;
    }

    public Student studentPackages(Set<StudentPackage> studentPackages) {
        this.setStudentPackages(studentPackages);
        return this;
    }

    public Student addStudentPackage(StudentPackage studentPackage) {
        this.studentPackages.add(studentPackage);
        studentPackage.getStudents().add(this);
        return this;
    }

    public Student removeStudentPackage(StudentPackage studentPackage) {
        this.studentPackages.remove(studentPackage);
        studentPackage.getStudents().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", nationalCode='" + getNationalCode() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", grade='" + getGrade() + "'" +
            "}";
    }
}
