package com.freenow.dataaccessobject;

import com.freenow.domainobject.ManufacturerDO;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{
}
