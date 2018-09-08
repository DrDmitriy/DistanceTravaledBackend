package backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.entity.User;

import backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * this class represent service layer for interaction with H2 database intended
 * to be a user-data storage
 * provides all necessary methods wrapping interaction with repositories
 * Besides CRUD methods, has custom methods like <code>updateUserPasswordByID</code> and
 * <code>updateUserNameByID</code>
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Long saveUser(String password, String login) {

        User user = new User(login, password);
        //UserTest user = new UserTest(login, password);
        repository.save(user);
        log.info("userService:: new user " + user.toString());
        return user.getUserID();
    }

  public void updateUserPasswordByID(Long id, String password) {
        Optional<User> optionalUser = findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        user.setHashPassword(password);
        repository.save(user);
        log.info("userService::  user updated " + user.toString());
    }

    public void updateUserNameByID(Long id, String firstName, String lastName) {
        Optional<User> optionalUser = findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        repository.save(user);
        log.info("userService::  user updated " + user.toString());
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public User getByLogin(String login) {
        return repository.getUserByLogin(login);
    }



}
