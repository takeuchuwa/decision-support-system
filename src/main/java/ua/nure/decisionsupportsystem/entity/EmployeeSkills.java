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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private EmployeeInformation employeeInformation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Skill skill;

    private Long skillLevel;
}
