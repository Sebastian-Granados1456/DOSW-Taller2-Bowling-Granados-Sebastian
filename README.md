# DOSW-Taller2-Bowling-Granados-Sebastian

## 1. Identificación

- **Nombre completo:** Sebastián Camilo Granados López
- **Código estudiantil:** 1000102459
- **Correo institucional:** sebastian.granados-l@mail.escuelaing.edu.co

## 2. Descripción

BowlTech S.A.S. quiere digitalizar el sistema de puntuación de sus pistas de bolos. Este proyecto implementa el motor de puntuación de un juego de Bowling aplicando Test-Driven Development (TDD) desde cero, cubriendo las reglas del dominio: tiro normal, spare, strike, el frame 10 (con sus casos especiales de bonos) y el juego perfecto (300 puntos).

### Reglas implementadas

| Situación | Condición | Puntuación |
|---|---|---|
| Tiro normal | Derriba algunos pinos sin completar 10 | Solo los pinos derribados en ese tiro |
| Spare (/) | Derriba los 10 pinos en 2 intentos del mismo frame | 10 + primer tiro del siguiente frame |
| Strike (X) | Derriba los 10 pinos en el primer intento | 10 + suma de los dos tiros siguientes |
| Frame 10 | Si hay strike o spare en el frame 10 | Hasta 3 tiros en el frame 10 |
| Juego perfecto | 12 strikes consecutivos | 300 puntos (máximo posible) |

### Responsabilidades de cada clase

- **`BowlingGame`**: motor principal del juego. Recibe los tiros (`roll()`), valida su legalidad (rango de pines, suma máxima por frame, estado del juego), agrupa los tiros en frames y determina cuándo el juego está completo (`isComplete()`). Expone `score()`, que delega el cálculo real a `BowlingScorer`.
- **`Frame`**: representa un frame individual con sus tiros (hasta 3 en el caso del frame 10). Sabe si es strike, si es spare, y expone el total de pines derribados en ese frame.
- **`BowlingScorer`**: clase sin estado que recibe la lista de frames ya jugados y calcula el puntaje total, aplicando los bonos de spare y strike, incluyendo el caso especial del último frame (donde no se aplican bonos hacia adelante porque no existe un frame siguiente).

## 3. Evidencia TDD

El desarrollo completo (Módulos A, B y C) siguió el ciclo RED → GREEN → REFACTOR, documentado en el historial de commits de cada Pull Request (ver sección 6).

### Ejemplo de ciclo completo — Módulo A, caso A1 (`roll(0)`)

**RED** — Se escribió el test esperando que `roll(0)` no lanzara excepción y que el frame registrara 0 pinos. Al ejecutar `mvn test` contra el esqueleto inicial (con `roll()` vacío y una lista de frames sin inicializar), el test falló con `IndexOutOfBoundsException: Index 0 out of bounds for length 0` — porque `roll()` nunca agregaba nada a la lista de frames.

```
[ERROR] Errors:
[ERROR]   BowlingGameTest.rollZeroPins_doesNotThrow:14 » ArrayIndexOutOfBounds Index 0 out of bounds for length 0
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
[INFO] BUILD FAILURE
```

**GREEN** — Se implementó lo mínimo: `Frame` recibe y guarda el valor real de pines en su constructor, y `roll()` agrega un `Frame(pins)` a la lista. El test pasó:

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

**REFACTOR** — Al llegar al caso A4 (dos tiros que suman más de 10), se detectó que el diseño de "un `Frame` por cada `roll()`" no permitía representar dos tiros dentro de un mismo frame. Se refactorizó `Frame` para acumular `firstRoll`/`secondRoll` (y más adelante `thirdRoll` para el frame 10), sin cambiar el comportamiento observable de los tests ya existentes (A1-A3), que siguieron pasando tras el cambio.

Este ciclo, y los sucesivos (Módulos A, B y C), están documentados commit por commit en el historial de Git, con mensajes `test: RED - ...`, `feat: GREEN - ...` y `refactor: ...`.

## 4. JaCoCo

**Comando ejecutado:** `mvn clean verify`

**Resultado:**

```
[INFO] --- jacoco:0.8.15:check (check) @ bowling-tdd ---
[INFO] All coverage checks have been met.
[INFO] BUILD SUCCESS
```

**Cobertura final:**

| Métrica | Cobertura | Umbral requerido |
|---|---|---|
| Instrucciones | 98% | — |
| Líneas | 98% | ≥ 85% |
| Branches | 92% | ≥ 70% |

![Cobertura JaCoCo](evidence/jacoco-final.png)

*Nota: la cobertura se construyó de forma incremental junto con cada ciclo TDD de los Módulos A, B y C, en vez de agregarse al final sobre una implementación ya completa. Por eso no existe una captura "antes" representativa: cada test se escribió antes que su implementación correspondiente, y la cobertura creció caso por caso.*

