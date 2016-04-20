package ru.kubsu.mms.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kubsu.mms.core.db.models.User;
import ru.kubsu.mms.core.db.repo.UserRepo;

import java.util.List;

/**
 * Created by DZRock on 25.03.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;


    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public List<User> getUserListByCriteria(String loginMask, String emailMask, String nameMask, Long groupId) {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void blockUser(Long userId) {

    }

    @Override
    public void changePasswordByEmail(String email) {

    }

    @Override
    public void registrationUser(User user) {

    }
}
