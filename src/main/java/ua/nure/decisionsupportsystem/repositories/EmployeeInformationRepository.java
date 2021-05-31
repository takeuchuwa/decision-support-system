package ua.nure.decisionsupportsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.User;

import java.util.Optional;

public interface EmployeeInformationRepository extends JpaRepository<EmployeeInformation, Long> {

    Optional<EmployeeInformation> findByUser(User user);
}
