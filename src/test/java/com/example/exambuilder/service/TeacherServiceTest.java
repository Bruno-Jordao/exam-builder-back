package com.example.exambuilder.service;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.exceptions.EmailAlreadyExistsException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.TeacherCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private TeacherService service;

    @Test
    void shouldSaveTeacherWithEncryptedPassword() {

        TeacherCreateDto dto = new TeacherCreateDto("Bruno", "bruno@email.com", "123456");

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encrypted");
        when(repository.save(any(Teacher.class))).thenAnswer(i -> i.getArgument(0));

        Teacher saved = service.save(dto);

        assertEquals("encrypted", saved.getPassword());
        verify(repository).save(any(Teacher.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {

        TeacherCreateDto dto = new TeacherCreateDto("Bruno", "bruno@email.com", "123456");

        when(repository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new Teacher()));

        assertThrows(RuntimeException.class, () -> service.save(dto));
    }

    @Test
    void shouldReturnTeacherWhenSearchByEmailExists() {
        Teacher teacher = new Teacher();
        teacher.setEmail("bruno@email.com");

        when(repository.findByEmail("bruno@email.com"))
                .thenReturn(Optional.of(teacher));

        Teacher result = service.searchByEmail("bruno@email.com");

        assertEquals("bruno@email.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenSearchByEmailNotFound() {
        when(repository.findByEmail("notfound@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> service.searchByEmail("notfound@email.com"));
    }

    @Test
    void shouldReturnTeacherWhenFindByIdExists() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(teacher));

        Teacher result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdNotFound() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> service.findById(99L));
    }

    @Test
    void shouldReturnAllTeachers() {
        when(repository.findAll())
                .thenReturn(java.util.List.of(new Teacher(), new Teacher()));

        var list = service.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void shouldUpdateTeacherWhenEmailIsSame() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setEmail("same@email.com");

        TeacherCreateDto dto =
                new TeacherCreateDto("Novo Nome", "same@email.com", "123");

        when(repository.findById(1L)).thenReturn(Optional.of(teacher));
        when(encoder.encode(dto.getPassword())).thenReturn("encrypted");
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Teacher updated = service.update(1L, dto);

        assertEquals("Novo Nome", updated.getName());
        assertEquals("encrypted", updated.getPassword());
    }

    @Test
    void shouldUpdateTeacherWhenEmailChangesAndIsUnique() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setEmail("old@email.com");

        TeacherCreateDto dto =
                new TeacherCreateDto("Nome", "new@email.com", "123");

        when(repository.findById(1L)).thenReturn(Optional.of(teacher));
        when(repository.findByEmail("new@email.com"))
                .thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encrypted");
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Teacher updated = service.update(1L, dto);

        assertEquals("new@email.com", updated.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUpdateEmailAlreadyExists() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setEmail("old@email.com");

        TeacherCreateDto dto =
                new TeacherCreateDto("Nome", "exist@email.com", "123");

        when(repository.findById(1L)).thenReturn(Optional.of(teacher));
        when(repository.findByEmail("exist@email.com"))
                .thenReturn(Optional.of(new Teacher()));

        assertThrows(EmailAlreadyExistsException.class,
                () -> service.update(1L, dto));
    }

    @Test
    void shouldDeleteTeacherWhenExists() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(teacher));

        service.delete(1L);

        verify(repository).delete(teacher);
    }
}
