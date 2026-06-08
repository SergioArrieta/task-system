package com.zerst.taskSystem.serviceUsers.services;

import com.zerst.taskSystem.serviceUsers.controller.CreateUserRequest;
import com.zerst.taskSystem.serviceUsers.entities.User;
import com.zerst.taskSystem.serviceUsers.exceptions.ExternalServiceException;
import com.zerst.taskSystem.serviceUsers.exceptions.UserNotFoundException;
import com.zerst.taskSystem.serviceUsers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServices {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    @Value("${task.service.url}")
    private String taskServiceUrl;

    public UserServices(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;

    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(User::toDTO)
                .orElseThrow(() -> new UserNotFoundException(String.format("User id:%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::toDTO).collect(Collectors.toList());
    }


    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        User newUser = new User();
        newUser.setName(request.name());
        newUser.setSurname(request.surname());
        newUser.setEmail(request.email());

        userRepository.save(newUser);

        return new UserDTO(newUser.getId(), newUser.getName(), newUser.getSurname(), newUser.getEmail());
    }

    @Transactional
    public UserDTO updateUser(Long id, CreateUserRequest request) {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User id:%s was not found", id)));

        oldUser.setName(request.name());
        oldUser.setSurname(request.surname());
        oldUser.setEmail(request.email());

        return new UserDTO(oldUser.getId(), oldUser.getName(), oldUser.getSurname(), oldUser.getEmail());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("User id:%s was not found", id));
        }
        try {
            restTemplate.delete(taskServiceUrl + "/user/" + id);
        } catch (Exception e) {
            log.error("Tasks from user could not be deleted {}", e.getMessage());
            throw new ExternalServiceException("Tasks from user could not be deleted " + e.getMessage());
        }
        userRepository.deleteById(id);
    }
}
