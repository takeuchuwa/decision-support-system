package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import ua.nure.decisionsupportsystem.entity.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperience extends BaseEntity {

    private String position;

    private String companyName;

    private String description;

    private boolean isNowWorking;

    private Date startWorkDate;

    private Date endWorkDate;

}
