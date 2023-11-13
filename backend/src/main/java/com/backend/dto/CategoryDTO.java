package com.backend.dto;

import com.backend.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(){
    }

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
