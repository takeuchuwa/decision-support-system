package ua.nure.decisionsupportsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.Skill;

import java.util.List;
import java.util.Optional;

public interface EmployeeSkillsRepository extends JpaRepository<EmployeeSkills, Long> {

    List<EmployeeSkills> findAllByEmployeeInformation(EmployeeInformation employeeInformation);

    Optional<EmployeeSkills> findBySkill(Skill skill);
}
