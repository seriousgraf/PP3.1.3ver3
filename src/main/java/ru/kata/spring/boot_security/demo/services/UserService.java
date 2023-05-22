package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public List<Role> findAllRoles();

    public User getUser(int id);

    public void saveUser(User user);

    public void updateUser(User updatedUser);

    public void deleteUser(int id);

    public boolean existsUserByEmail(String email);

    public User findUserById(Integer id);
}
