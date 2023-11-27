package hu.wob.assignment.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.wob.assignment.model.ListingStatusModel;

/**
 * Repository that handles data accessing and persisting of {@link ListingStatusModel} entities in database.
 */
@Repository
public interface ListingStatusRepository extends JpaRepository<ListingStatusModel, Integer> {

    @Query("SELECT ls.id FROM ListingStatusModel ls WHERE ls.id IN :listingStatusIds")
    List<Integer> findIdsThatAreInGivenCollection(@Param("listingStatusIds") Collection<Integer> listingStatusIds);

    @Query("SELECT ls FROM ListingStatusModel ls WHERE ls.id IN :listingStatusIds")
    List<ListingStatusModel> findThatAreInGivenCollection(@Param("listingStatusIds") Collection<Integer> listingStatusIds);
}
