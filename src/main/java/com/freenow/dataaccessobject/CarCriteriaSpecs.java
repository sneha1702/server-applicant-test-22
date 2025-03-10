package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.ManufactureType;
import javax.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class CarCriteriaSpecs
{
    public static Specification<CarDO> withLicensePlate(String licensePlate)
    {
        return (root, query, cb) ->
            licensePlate == null ? null : cb.equal(root.get("licensePlate"), licensePlate);
    }


    public static Specification<CarDO> withRating(String rating)
    {
        return (root, query, cb) ->
            rating == null ? null : cb.equal(root.get("rating"), rating);
    }


    public static Specification<CarDO> withConvertible(Boolean convertible)
    {
        return (root, query, cb) ->
            convertible == null ? null : cb.equal(root.get("convertible"), convertible);
    }


    public static Specification<CarDO> withEngineType(EngineType engineType)
    {
        return (root, query, cb) ->
            engineType == null ? null : cb.equal(root.get("engineType"), engineType);
    }


    public static Specification<CarDO> withSelected(Boolean selected)
    {
        return (root, query, cb) -> selected == null ? null : cb.equal(root.get("selected"), selected);
    }


    public static Specification<CarDO> withManufactureType(ManufactureType manufactureType)
    {
        return (root, query, cb) ->
            manufactureType == null
                ? null
                : cb.equal(
                root.join("manufacturer", JoinType.LEFT).get("manufactureType"), manufactureType);
    }
}
