package ru.kubsu.mms.core.services;

import ru.kubsu.mms.core.db.models.User;

import java.util.List;

/**
 * Created by DZRock on 25.03.2016.
 */
public interface UserService {

    User getUserById(Long id);
    User getUserByLogin(String login);
    List<User> getUserListByCriteria(String loginMask, String emailMask, String nameMask, Long groupId);
    List<User> getUserList();
    void saveUser(User user);
    void updateUser(User user);
    void blockUser(Long userId);
    void changePasswordByEmail(String email);
    void registrationUser(User user);

    void registerUser(User map);
}
