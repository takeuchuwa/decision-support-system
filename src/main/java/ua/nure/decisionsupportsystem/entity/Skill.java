package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import ua.nure.decisionsupportsystem.entity.common.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends BaseEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EmployeeSkills> employeeSkills;

}
