# Datos a usar de la base de datos

**Admins**

user: admin1
password: 4dm1n
Escenarios: US19-P1



**Owners**

user: owner1
password: 0wn3r
pets: Pepe
Escenarios: US1-P1, US1-N1, US2-P1, US2-N1, US3-P1, US3-N1, US4-P1, US4-N1, USA-N1, USA-N2, US5B-P1, USB-N2, US6-P1, US6-N2

user: owner2
password: owner2
pets: Mario
*No tiene ninguna visita programada*.
Escenarios: US1-N1, US5A-P2, USB-N1

user: owner3
password: owner3
*No tiene ninguna mascota*
Escenarios: US1-N2



**Vets**

user: vet2
password: vet2
firstName: Antonio
lastName: Sánchez
*Slots reservados para meter citas en escenarios:* 

* 2020-08-03 11:00 am  (US1-P1)
* 2020-08-03 12:00 am  (US1-N1)
* 2020-08-03 1:00 pm  (US2-P1)
* 2020-08-03 2:00 pm  (US3-P1)
* 2020-08-03 3:00 pm  (US4-P1)
* 2020-08-03 4:00 pm  (US4-N1)

*Vistas ya existentes*:

* 2020-02-03 10:00 am consultation con owner1/Pepe (USA-N2, US5B-P1, US6-N2)
* 2020-09-01 10:00 am consultation con owner1/Pepe (USA-N1, USB-N2, US6-P1)

Escenarios: US1-P1, US1-N1, US2-P1, US3-P1, US3-N1, US4-P1, US4-N1, USA-N1, USA-N2, US5B-P1, USB-N2, US6-P1, US6-N2

user: vet3
password: vet3
*No tiene nignua visita en el futuro.*
Escenarios: US6-N1



Nombres reservados para vets que no se pueden usar:

* Invalid Vet (US2-N1)



**VisitTypes**

name: consultation
price: 20€
Escenarios: US1-P1, US1-N1, US2-P1, US2-N1, US3-P1, US3-N1, US4-P1, USA-N1, USA-N2, US5B-P1, USB-N2

name: revision
Escenarios: US1-N1



Nombres reservados para vets que no se pueden usar:

* Invalid Type (US4-N1)



**Medicine**

name: Betadine
Ese medicamento debe de estar en alguna receta para que no se pueda borrar en la prueba de US17-N1.



**Visits**

dejar libre todos los huecos en agosto de 2020 para poder hacer pruebas.



**Secretaries**

user: secretary1
password: s3cr3tary