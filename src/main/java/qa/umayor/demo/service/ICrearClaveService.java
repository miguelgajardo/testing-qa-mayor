package qa.umayor.demo.service;

import qa.umayor.dto.CrearClaveRequest;
import qa.umayor.dto.CrearClaveResponse;

public interface ICrearClaveService {
	
	public CrearClaveResponse crearClave(CrearClaveRequest request) throws Exception;

}