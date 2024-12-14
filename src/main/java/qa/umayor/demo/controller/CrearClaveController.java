package qa.umayor.demo.controller;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;
import jakarta.servlet.http.HttpServletRequest;
import qa.umayor.demo.service.ICrearClaveService;
import qa.umayor.dto.CrearClaveRequest;
import qa.umayor.dto.CrearClaveResponse;
import qa.umayor.tester.SignUpApiDelegate;

@Controller
public class CrearClaveController implements SignUpApiDelegate  {

	private final HttpServletRequest request;
	
	private final ICrearClaveService service;
	
	@Autowired
	public CrearClaveController(HttpServletRequest request, ICrearClaveService service) {
		this.request = request;
		this.service = service;
	}
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		// TODO Auto-generated method stub
		return SignUpApiDelegate.super.getRequest();
	}

	@Override
	public ResponseEntity<CrearClaveResponse> crearClave(CrearClaveRequest crearClaveRequest) {
		CrearClaveResponse response = new CrearClaveResponse();
		try {
			service.crearClave(crearClaveRequest);
			response.setMensaje("OK");
			response.setOk(true);
		} catch (Exception e) {
			response.setOk(false);
			response.setMensaje("ERROR" + e.getMessage());
			return new ResponseEntity<CrearClaveResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CrearClaveResponse>(response, HttpStatus.OK);
	}
}