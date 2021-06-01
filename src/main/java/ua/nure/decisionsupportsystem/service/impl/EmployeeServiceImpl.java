package ua.nure.decisionsupportsystem.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.repositories.EmployeeInformationRepository;
import ua.nure.decisionsupportsystem.repositories.EmployeeSkillsRepository;
import ua.nure.decisionsupportsystem.repositories.UserRepository;
import ua.nure.decisionsupportsystem.service.EmployeeService;

import java.security.Principal;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Setter(onMethod_ = @Autowired)
    private EmployeeInformationRepository employeeInformationRepository;

    @Setter(onMethod_ = @Autowired)
    private EmployeeSkillsRepository employeeSkillsRepository;

    @Override
    @Transactional
    public void addSkill(EmployeeInformation employeeInformation, EmployeeSkills employeeSkills, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        user.ifPresent(u -> {
            Optional<EmployeeInformation> empInfo = employeeInformationRepository.findByUser(u);
            if (empInfo.isPresent()) {
                empInfo.get().setWorkExperience(employeeInformation.getWorkExperience());
                employeeInformationRepository.save(empInfo.get());
                saveOrUpdateSkillByInformation(employeeSkills, empInfo.get());
            } else {
                employeeInformation.setUser(u);
                employeeInformationRepository.save(employeeInformation);
                saveOrUpdateSkillByInformation(employeeSkills, employeeInformation);
            }
        });
    }

    public void saveOrUpdateSkillByInformation(EmployeeSkills employeeSkills, EmployeeInformation employeeInformation) {
        Optional<EmployeeSkills> empSkills = employeeSkillsRepository.findBySkill(employeeSkills.getSkill());
        if (employeeSkills.getSkillLevel() != null) {
            if (empSkills.isPresent()) {
                empSkills.get().setSkillLevel(employeeSkills.getSkillLevel());
                employeeSkillsRepository.save(empSkills.get());
            } else {
                employeeSkills.setEmployeeInformation(employeeInformation);
                employeeSkillsRepository.save(employeeSkills);
            }
        }
    }
}
