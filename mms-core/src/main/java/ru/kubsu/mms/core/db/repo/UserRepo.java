package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.User;

import java.util.List;

/**
 * Created by DZRock on 25.03.2016.
 */
public interface UserRepo extends CrudRepository<User,Long> {

    User findOneByEmail(String login);
    User findOneById(Long id);

    User getByEmailAndPassword(String email, String passwordHash);

    User findByEmail(String email);
}
