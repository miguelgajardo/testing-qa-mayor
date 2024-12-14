package qa.umayor.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qa.umayor.demo.model.CredencialModel;

@Repository
public interface IClaveRepository extends JpaRepository<CredencialModel, String> {
	
	public Optional<CredencialModel> findByRut(String rut);

}