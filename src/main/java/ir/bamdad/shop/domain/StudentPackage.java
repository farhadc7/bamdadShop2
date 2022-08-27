package ir.bamdad.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.bamdad.shop.domain.enumeration.Grade;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A StudentPackage.
 */
@Entity
@Table(name = "student_package")
public class StudentPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @ManyToMany(mappedBy = "studentPackages")
    @JsonIgnoreProperties(value = { "studentPackages" }, allowSetters = true)
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StudentPackage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public StudentPackage packageName(String packageName) {
        this.setPackageName(packageName);
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public StudentPackage grade(Grade grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        if (this.students != null) {
            this.students.forEach(i -> i.removeStudentPackage(this));
        }
        if (students != null) {
            students.forEach(i -> i.addStudentPackage(this));
        }
        this.students = students;
    }

    public StudentPackage students(Set<Student> students) {
        this.setStudents(students);
        return this;
    }

    public StudentPackage addStudent(Student student) {
        this.students.add(student);
        student.getStudentPackages().add(this);
        return this;
    }

    public StudentPackage removeStudent(Student student) {
        this.students.remove(student);
        student.getStudentPackages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentPackage)) {
            return false;
        }
        return id != null && id.equals(((StudentPackage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentPackage{" +
            "id=" + getId() +
            ", packageName='" + getPackageName() + "'" +
            ", grade='" + getGrade() + "'" +
            "}";
    }
}
