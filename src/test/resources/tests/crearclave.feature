# language: es
Característica: Flujo de creacion de contraseña
    
    
	Escenario: creación exitosa de la clave
		Dado los parametros de entrada son válidos
		Y se consulta al cliente con respuesta existe
		Y se crea clave del cliente de forma exitosa
		Cuando se solicita crear la clave del cliente
		Entonces response http 200 OK

	Escenario: valida los valores de entrada
		Dado parametros de entrada no validos
		Cuando se solicita crear la clave del cliente
		Entonces bad request parametros no validos
		
	Escenario: error al crear la clave
		Dado los parametros de entrada son válidos
		Y error al crear la clave en base de datos
		Cuando se solicita crear la clave del cliente
		Entonces error interno