package ru.kubsu.mms.core.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.kubsu.mms.core.db.models.TechCheckout;

/**
 * Created by fedor on 06.06.2016.
 */
public interface TechCheckoutRepo extends CrudRepository<TechCheckout,Long> {
}
