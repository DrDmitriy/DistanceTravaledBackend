package backend.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import backend.entity.Coordinate;

/**
 * repository for Coordinate Node Entity
 */
public interface CoordinateRepository extends Neo4jRepository<Coordinate, Long> {

}
