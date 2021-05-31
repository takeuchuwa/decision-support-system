package ua.nure.decisionsupportsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.decisionsupportsystem.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
