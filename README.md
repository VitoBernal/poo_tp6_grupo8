# Trabajo Práctico 6 – Clases y Relaciones

**Programación Orientada a Objetos** – Analista Programador Universitario – Extensión Áulica San Pedro
**Grupo 8**

## Integrantes

- VITO BERNARDO BERNAL
- DANIEL ORLANDO VARGAS RUIZ
- SERGIO SEBASTIÁN ORTEGA

## Estructura del proyecto y ejecución

| Ejercicio | Paquete | Clase principal |
|---|---|---|
| 1 – Playa de estacionamiento | `ar.edu.unju.fi.poo.estacionamiento` | `MainEstacionamiento` |
| 2 – Sueldos de empleados | `ar.edu.unju.fi.poo.empleados` | `MainEmpleados` |

---

## Ejercicio 1 – Playa de estacionamiento

### 1. Preguntas

**a. ¿Cuántos atributos y operaciones tienen las clases PorHora y Mensual?**

- **PorHora:** declara **2 atributos** propios (`cupon`, `valorHora`) y **ninguna operación** propia en el diagrama. Hereda de `RegistroIngresoSalida` 5 atributos (`id`, `fecha`, `hora`, `vehiculo`, `estado`) y 2 operaciones (`obtenerImporte()`, `cambiarEstado()`). En total tiene **7 atributos y 2 operaciones**.
- **Mensual:** declara **1 atributo** propio (`cliente`) y **ninguna operación** propia. Con lo heredado tiene **6 atributos y 2 operaciones**.

En las dos clases, `obtenerImporte()` es abstracta en la superclase, así que cada una debe implementarla. No se cuentan constructores, getters ni setters porque no figuran en el diagrama.

**b. ¿Un ingreso por hora es un tipo de RegistroIngresoSalida?**
Sí. Hay una generalización (flecha con triángulo) de `PorHora` hacia `RegistroIngresoSalida`: un `PorHora` *es un* `RegistroIngresoSalida`. Hereda sus atributos y operaciones y puede usarse donde se espera un `RegistroIngresoSalida`, por ejemplo en `registrarIngreso(registro)`.

**c. ¿Un cliente es un tipo de RegistroIngresoSalida?**
No. `Cliente` se relaciona con `Mensual` mediante una agregación (rombo vacío, rol `cliente`): un registro mensual *tiene un* cliente. Es una relación *tiene un*, no *es un*.

**d. ¿Cuántas clases abstractas y métodos abstractos hay?**
Una clase abstracta, `RegistroIngresoSalida`, y un método abstracto, `obtenerImporte()`. Los dos se escriben en cursiva en el diagrama.

**e. ¿El método obtenerImporte() es necesario implementarlo en las subclases de RegistroIngresoSalida?**
Sí. Al ser abstracto, toda subclase concreta está obligada a implementarlo; si no lo hace, la subclase también tendría que ser abstracta. Cada una lo calcula distinto:
- `PorHora`: horas × valor hora, menos el descuento del cupón si no está vencido.
- `Mensual`: devuelve 0.

**f. ¿El método registrarIngreso(registro) es necesario implementarlo en las subclases de Manager?**
No. `Manager` es una clase concreta, no tiene subclases en el diagrama y `registrarIngreso` no es abstracto. Si existieran subclases, lo heredarían ya implementado y redefinirlo sería opcional.

**g. ¿Vehiculo es una subclase de RegistroIngresoSalida?**
No. `Vehiculo` se relaciona con `RegistroIngresoSalida` mediante una agregación (rol `vehiculo`): un registro *tiene un* vehículo. No hay generalización entre ellas.

### 2. Implementación del diagrama – decisiones

