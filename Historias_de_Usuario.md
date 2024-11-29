# Historia de Usuario 1: Registrar la Compra de Acciones

**Como** usuario,  
**quiero** registrar la compra de acciones,  
**para** poder llevar un registro de las transacciones realizadas y conocer el valor de las acciones adquiridas.

## Criterios de Aceptación

### Escenario 1: Ingresar el Símbolo Bursátil
- **Dado que** el usuario esta en el formulario para registrar la compra de acciones,  
- **Cuando** ingresa un símbolo bursátil válido (como "AAPL" o "TSLA"),  
- **Entonces** el sistema debe aceptar el simbolo ingresado.

### Escenario 2: Ingresar la Fecha de Compra
- **Dado que** el usuario esta en el formulario para registrar la compra de acciones,  
- **Cuando** selecciona en un calendario una fecha válida de compra,  
- **Entonces** el sistema debe aceptar y mostrar la fecha correctamente.

### Escenario 3: Ingresar la Cantidad de Acciones Compradas
- **Dado que** el usuario esta en el formulario de compra de acciones,  
- **Cuando** ingresa una cantidad válida de acciones (número positivo entero),  
- **Entonces** el sistema debe aceptar y mostrar la cantidad de acciones correctamente.

### Escenario 4: Ingresar el Valor de Compra de Cada Acción
- **Dado que** el usuario esta en el formulario de compra de acciones,  
- **Cuando** ingresa un valor de compra válido para cada acción (número positivo con decimales),  
- **Entonces** el sistema debe aceptar y mostrar el valor de compra de las acciones correctamente.

### Escenario 5: Almacenar los Datos de Forma Segura en la Base de Datos
- **Dado que** los datos de la compra de acciones son ingresados correctamente,  
- **Cuando** se haga clic en el botón de registrar,  
- **Entonces** los datos deben ser almacenados correctamente en la base de datos de manera segura.

### Escenario 6: Confirmación de Registro Exitoso
- **Dado que** los datos de la compra de acciones han sido registrados correctamente,  
- **Cuando** el registro se complete exitosamente,  
- **Entonces** el sistema debe mostrar un mensaje de confirmación indicando que la compra ha sido registrada con éxito.

### Escenario 7: Ingresar un Símbolo Bursátil Inválido
- **Dado que** el usuario esta en el formulario para registrar la compra de acciones,  
- **Cuando** ingresa un símbolo bursátil que no existe,  
- **Entonces** el sistema debe mostrar un mensaje de error indicando que el símbolo ingresado no existe.  

### Escenario 8: Ingresar una Cantidad Negativa de Acciones
- **Dado que** el usuario esta en el formulario para registrar la compra de acciones,  
- **Cuando** ingresa una cantidad negativa de acciones,  
- **Entonces** el sistema debe mostrar un mensaje de error de invalidez de números negativos  

### Escenario 9: Ingresar un Valor de Compra Negativo
- **Dado que** el usuario esta en el formulario para registrar la compra de acciones,  
- **Cuando** ingresa un valor de compra negativo para una acción,  
- **Entonces** el sistema debe mostrar un mensaje de error de invalidez de números negativos
## Tareas

1. Crear un formulario para ingresar el simbolo Bursatil, la fecha, cantidad de acciones y valor de compra.
2. Configurar la base de datos para registrar la información de la compra.
3. Crear un método en el backend que registre los datos de la compra de acciones.
4. Implementar una notificación de éxito cuando los datos se hayan registrado correctamente.
5. Validar el campo del símbolo bursátil en el formulario para que acepte únicamente simbolos existentes
6. Validar el campo de Valor de compra y Cantidad de acciones en el formulario para que acepte únicamente.

# Historia de Usuario 2: Ver Detalles de un Registro

**Como** usuario,  
**quiero** poder seleccionar un registro específico de mis compras de acciones,  
**para** ver los detalles  del registro seleccionado.

## Criterios de Aceptación

### **Escenario 1: Ver Detalles de un Registro**
**Dado** que el usuario observa la lista de registros de compra de acciones,  
**Cuando** el usuario selecciona un registro específico,  
**Entonces** el sistema debe mostrar los detalles de ese registro.

## Tareas
1. Actualizar la interfaz del usuario para mostrar todos los registros de compra de acciones.
2. Implementar la funcionalidad para que el usuario pueda seleccionar un registro específico de la lista.
3. Crear la interfaz para mostrar los detalles del registro seleccionado.



# Historia de Usuario 3: Cálculo de Ganancia/Pérdida
**Como** usuario,  
**quiero** ver la ganancia o pérdida de mis acciones,  
**para** poder evaluar el rendimiento de mi inversión en tiempo real.

## Criterios de Aceptación

### **Escenario 1: Ver Ganancia**
**Dado** que el usuario ha registrado una compra de acciones con un valor de compra determinado,  
**Cuando** el usuario consulta la ganancia en la fecha actual,  
**Entonces** el sistema debe calcular el porcentaje de ganancia, mostrando un valor positivo si el valor actual de las acciones es mayor que el valor de compra.

---

### **Escenario 2: Ver Pérdida**
**Dado** que el usuario ha registrado una compra de acciones con un valor de compra determinado,  
**Cuando** el usuario consulta la pérdida en la fecha actual,  
**Entonces** el sistema debe calcular el porcentaje de pérdida, mostrando un valor negativo si el valor actual de las acciones es menor que el valor de compra.

---

### **Escenario 3: Mostrar el Porcentaje de Ganancia/Pérdida**
**Dado** que el cálculo de ganancia o pérdida ha sido realizado,  
**Cuando** el usuario ve el resultado,  
**Entonces** el sistema debe mostrar el porcentaje de ganancia o pérdida de forma clara, indicando si es un valor positivo (ganancia) o negativo (pérdida).


## Tareas

1. Implementar la lógica para calcular la ganancia cuando el valor actual de las acciones es mayor que el valor de compra.
2. Implementar la lógica para calcular la pérdida cuando el valor actual de las acciones es menor que el valor de compra.
3. Crear una interfaz para mostrar el porcentaje de ganancia o pérdida, la ganancia y la perdida al usuario.
