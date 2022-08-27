package ir.bamdad.shop.repository;

import ir.bamdad.shop.domain.StudentPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StudentPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentPackageRepository extends JpaRepository<StudentPackage, Long> {}