- `Date` se implementó con `LocalDate` (fecha) y `LocalTime` (hora).
- El estado es un `String`, como indica el diagrama, con las constantes `INGRESADO` y `AFUERA`.
- **Vehículo ya ingresado:** `Manager.registrarIngreso` usa `validarPatente` para rechazar un vehículo que ya está dentro de la playa.
- **Horario:** los ingresos por hora solo se permiten de 08:00 a 21:00; fuera de ese horario no se puede crear el registro `PorHora`. Los clientes mensuales no tienen restricción de horario.
- **Importe por hora:** $1.000 por hora. No se cobra fracción: toda hora empezada se cobra completa, con un mínimo de 1 hora. El cupón se aplica solo si no está vencido.
- **Importe mensual:** es $0, porque la cuota mensual no se gestiona en esta aplicación.

### 3. Casos de prueba (`MainEstacionamiento`)

| Caso | Qué se prueba |
|---|---|
| a | Ingreso por hora sin cupón. |
| b | Ingresos por hora con cupón vigente (10%) y vencido; salida simulando 3 horas de estadía para comparar los importes con y sin descuento. |
| c | Ingreso por hora a las 22:30, que se rechaza por estar fuera de horario. |
| d | Ingreso de un cliente mensual, con importe $0. |
| e | Búsqueda de un registro por ID mostrando sus datos e importe actual. |
| f | Búsqueda por ID y salida de un vehículo por hora: se informa el importe y el estado pasa a AFUERA. |
| g | Búsqueda por ID y salida de un cliente mensual: importe $0 y estado AFUERA. |

---

## Ejercicio 2 – Sueldos de empleados

### Etapa de análisis y diseño: Historias de usuario

**HU01 – Registrar empleado**
Como encargado de liquidación de sueldos, quiero registrar un empleado con su legajo, documento, nombre, fecha de ingreso, cantidad de hijos y tipo, para gestionar sus datos y calcular su sueldo.
*Criterios de aceptación:*
- Si el legajo ya existe, el empleado no se registra (el legajo no se puede repetir).
- Todos los datos son obligatorios, la fecha de ingreso no puede ser futura y la cantidad de hijos no puede ser negativa.
- El tipo puede ser Profesional, Administrativo o Limpieza.

**HU02 – Registrar títulos de un profesional**
Como encargado, quiero registrar los títulos de un profesional (año, carrera y nivel), para que cada título sume su adicional al sueldo.
*Criterios de aceptación:*
- Un profesional se registra con al menos un título.
- El nivel del título puede ser Universitario o Terciario.
- Cada título suma un adicional de $30.000.

**HU03 – Asignar categoría a un administrativo**
Como encargado, quiero asignar una categoría a cada administrativo, para que su sueldo incluya el monto de esa categoría.
*Criterios de aceptación:*
- La categoría es obligatoria.
- Los montos son: A (Auxiliar) $30.000, B (Ventas) $45.000 y C (Gerencia) $55.000.

**HU04 – Adicional por insalubridad**
Como encargado, quiero que a los empleados de limpieza se les sume el adicional por insalubridad, para liquidar correctamente su sueldo.
*Criterios de aceptación:*
- El adicional es de $25.000.
- Se aplica solo a los empleados de limpieza.

**HU05 – Calcular antigüedad**
Como encargado, quiero que el sistema calcule la antigüedad a partir de la fecha de ingreso, para sumar $6.500 por cada año de antigüedad.
*Criterios de aceptación:*
- Solo se cuentan años completos: si en el año actual todavía no se cumplió el aniversario de ingreso, ese año no se cuenta.

**HU06 – Calcular salario familiar**
Como encargado, quiero que se pague el salario familiar por los hijos a cargo, para incluirlo en el sueldo neto.
*Criterios de aceptación:*
- Se pagan $15.000 por cada hijo.
- Si el empleado no tiene hijos, el salario familiar es $0.

**HU07 – Calcular descuentos**
Como encargado, quiero que se calculen los descuentos, para obtener el sueldo neto.
*Criterios de aceptación:*
- Los descuentos son el 18% de los remunerativos bonificables.

**HU08 – Calcular sueldo neto**
Como encargado, quiero obtener el sueldo neto de cada empleado, para saber cuánto debe cobrar.
*Criterios de aceptación:*
- Remunerativos bonificables = Sueldo básico ($400.000) + Adicional según el tipo + Antigüedad.
- Sueldo neto = Remunerativos bonificables + Salario familiar − Descuentos.
- Cada adicional se aplica solo al tipo de empleado que corresponde.

