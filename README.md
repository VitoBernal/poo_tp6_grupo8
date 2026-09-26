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
