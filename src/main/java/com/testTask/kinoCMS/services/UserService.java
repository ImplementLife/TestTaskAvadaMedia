package com.testTask.kinoCMS.services;

import com.testTask.kinoCMS.entity.user.Role;
import com.testTask.kinoCMS.entity.user.User;
import com.testTask.kinoCMS.repositoryes.UserDao;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import com.testTask.kinoCMS.services.defaultRestData.DefaultRestService;
import com.testTask.kinoCMS.services.defaultRestData.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@Component
public class UserService implements UserDetailsService, DefaultRestService<User> {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public User create(User user, Role role) throws AlreadyExistException {
        User userFromDB = userDao.findByUsername(user.getUsername());

        if (userFromDB != null) throw new AlreadyExistException();

        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userFromDB = userDao.saveAndFlush(user);
        return userFromDB;
    }

    @Override
    public User create(@Valid User user) throws AlreadyExistException {
        return create(user, new Role(1L, "ROLE_USER"));
    }

    @Override
    public boolean update(User user) {
        if (userDao.existsById(user.getId())) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        if (userDao.existsById(id)) return userDao.getOne(id);
        else throw new NotFoundException();
    }

    @Override
    public Collection<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
            return true;
        }
        return false;
    }
}
