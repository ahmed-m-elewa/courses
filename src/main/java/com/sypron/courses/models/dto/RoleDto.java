package com.sypron.courses.models.dto;

import com.sypron.courses.models.entities.Action;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@ToString
public class RoleDto {
    private Long id;
    private String name;
    private List<ActionDto> actions;

}