# KallSonysProject
Proyecto KallSony's para la materia de Proyecto de Implementación de la Especialización en Arquitectura Empresarial de Software de la Universidad Javeriana

Desarrolladores: 
  
	David Camacho	https://github.com/davidcinho89
	Diego Sánchez	https://github.com/dmsanchezr79
	Felipe Grisales
	Marcela Vaquiro	https://github.com/marcevaquiro
	Michael Rondón	https://github.com/MichaelRondon

Estructura:

	  KallSonysProject/servicios/ProductService
	  KallSonysProject/servicios/ClientService
	  KallSonysProject/servicios/ImagesService
	  KallSonysProject/servicios/LogonService
	  KallSonysProject/OMS
	  KallSonysProject/B2C
	  KallSonysProject/SOA

Ejecutar servicio de productos:

	1. Una vez instalado maven y docker ejecutar:
	
		sh KallSonysProject/servicios/start.sh
		
	2. Verificar que el servicio se este ejecutando con el comando 
	
		docker ps
		
	3. Ingresar mediante un navegador a la dirección:
	
		DOCKER_HOST:7076/swagger-ui.html
