package ua.nure.decisionsupportsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.decisionsupportsystem.entity.dto.elements.SearchSkill;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    private Long workExperience;

    private Long salary;

    private List<SearchSkill> skills;

}
