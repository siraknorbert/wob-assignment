package hu.wob.assignment.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.wob.assignment.model.LocationModel;

/**
 * Repository that handles data accessing and persisting of {@link LocationModel} entities in database.
 */
@Repository
public interface LocationRepository extends JpaRepository<LocationModel, String> {

    @Query("SELECT l.id FROM LocationModel l WHERE l.id IN :locationIds")
    List<String> findIdsThatAreInGivenCollection(@Param("locationIds") Collection<String> locationIds);

    @Query("SELECT l FROM LocationModel l WHERE l.id IN :locationIds")
    List<LocationModel> findThatAreInGivenCollection(@Param("locationIds") Collection<String> locationIds);
}
