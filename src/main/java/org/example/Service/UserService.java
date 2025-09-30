package org.example.Service;

import org.example.Entity.DTO.UserDTO;
import org.example.Entity.User;
import org.example.Service.DAO.UserDAO;
import org.example.Service.DAO.UserDAOImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();


    public User createUser(UserDTO dto) {
        User user = new User(
                dto.getName(),
                dto.getEmail(),
                dto.getAge(),
                LocalDateTime.now()
        );
        return userDAO.create(user);
    }

    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> updateUser(Long id, UserDTO dto) {
        Optional<User> opt = userDAO.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        User user = opt.get();
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAge() != null) user.setAge(dto.getAge());

        return Optional.of(userDAO.update(user));
    }

    public boolean deleteUser(Long id) {
        return userDAO.delete(id);
    }

}