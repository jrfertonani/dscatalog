package com.backend.resources;

import com.backend.dto.CategoryDTO;
import com.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "LinesPerPage",defaultValue = "12")Integer LinesPerPage,
            @RequestParam(value = "direction",defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy",defaultValue = "name")String orderBy

            ){
        PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<CategoryDTO> list = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    };

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    };

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    };


    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> updata(@PathVariable Long id,@RequestBody CategoryDTO dto){
        dto = service.updata(id,dto);
        return ResponseEntity.ok().body(dto);

    };

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();

    };

}
