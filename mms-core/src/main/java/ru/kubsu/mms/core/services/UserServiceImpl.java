package ru.kubsu.mms.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kubsu.mms.core.db.models.Role;
import ru.kubsu.mms.core.db.models.User;
import ru.kubsu.mms.core.db.repo.UserRepo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
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
    @Transactional
    public void registerUser(User user) throws Exception {
        User pUser = userRepo.findByEmail(user.getEmail());
        if(pUser!=null)throw new Exception("Пользователь с таким Email уже существует");

        user.setCreated(new Date());
        user.setActive(Boolean.TRUE);

        Role role = new Role();
        role.setName("ROLE_USER");

        user.getRoles().add(role);

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(user.getPassword().getBytes("utf-8"));
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error calculating passwd hash");
        }
        String passwordHash = new BigInteger(1, md5.digest()).toString(16);

        user.setPassword(passwordHash);

        userRepo.save(user);
    }
}
