# poo_tp6_grupo8

1. Responder y justificar las preguntas teóricas (a–g)

Estas son las preguntas de la página 1 del PDF. Te las respondo acá para que las tengas listas:

a. ¿Cuántos atributos y operaciones tiene la clase PorHora y Mensual?

PorHora:

Atributos propios: 2 (cupon, valorHora)
Atributos heredados de RegistroIngresoSalida: 5 (id, fecha, hora, vehiculo, estado)
Total atributos: 7
Operaciones propias: 1 (obtenerImporte() sobrescrito)
Operaciones heredadas: cambiarEstado(), getters/setters
Total operaciones relevantes: mínimo 2
Mensual:

Atributos propios: 1 (cliente)
Atributos heredados: 5 (id, fecha, hora, vehiculo, estado)
Total atributos: 6
Operaciones propias: 1 (obtenerImporte() sobrescrito)
Operaciones heredadas: cambiarEstado(), getters/setters
Total operaciones relevantes: mínimo 1
b. ¿Un ingreso por hora es un tipo de RegistroIngresoSalida?

Sí. PorHora hereda (extends) de RegistroIngresoSalida. Es una especialización: todo ingreso por hora es un registro de ingreso/salida, pero con comportamiento específico (cálculo por horas y cupón).

c. ¿Un cliente es un tipo de RegistroIngresoSalida?

No. Cliente está asociado a Mensual (relación "tiene-un cliente"), no hereda. Un cliente no es un registro de ingreso/salida, es una entidad independiente que se vincula a un tipo de tarifa.

d. ¿Cuántas clases abstractas y métodos abstractos hay?

Clases abstractas: 1 (RegistroIngresoSalida)
Métodos abstractos: 1 (obtenerImporte())
e. ¿El método obtenerImporte() es necesario implementarlo en las subclases?

Sí, es obligatorio. Al ser abstract en RegistroIngresoSalida, cada subclase concreta (PorHora, Mensual) debe implementarlo, porque cada una calcula el importe de forma distinta:

PorHora → horas × valor, con descuento si el cupón es válido.
Mensual → siempre 0.0.
f. ¿El método registrarIngreso(registro) es necesario implementarlo en las subclases de Manager?

No aplica. Manager no es abstracto ni tiene subclases en el diagrama. Si existieran subclases de Manager, solo sería obligatorio si el método fuera abstract en Manager. Como no lo es, no hay obligación de sobrescribirlo.

g. ¿Vehiculo es una subclase de RegistroIngresoSalida?

No. Vehiculo está asociado a RegistroIngresoSalida (relación "vehiculo 1..*"), no hereda. Un vehículo no es un registro de ingreso/salida; es una entidad que se vincula a un registro.

Punto 2
Historias de usuario.
HU1 –
Como encargado de la empresa, quiero registrar un empleado ingresando su legajo, documento, nombre, fecha de ingreso, cantidad de hijos y tipo de empleado, para poder gestionar sus datos y calcular posteriormente su sueldo neto.

HU2 –
Como encargado de la empresa, quiero registrar un empleado profesional y asociarle uno o más títulos indicando año, nombre de la carrera y nivel, para que cada título genere el adicional correspondiente en su sueldo.

HU3 –
Como encargado de la empresa, quiero asignar y modificar la categoría de un empleado administrativo entre A, B o C, para que el sueldo se calcule de acuerdo con el adicional correspondiente a su categoría.

HU4 – 
Como encargado de la empresa, quiero calcular el sueldo de un empleado de limpieza considerando su adicional por insalubridad, para obtener correctamente su sueldo neto.

HU5 – 
Como encargado de la empresa, quiero que el sistema calcule la antigüedad del empleado a partir de su fecha de ingreso, para incorporar $6.500 por cada año de antigüedad a los remunerativos bonificables.

HU6 – 
Como encargado de la empresa, quiero que el sistema calcule el salario familiar considerando la cantidad de hijos a cargo, otorgando $15.000 por cada hijo, para incorporarlo al sueldo neto.

HU7 –
Como encargado de la empresa, quiero que el sistema calcule los descuentos equivalentes al 18% de los remunerativos bonificables, para determinar correctamente el sueldo neto.

HU8 – 
Como encargado de la empresa, quiero obtener el sueldo neto de cada empleado considerando sueldo básico, adicionales según el tipo, antigüedad, categoría cuando corresponda, salario familiar y descuentos, para conocer el importe final que debe percibir.

HU9 – 
Como encargado de la empresa, quiero buscar un empleado mediante su legajo, para consultar sus datos personales y el sueldo neto que le corresponde.

HU10 –
Como encargado de la empresa, quiero buscar un empleado administrativo por su legajo y cambiar su categoría, para actualizar su remuneración de acuerdo con la nueva categoría.

HU11 –
Como encargado de la empresa, quiero buscar un empleado profesional por su legajo y agregarle un nuevo título, para actualizar los adicionales que corresponden a su sueldo.
