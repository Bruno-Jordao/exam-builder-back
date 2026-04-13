package com.example.exambuilder.jwt;


import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));
        return new JwtUserDetails(teacher);
    }

    public JwtToken getTokenAuthenticated(String email){
        return JwtUtils.createToken(email);
    }
}
