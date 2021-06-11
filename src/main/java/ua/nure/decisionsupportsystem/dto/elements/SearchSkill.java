package ua.nure.decisionsupportsystem.dto.elements;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.decisionsupportsystem.entity.Skill;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchSkill {

    private Skill skill;

    private Long level;
}
