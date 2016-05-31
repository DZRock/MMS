package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.Location;

/**
 * Created by DZRock on 15.05.2016.
 */
public interface LocationRepo extends CrudRepository<Location,Long> {
}
