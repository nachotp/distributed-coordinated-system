# distributed-coordinated-system
Distributed coordinated system using the bully algorithm
Ignacio Tampe Palma - Rol : 201573514-k
Franco Zalavari Palma - Rol : 201573501-8

 Para compilar y ejecutar el projecto usar ```make```.

 Los archivos JSON se encuentran en la carpeta data

 Se implemento solucion solo para los medicos y se hace asignacion segun el criterio (años). Doctor bloquea un recurso y ningun otro doctor puede acceder a este mientras el coordinador no lo permita. Cuando se aprueba el cambio, se libera recurso y alguien mas lo puede recibir. Y constantemente se reasigna el coordinador.
 ```/commit?accion=1&opcion=metformina&id=1``` bloquea el recurso si es que puede, o dira que no es posible hacerlo. 
 ```/pushed?accion=1&opcion=metformina&id=1``` libera el recurso y replicara el cambio en todos los servidores.

Procedimiento escogido:
 - Escogimos el procedimiento en el que se envıan los cambios al coordinador actual y este propaga a las replicas. Escogimos este procedimiento ya que asegura un nivel de consistencia mayor que el otro (completa). Si bien, el numero de mensajes sera mayor, al igual que la latencia, se tendra una mayor consistencia, que es importante en este tipo de sistemas.

Observaciones:
- El archivo pacientes.JSON fue modificado, con tal de poder manipularlo mas facilmente. Se le agrega parametro Paciente para su lectura.
- La ```contraseña``` de las maquinas virtuales fue reemplazada por: ```momos123```