## 5. SonarQube

**Estado: no ejecutado.**

Se intentó levantar SonarQube localmente con Docker según lo indicado en el taller. Al verificar el entorno, se encontró que ni Docker Desktop ni WSL2 estaban instalados en la máquina de desarrollo. Instalar WSL2 requiere reiniciar el sistema operativo, y dado el tiempo disponible antes de la entrega, se priorizó completar y verificar el código funcional (Módulos A, B, C), la cobertura de JaCoCo (98%) y esta documentación, en vez de arriesgar tiempo en una instalación con reinicio de por medio.

Queda pendiente como mejora futura: instalar Docker Desktop (con soporte WSL2), levantar la imagen `sonarqube:26.9.0.129388-community`, y ejecutar `mvn clean verify sonar:sonar` para obtener el dashboard de Quality Gate.

## 6. Pull Requests

| # | Enlace | Módulo que cubre |
|---|---|---|
| #1 | https://github.com/Sebastian-Granados1456/DOSW-Taller2-Bowling-Granados-Sebastian/pull/1 | Configuración inicial del proyecto (Maven, JUnit 5, JaCoCo, SonarQube plugin) |
| #2 | https://github.com/Sebastian-Granados1456/DOSW-Taller2-Bowling-Granados-Sebastian/pull/2 | Módulo A — `BowlingGame.roll()` (validaciones y estado, casos A1-A8) |
| #3 | https://github.com/Sebastian-Granados1456/DOSW-Taller2-Bowling-Granados-Sebastian/pull/3 | Módulo B — `BowlingScorer.calculate()` (bonos de spare/strike, casos B1-B8) |
| #4 | https://github.com/Sebastian-Granados1456/DOSW-Taller2-Bowling-Granados-Sebastian/pull/4 | Módulo C — `BowlingGame.isComplete()` (casos de borde del frame 10, C1-C6) |

## 7. Reflexión técnica

**1. ¿Qué caso de borde del Bowling fue el más difícil de implementar con TDD y por qué?**

El frame 10 con strike o spare (casos A8, B7 y C4-C6). Es el único frame que rompe la regla general de "máximo 2 tiros por frame", y detectar cuándo ese frame está realmente completo (2 tiros normales, o 3 si hubo strike/spare) requirió una lógica separada del resto del juego. El caso B7 (juego perfecto = 300) expuso un bug real: el cálculo de bono de strike asumía que siempre existía un "frame siguiente" externo para tomar el bono, pero en el último frame los strikes adicionales están *dentro* del mismo `Frame` (como segundo y tercer tiro), no en un frame nuevo. El primer intento de `BowlingScorer.calculate()` daba 310 en vez de 300 porque aplicaba el bono del frame 10 hacia un frame inexistente además de contar sus propios pines.

**2. ¿Qué parte del código cambió durante REFACTOR sin modificar el comportamiento observable?**

El diseño interno de `Frame` cambió completamente entre el caso A3 y el caso A7: pasó de ser una clase con un único valor `pins` fijo por instancia (una instancia de `Frame` por cada llamada a `roll()`) a una clase que acumula `firstRoll`, `secondRoll` y `thirdRoll` dentro de la misma instancia. Este cambio fue necesario porque el diseño inicial no podía representar un spare (dos tiros que pertenecen al mismo frame). El comportamiento observable de los tests A1-A3 no cambió: seguían esperando lo mismo (validaciones de rango, no lanzar excepciones con valores válidos), solo cambió cómo se construía internamente el objeto `Frame`.

**3. ¿Qué casos de prueba descubriste al revisar el reporte de cobertura de JaCoCo que no habías considerado antes?**

El reporte de JaCoCo mostró un 92% de cobertura de branches (ligeramente por debajo del 98% de líneas), señalando ramas condicionales no cubiertas — en particular, combinaciones menos comunes de la lógica del frame 10 (por ejemplo, un spare en el frame 10 donde el tiro de bono por sí solo no es un strike). Esto confirmó que, aunque los 22 tests explícitos cubrían los casos exigidos por el taller, había caminos de código (branches del `if`/`else`) que solo se ejercitan con combinaciones específicas de entradas que no todos los tests explícitos activaban directamente.

**4. ¿Qué hallazgo de SonarQube produjo un cambio real en el código?**

No aplica: el análisis de SonarQube no pudo ejecutarse en este entorno por falta de Docker/WSL2 instalados, según se documenta en la sección 5.

## Cómo ejecutar el proyecto

```bash
# Compilar
mvn clean package

# Ejecutar pruebas
mvn test

# Ejecutar pruebas + verificar cobertura (falla si < 85%)
mvn clean verify

# Ver reporte de cobertura
# Abrir target/site/jacoco/index.html en el navegador
```
