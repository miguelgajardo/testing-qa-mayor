package qa.umayor.demo.cucumber;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.spring.CucumberContextConfiguration;
import qa.umayor.demo.DemoApplication;
import qa.umayor.demo.model.CredencialModel;
import qa.umayor.demo.repository.IClaveRepository;
import qa.umayor.dto.CrearClaveRequest;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = DemoApplication.class)
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class MicroservicioDefinitions {
	
	@MockitoBean
	@Autowired
	private IClaveRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	ResultActions resultActions;
	
	private String rut;
	private String password;
	private String estado;
	
	@Before
	public void mockServicio() throws Exception {
		Mockito.reset(repository);
	}
	
	@Dado("los parametros de entrada son válidos")
	public void los_parametros_de_entrada_son_validos() {
		rut = "176850116";
		password = "sanP8xwi2FGncR3";
		estado = "vigente";
	}
	
	@Dado("parametros de entrada no validos")
	public void parametros_de_entrada_no_validos() {
		rut = null;
		password = null;
		estado = null;
	}
	
	@Y("se consulta al cliente con respuesta existe")
	public void se_consulta_al_cliente_con_respuesta_existe() throws Exception {
		CredencialModel model = new CredencialModel();
		model.setRut("");
		Optional<CredencialModel> optional = Optional.of(model);
		try {
			Mockito.when(repository.findByRut(Mockito.eq(rut)))
					.thenReturn(optional);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Y("se crea clave del cliente de forma exitosa")
	public void se_crea_clave_del_cliente_de_forma_exitosa() throws Exception {
		CredencialModel saved = new CredencialModel();
		saved.setRut(rut);
		saved.setClave(password);
		saved.setEstado(estado);
		try {
			Mockito.when(repository.saveAndFlush(any(CredencialModel.class))).thenReturn(saved);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Y("error al crear la clave en base de datos")
	public void error_al_crear_la_clave_en_base_de_datos() throws Exception {
		try {
			Mockito.doThrow(new RuntimeException()).when(repository).save(Mockito.any());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Cuando("se solicita crear la clave del cliente")
	public void se_solicita_crear_la_clave_del_cliente() throws Exception {
		CrearClaveRequest requestAPI = new CrearClaveRequest(rut, password, estado);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter myObject = mapper.writer().withDefaultPrettyPrinter();
		String request = myObject.writeValueAsString(requestAPI);
		resultActions = mockMvc.perform(post("/v1/crear-clave")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept("application/json")
				.content(request))
				.andDo(print());
	}
	
	@Entonces("response http 200 OK")
	public void response_http_200_ok() throws Exception {
		resultActions.andDo(print())
				.andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("mensaje").value("OK"))
				.andExpect(jsonPath("ok").value(true));
	}
	
	@Entonces("bad request parametros no validos")
	public void bad_request_parametros_no_validos() throws Exception {
		resultActions.andDo(print())
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Entonces("error interno")
	public void error_interno() throws Exception {
		resultActions.andDo(print())
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	
}
}