**HU09 – Consultar empleado por legajo**
Como encargado, quiero buscar un empleado por legajo, para ver sus datos personales y su sueldo neto.
*Criterios de aceptación:*
- Se muestran los datos personales y el detalle de cada concepto del sueldo.
- Si el legajo no existe, se informa.

**HU10 – Cambiar la categoría de un administrativo**
Como encargado, quiero cambiar la categoría de un administrativo buscándolo por legajo, para actualizar su sueldo.
*Criterios de aceptación:*
- Se muestra el sueldo neto antes y después del cambio.
- Si el legajo no corresponde a un administrativo, se informa y no se modifica nada.

**HU11 – Agregar un título a un profesional**
Como encargado, quiero agregar un título a un profesional buscándolo por legajo, para actualizar su adicional.
*Criterios de aceptación:*
- Se muestra el sueldo neto antes y después de agregar el título.
- Si el legajo no corresponde a un profesional, se informa.

**HU12 – Listar empleados de una categoría**
Como encargado, quiero ver los empleados de una categoría, para conocer lo que se liquida en esa categoría.
*Criterios de aceptación:*
- Se muestra cada empleado con sus remunerativos, salario familiar, descuentos y neto.
- Al final se muestran los totales acumulados de esos cuatro conceptos.

**HU13 – Importe neto acumulado por tipo de empleado**
Como encargado, quiero obtener el importe neto acumulado de un tipo de empleado elegido por mí, para conocer el costo de cada tipo de personal.
*Criterios de aceptación:*
- El tipo se solicita al usuario y se valida.
- Se muestra el detalle de cada empleado de ese tipo y el total acumulado.

### Etapa de implementación

#### 1. Diagrama de clases

![Diagrama de clases – Ejercicio 2](TrabajoPractico6/DiagramaUML_Ejercicio2.png)

Decisiones de diseño:

- **`Empleado` es abstracta**, porque no existe un "empleado genérico". Su único método abstracto es `getTipo()`.
- **`calcularAdicional()` es un único método.** En `Empleado` devuelve 0 (empleado sin adicional) y cada subclase lo redefine (polimorfismo):
  - Profesional: $30.000 por título.
  - Administrativo: monto de su categoría.
  - Limpieza: $25.000 por insalubridad.
- **El cálculo del sueldo se implementa una sola vez en `Empleado`** y no se duplica en las subclases. Esto incluye `calcularRemunerativosBonificables()`, `calcularSalarioFamiliar()`, `calcularDescuentos()` y `calcularSueldoNeto()`.
- **`Categoria` es un enumerado con descripción y monto**, en lugar de valores sueltos en el código.
- **`Profesional – Titulo` es una composición 1..*.** El constructor de `Profesional` exige un título inicial.
- **`ManagerEmpleado` concentra las reglas de negocio**, como el legajo único, y las operaciones de búsqueda, modificación y totales.


#### 2 y 3. Implementación y `ManagerEmpleado`

`ManagerEmpleado.inicializarEmpleados()` carga 6 empleados, dos de cada tipo.

#### 4. Casos de prueba (`MainEmpleados`)

| Caso | Qué se prueba |
|---|---|
| a | Se agregan 3 empleados de distintos tipos y se intenta agregar un legajo repetido, que se rechaza. |
| b | Búsqueda por legajo con el detalle completo del sueldo neto; búsqueda de un legajo inexistente. |
| c | Cambio de categoría de un administrativo (B → C) con el sueldo antes y después; intento sobre un empleado que no es administrativo. |
| d | Se agrega un título a un profesional, con el sueldo antes y después; intento sobre un empleado que no es profesional. |
| e | Empleados de la categoría ingresada por el usuario, con los totales de remunerativos, salario familiar, descuentos y neto. |
| f | Importe neto acumulado del tipo de empleado ingresado por el usuario. |
