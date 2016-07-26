package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.TechControl;
import ru.kubsu.mms.core.db.models.User;

import java.util.List;

/**
 * Created by DZRock on 15.05.2016.
 */
public interface TechControlRepo extends CrudRepository<TechControl,Long> {

    List<TechControl> findByResponsibleUser(User currentUser);

    List<TechControl> findByOrganisation_Id(Long id);
}
