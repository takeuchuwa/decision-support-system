package ua.nure.decisionsupportsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.nure.decisionsupportsystem.entity.Skill;
import ua.nure.decisionsupportsystem.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("select distinct u from User u " +
            "inner join u.employeeInformation ei " +
            "inner join ei.employeeSkills es " +
            "where ei.salary <= :salary " +
            "and ei.workExperience >= :workExperience")
    List<User> findAllByEmployeeSalaryAndWorkExperience(Long salary, Long workExperience);

}
