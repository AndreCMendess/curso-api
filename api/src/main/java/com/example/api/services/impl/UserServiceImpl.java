package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.UserService;
import com.example.api.services.impl.exception.DataIntegratyViolationException;
import com.example.api.services.impl.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj,User.class));
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj,User.class));
    }

    public void findByEmail(UserDTO obj){
        Optional<User> user = userRepository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("Email ja esta cadastrado");
        }
    }
}
