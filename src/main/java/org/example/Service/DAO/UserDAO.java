package org.example.Service.DAO;

import org.example.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    public User create(User user);
    public Optional<User> findById(Long id);
    public List<User> findAll();
    public User update(User user);
    public boolean delete(Long id);

}
