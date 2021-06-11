package ua.nure.decisionsupportsystem.service;

import org.springframework.ui.ModelMap;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.dto.SearchDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface ProfileService {

    void prepareEmployeeProfile(ModelMap map, Principal principal);

    void prepareHrProfile(ModelMap map);

    void saveInformation(EmployeeInformation employeeInformation, Principal principal);

    void addSkill(EmployeeInformation employeeInformation, EmployeeSkills employeeSkills, Principal principal);

    void deleteSkill(Long id);

    void addRow(SearchDto searchDto, ModelMap map);

    void removeRow(SearchDto searchDto, HttpServletRequest req, ModelMap map);

    List<User> searchUsers(SearchDto searchDto);
}
