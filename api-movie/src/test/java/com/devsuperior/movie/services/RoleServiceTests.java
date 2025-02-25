package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.RoleDTO;
import com.devsuperior.movie.entities.Role;
import com.devsuperior.movie.repositories.RoleRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.services.impl.RoleServiceImpl;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class RoleServiceTests {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllRolePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPaged(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllRoleFilteredByAuthority(){

        String authority = "Member";

        List<RoleDTO> list = service.queryMethod(authority);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenAuthorityNonExisting(){

        String authority = "Boss";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<RoleDTO> list = service.queryMethod(authority);
            throw new ResourceNotFoundException("Authority not found: " + authority);
        });
    }

    @Test
    public void findByIdShouldReturnRoleByIdWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Role> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertNotNull(optional);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        RoleDTO dto = Factory.createdRoleDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = Factory.createdRoleDtoToUpdateIsNotFound();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdIsAssociated(){

        Optional<Role> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(id);
            throw new DataIntegrityViolationException("Data Integrity Violation Exception");
        });
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
