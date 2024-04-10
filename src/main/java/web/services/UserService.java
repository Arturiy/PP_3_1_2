package web.services;

import web.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    void save(User user);

    void update(int id, User updatedUser);

    void delete(int id);

    List<User> getUserByFullName(String name, String surname);
}
