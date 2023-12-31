package com.backend.resources;

import com.backend.dto.ProductDTO;
import com.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "LinesPerPage",defaultValue = "12")Integer LinesPerPage,
            @RequestParam(value = "direction",defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy",defaultValue = "name")String orderBy

            ){
        PageRequest pageRequest = PageRequest.of(page, LinesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ProductDTO> list = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    };

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    };

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    };


    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updata(@PathVariable Long id,@RequestBody ProductDTO dto){
        dto = service.updata(id,dto);
        return ResponseEntity.ok().body(dto);

    };

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();

    };

}
