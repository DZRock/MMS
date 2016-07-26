package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.ControlOrganisation;

/**
 * Created by fedor on 26.06.2016.
 */
public interface ControlOrganisationRepo extends CrudRepository<ControlOrganisation,Long> {
    ControlOrganisation findByCode(String stringCellValue);
}
