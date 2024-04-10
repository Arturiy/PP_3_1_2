package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;
import web.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserServiceImplementationDataJpa implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImplementationDataJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByFullName(String name, String surname) {
        return userRepository.getUsersByNameAndSurname(name, surname);
    }

    public User findById(int id) {
        Optional<User> foundPerson = userRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(User person) {
        userRepository.save(person);
    }

    @Transactional
    public void update(int id, User updatedPerson) {
        updatedPerson.setId(id);
        userRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }


}
