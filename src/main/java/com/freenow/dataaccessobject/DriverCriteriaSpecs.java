package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.OnlineStatus;
import javax.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class DriverCriteriaSpecs
{
    public static Specification<DriverDO> withUsername(String username)
    {
        return (root, query, cb) ->
            username == null ? null : cb.equal(root.get("username"), username);
    }


    public static Specification<DriverDO> withOnlineStatus(OnlineStatus onlineStatus)
    {
        return (root, query, cb) ->
            onlineStatus == null ? null : cb.equal(root.get("onlineStatus"), onlineStatus);
    }


    public static Specification<DriverDO> withLicensePlate(String licensePlate)
    {
        return (root, query, cb) ->
            licensePlate == null
                ? null
                : cb.equal(
                root.join("car", JoinType.LEFT).get("licensePlate"), licensePlate);
    }


    public static Specification<DriverDO> withRating(String rating)
    {
        return (root, query, cb) ->
            rating == null
                ? null
                : cb.equal(
                root.join("car", JoinType.LEFT).get("rating"), rating);
    }


    public static Specification<DriverDO> withEngineType(EngineType engineType)
    {
        return (root, query, cb) ->
            engineType == null
                ? null
                : cb.equal(
                root.join("car", JoinType.LEFT).get("engineType"), engineType);
    }


    public static Specification<DriverDO> withConvertible(Boolean convertible)
    {
        return (root, query, cb) ->
            convertible == null
                ? null
                : cb.equal(
                root.join("car", JoinType.LEFT).get("convertible"), convertible);
    }


    public static Specification<DriverDO> withSelected(Boolean selected)
    {
        return (root, query, cb) ->
            selected == null
                ? null
                : cb.equal(
                root.join("car", JoinType.LEFT).get("selected"), selected);
    }
}
