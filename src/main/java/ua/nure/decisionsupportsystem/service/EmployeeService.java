package ua.nure.decisionsupportsystem.service;

import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;

import java.security.Principal;

public interface EmployeeService {

    void addSkill(EmployeeInformation employeeInformation, EmployeeSkills employeeSkills, Principal principal);

    void saveInformation(EmployeeInformation employeeInformation, Principal principal);
}
