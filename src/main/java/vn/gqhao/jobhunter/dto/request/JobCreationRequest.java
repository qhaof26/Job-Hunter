package vn.gqhao.jobhunter.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import vn.gqhao.jobhunter.domain.Company;
import vn.gqhao.jobhunter.domain.Skill;
import vn.gqhao.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCreationRequest {
    private String name;
    private String location;
    private double salary;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private LevelEnum levelEnum;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private boolean active;
    public Company company;
    private List<Skill> skills;
}
