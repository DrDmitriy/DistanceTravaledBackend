package backend.repository;

import backend.entity.Coordinate;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * repository for Coordinate Node Entity
 */
public interface CoordinateRepository extends Neo4jRepository<Coordinate, Long> {

}
