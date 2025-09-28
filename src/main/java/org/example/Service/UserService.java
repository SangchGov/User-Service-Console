package org.example.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entity.User;
import org.example.Service.DAO.UserDAO;
import org.example.Service.DAO.UserDAOImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService{

    private final UserDAO userDAO = new UserDAOImpl();
    private static final Logger logger = LogManager.getLogger(UserService.class);


    public User createUser(String name, String email, int age, LocalDateTime createdAt){
        return userDAO.create(new User(name, email, age, createdAt));
    }

    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public User updateUser(Long id,String name, String email, int age){
        Optional<User> opt = userDAO.findById(id);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found");
        User user = opt.get();
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (age != 0) user.setAge(age);
        return userDAO.update(user);
    }

    public boolean deleteUser(Long id) {
        return userDAO.delete(id);
    }

}