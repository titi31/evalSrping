package co.simplon.evaluation.dao;

import co.simplon.evaluation.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface CityRepositry extends JpaRepository<City,Long> {
}
