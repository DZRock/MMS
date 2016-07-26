package ru.kubsu.mms.core.db.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.kubsu.mms.core.db.models.Equipment;

/**
 * Created by DZRock on 15.05.2016.
 */
public interface EquipmentRepo extends CrudRepository<Equipment,Long> {
    @Query("select e from Equipment e where e.inventoryNumber = :i")
    Equipment findByInventoryNumber(@Param(value = "i") String stringCellValue);
}
