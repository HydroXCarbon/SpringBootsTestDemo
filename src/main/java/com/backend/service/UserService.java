package com.backend.service;

import com.backend.exception.BaseException;
import com.backend.exception.UserException;
import com.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.entity.User;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,  PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public User update(User user){
        return repository.save(user);
    }

    public User updateName (String id, String name) throws BaseException {

        Optional<User> opt = repository.findById(id);

        if (opt.isEmpty()){
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User create(String email, String password, String name)throws BaseException {

        // validate
        if( Objects.isNull(email)){
            throw UserException.createEmailNull();
        }

        if( Objects.isNull(password)){
            throw UserException.createPasswordNull();
        }

        if( Objects.isNull(name)){
            throw UserException.createUsernameNull();
        }

        // verify
        if (repository.existsByEmail(email)){
            throw UserException.createEmailDuplicate();
        }

        if (repository.existsByName(name)){
            throw UserException.createUsernameDuplicate();
        }


        // save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        return repository.save(entity);
    }

}
