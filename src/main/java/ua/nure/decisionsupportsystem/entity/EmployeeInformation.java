package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import ua.nure.decisionsupportsystem.entity.base.BaseEntity;

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

    @OneToMany(mappedBy = "employeeInformation")
    private List<EmployeeSkills> employeeSkills;

}
