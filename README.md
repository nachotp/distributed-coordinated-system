# distributed-coordinated-system
Distributed coordinated system using the bully algorithm
Ignacio Tampe Palma - Rol : 201573514-k
Franco Zalavari Palma - Rol : 201573501-8

 Para compilar y ejecutar el projecto usar ```make```.

 Los archivos JSON se encuentran en la carpeta data

Procedimiento escogido:
 - Escogimos el procedimiento en el que se envıan los cambios al coordinador actual y este propaga a las replicas. Escogimos este procedimiento ya que asegura un nivel de consistencia mayor que el otro (completa). Si bien, el numero de mensajes sera mayor, al igual que la latencia, se tendra una mayor consistencia, que es importante en este tipo de sistemas.

Observaciones:
- El archivo pacientes.JSON fue modificado, con tal de poder manipularlo mas facilmente. Se le agrega parametro Paciente para su lectura.
- La ```contraseña``` de las maquinas virtuales fue reemplazada por: ```momos123```
