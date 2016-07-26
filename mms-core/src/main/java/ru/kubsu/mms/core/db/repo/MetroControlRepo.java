package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.MetroControl;
import ru.kubsu.mms.core.db.models.TechControl;
import ru.kubsu.mms.core.db.models.User;

import java.util.List;

/**
 * Created by DZRock on 15.05.2016.
 */
public interface MetroControlRepo extends CrudRepository<MetroControl,Long> {

    List<MetroControl> findByResponsibleUser(User currentUser);

    List<MetroControl> findByOrganisation_Id(Long id);
}
