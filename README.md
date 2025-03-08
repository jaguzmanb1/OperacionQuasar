# **Misión Rebelde: Decodificación de Mensaje de Auxilio**

## **Transmisión Prioritaria de la Alianza Rebelde**

**General Solo,**

El Imperio Galáctico ha bajado la guardia y tenemos una oportunidad única para debilitar sus fuerzas.
Nuestros espías han interceptado una señal de auxilio proveniente de una nave portacarga imperial varada en un campo de asteroides. Los rumores indican que transporta suministros vitales y armamento para una legión entera.

El servicio de inteligencia rebelde ha triangulado la señal con nuestros satélites de comunicación, pero la interferencia del campo de asteroides ha fragmentado el mensaje. Nuestra misión es clara: **reconstruir la transmisión y determinar la ubicación exacta de la nave antes de que el Imperio lo haga.**

---



## **Protocolos de Comunicación**

### **1. Procesamiento del Mensaje Capturado**
**POST /topsecret**

📡 *Envíanos la información capturada por los satélites para reconstruir la transmisión.*

**📥 Datos esperados:**
```json
{
  "satellites": [
    { "name": "kenobi", "distance": 100.0, "message": ["", "este", "", "mensaje"] },
    { "name": "skywalker", "distance": 115.5, "message": ["este", "", "un", ""] },
    { "name": "sato", "distance": 142.7, "message": ["", "", "", "es", "secreto"] }
  ]
}
```

**📤 Respuesta esperada:**
```json
{
  "position": { "x": -100.0, "y": 75.5 },
  "message": "este es un mensaje secreto"
}
```

⚠️ **Si la ubicación no puede ser triangulada, se devolverá un error 404:**
```json
{
  "error": "Error - Indeterminate position..."
}
```

---

### **2. Consulta de Satélites Disponibles**
**GET /satellites**

📡 *Verifica qué satélites están disponibles y su estado.*

**📤 Respuesta esperada:**
```json
[
  { "name": "kenobi", "position": { "x": -500, "y": -200 }, "distance": 100.0 },
  { "name": "skywalker", "position": { "x": 100, "y": -100 }, "distance": 115.5 }
]
```

---

### **3. Transmisión en Modo Acumulativo**
**POST /topsecret_split/{satellite_name}**

📡 *Carga información de un solo satélite a la vez, permitiendo reconstruir la transmisión de forma progresiva.*

🔸 **Parámetro de Ruta:** `satellite_name` *(kenobi, skywalker, sato)*

**📥 Datos esperados:**
```json
{
  "distance": 100.0,
  "message": ["este", "", "un", "", "mensaje"]
}
```

**📤 Respuesta esperada:**
```json
{
  "message": "OK"
}
```

---

### **4. Decodificación de Mensaje en Modo Acumulativo**
**GET /topsecret_split**

📡 *Procesa la información de los satélites ya registrados y devuelve el mensaje completo y la posición de la nave imperial.*

**📤 Respuesta esperada:**
```json
{
  "position": { "x": -100.0, "y": 75.5 },
  "message": "este es un mensaje secreto"
}
```

⚠️ **Si la ubicación no puede ser triangulada, se devolverá un error 404:**
```json
{
  "error": "Error - Indeterminate position..."
}
```

---

## **Recursos para la Resistencia**
📌 **Lenguaje:** Java 22  
📌 **Framework:** Spring Boot  
📌 **Base de Datos:** SQLite  
📌 **Gestor de Dependencias:** Maven

🔧 **Para desplegar la misión:**
```sh
mvn spring-boot:run
```
🔎 **El servicio estará disponible en:** `http://localhost:8080/`

---

## **Notas Estratégicas**
⚠️ **El mensaje de auxilio solo puede ser reconstruido si contamos con datos de al menos tres satélites.**  
⚠️ **Si falta información crítica, se notificará el error indicando qué datos no están disponibles.**  
⚠️ **El sistema valida que solo los satélites de la Resistencia (Kenobi, Skywalker y Sato) pueden ser utilizados.**

General Solo, el tiempo es crucial. ¡Decodifiquemos la señal antes de que el Imperio lo haga! 🚀

