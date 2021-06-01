package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import ua.nure.decisionsupportsystem.entity.base.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSkills extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private EmployeeInformation employeeInformation;

    @ManyToOne
    @JoinColumn
    private Skill skill;

    private Long skillLevel;
}
