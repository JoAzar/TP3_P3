# Trabajo Práctico N°3 de Programación 3 

---

### Package Model
Dentro va todo el código de negocio del proyecto

### Package View
Detro va todo el código de la vista donde el usuario interactua con el programa

### Package Presenter
Dentro va la clase Presenter y todo lo que involucre al mismo

---

### Class Model abarca tres clases Tablero/Backtracking/Solver

### Class Tablero
Se encarga de crear una instancia del tablero con los métodos respectivos del tablero como verificar que los valores ingresados por los usuarios tengan validez

### Class Backtracking
Esta clase verifica que el tablero tenga resultados por medio de fuerza bruta con podas

### Class Solver
Es una clase que sirve como intersección entre Backtracking y Tablero para evitar que ambas clases tengan que depender directamente de la otra, de esta manera el código queda más limpio y cada clase cumple con su funcionalidad independientemente de otras clases

---

### Class Presenter
Se encarga de conectar el código de negocio/modelo con la vista, es un intermediario entre modelo y vista. Permite la que la interacción del usuario vaya hasta el código de negocio sin que el usuario pueda tener una interacción directa con dicho código

### Class View
Se encarga de todo lo relacionado con la vista, toda interacción con los usuarios con el programa depende de esta clase
