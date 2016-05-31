package ru.kubsu.mms.core.db.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by DZRock on 25.03.2016.
 */
@Entity
@Table(name = "roles")
public class Role extends BasicEntity implements Serializable {
}
