# Propuestas de proyectos de DP2

### Petclinic

Entidades ya implementadas:

* Vet
* Speciality
* Type (animal)
* Owner
* Pet
* Visit

Historias de usuario ya implementadas:

* Como admin/owner, listar todos los owner, o los que coinicdan en nombre con un valor determinado
* Como admin/owner, mostrar la información de un owner, incluido sus pets y los visits de ellos
* Como admin/owner, editar la información de un pet
* Como admin/owner, añadir un pet a un owner
* Como admin/owner, añadir un visit a un pet de un owner
* Como admin/owner/vet (=authenticated), listar los vets con su especialidad

Problemas a resolver:

* Al meterse como vet en la pestaña owners salta una excpeción
* Existen muchos owners para el mismo usuario. Limitar a 1?
* Un usuario no autenticado no puede hacer absolutamente nada
* cambiar logo, quitar pestaña de error, añadir página inicial, poner página más bonita, ...

#### Propuesta de extensión (Miguel)

Se trata de hacer un sistema completo de gestión de veterinaria. Añadiría un rol adicional, worker, que se refiere a cualquier empleado que no sea veterinario y que tenga derechos adicionales.

Cambios en el modelo:

* Rol employee, con subroles vet, worker, admin
* Asignar vets a visit (relación 0:\*:0:\*)
* Añadir atributo a visit:
  * Hora inicio y fin
  * Recomendaciones / resultado 

Cambios en la aplicación existente:

* Quitarle a owner la capacidadad de cambiar un pet si tiene visit en el sistema.
* Quitarle a owner la capacidad de añadir visit (solo lo deberian de poder hacer empleados)
* Quitarle a owner la lista de owner, mostrar solo su perfil personal que deberia ser único

Historias de usuario nuevas:

1. Como employee, crear/editar cuenta de owner
2. Como employee, añadir pet a un owner y poder editarlo posteriormente
3. Como employee, crear visit para un pet. Poder asignar uno u varios vets. Tembién poder editar la visit y los vets asignados en cualquier momento.
4. Como vet asignado a una visit, poder escribir un resultado
5. Como worker, ver la planificación (tabla semanal) de cada vet
6. Como vet, ver la planificación (tabla semanal) propia





#### Cambios propuestos en la reunión

Entidades a modificar

* Visita (Veterinario, TipoVisita, Hora (restringida), Estado (aceptada o no), Diagnostico, pago (opcional))
* Administrador --> Vista de todas las visitas para poder aceptarla o rechazar

Entidades nuevas:

* TipoVisita (nombre, precio, duración): revision, operacion, malestar
* Diagnóstico (Fecha y hora, descripción, posología )
* Posología (medicamento, frecuencia, cantidad)
* Medicamento (nombre, empresa) 
* Pago (modo, creditcard, fecha, cantidad)
* Creditcard (titular, marca, numero, ccv, caducidad)
* Secretario extiende a person (usuario)