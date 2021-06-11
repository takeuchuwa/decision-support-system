package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import ua.nure.decisionsupportsystem.entity.common.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInformation extends BaseEntity {

    @OneToOne
    @JoinColumn
    private User user;

    private Long workExperience;

    private Long salary;

    @OneToMany(mappedBy = "employeeInformation")
    private List<EmployeeSkills> employeeSkills;

}
