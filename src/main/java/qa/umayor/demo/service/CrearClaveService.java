package qa.umayor.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qa.umayor.demo.model.CredencialModel;
import qa.umayor.demo.repository.IClaveRepository;
import qa.umayor.dto.CrearClaveRequest;
import qa.umayor.dto.CrearClaveResponse;

@Service
public class CrearClaveService implements ICrearClaveService {

	@Autowired
	IClaveRepository repository;
	
	@Override
	public CrearClaveResponse crearClave(CrearClaveRequest request) throws Exception {
		Optional<CredencialModel> optional = null;
		boolean existe = false;
		try {
			optional = repository.findByRut(request.getRut());
		} catch (Exception e) {
			throw new Exception(e);
		}
		CrearClaveResponse response = new CrearClaveResponse();
		CredencialModel model = new CredencialModel();
		model.setRut(request.getRut());
		model.setClave(request.getPassword());
		model.setEstado("V");
		model = repository.saveAndFlush(model);
		if (model == null) {
			throw new RuntimeException("");
		} else {
			response.setMensaje("OK");
			return response;
		}
}
}
