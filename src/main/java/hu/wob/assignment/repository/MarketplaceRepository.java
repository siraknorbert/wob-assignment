package hu.wob.assignment.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.wob.assignment.model.MarketplaceModel;

/**
 * Repository that handles data accessing and persisting of {@link MarketplaceModel} entities in database.
 */
@Repository
public interface MarketplaceRepository extends JpaRepository<MarketplaceModel, Integer> {

    @Query("SELECT m.id FROM MarketplaceModel m WHERE m.id IN :marketplaceIds")
    List<Integer> findIdsThatAreInGivenCollection(@Param("marketplaceIds") Collection<Integer> marketplaceIds);

    @Query("SELECT m FROM MarketplaceModel m WHERE m.id IN :marketplaceIds")
    List<MarketplaceModel> findThatAreInGivenCollection(@Param("marketplaceIds") Collection<Integer> marketplaceIds);
}
