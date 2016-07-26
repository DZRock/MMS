package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.File;

/**
 * Created by fedor on 10.06.2016.
 */
public interface FileRepo extends CrudRepository<File,Long> {
}
