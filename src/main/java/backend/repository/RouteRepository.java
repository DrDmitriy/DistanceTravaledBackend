package backend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import backend.entity.Route;
/**
 * repository for Route Node Entity
 */
public interface RouteRepository extends Neo4jRepository<Route, Long> {
    public Iterable<Route> findAllByUserIDEquals(Long userId);
}
