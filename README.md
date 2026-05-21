# 🏥 Hospital Appointment System

A complete backend REST API application for managing hospital appointments built with **Spring Boot**. This system allows patients, doctors, and appointments to be managed efficiently with features like overlapping appointment prevention.

---

## 📋 Table of Contents

- [Technologies Used](#technologies-used)
- [Features](#features)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Sample API Requests](#sample-api-requests)
- [Business Logic](#business-logic)
- [Team Members](#team-members)
- [Postman Collection](#postman-collection)

---

## 🛠 Technologies Used

| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | 3.2.5 |
| Spring Web | 3.2.5 |
| Spring Validation | 3.2.5 |
| MySQL | 8.0+ |
| Maven | 3.8+ |
| Postman | (Testing) |

---

## ✨ Features

### Core Features
- ✅ **Patient Management** - Create, Read, Update, Delete patients
- ✅ **Doctor Management** - Create, Read, Update, Delete doctors
- ✅ **Appointment Booking** - Schedule appointments between patients and doctors
- ✅ **Overlap Prevention** - Prevents double-booking of doctors
- ✅ **Validation** - Request validation using Jakarta Validation

### Bonus Features
- ✅ Filter appointments by **Doctor ID**
- ✅ Filter appointments by **Date**
- ✅ Filter appointments by **Doctor ID and Date** (combined)
- ✅ Global Exception Handling

### Entity Relationships
- ✅ `@OneToMany` - Patient → Appointments
- ✅ `@OneToMany` - Doctor → Appointments
- ✅ `@ManyToOne` - Appointment → Patient, Appointment → Doctor

---

## 📁 Project Structure
```
com.example.hospital_appointment_system/
├── controller/
│ ├── PatientController.java
│ ├── DoctorController.java
│ └── AppointmentController.java
├── service/
│ ├── PatientService.java
│ ├── PatientServiceImpl.java
│ ├── DoctorService.java
│ ├── DoctorServiceImpl.java
│ ├── AppointmentService.java
│ └── AppointmentServiceImpl.java
├── repository/
│ ├── PatientRepository.java
│ ├── DoctorRepository.java
│ └── AppointmentRepository.java
├── entity/
│ ├── Patient.java
│ ├── Doctor.java
│ ├── Appointment.java
│ └── AppointmentStatus.java
├── dto/
│ ├── patient/
│ │ ├── PatientRequestDTO.java
│ │ └── PatientResponseDTO.java
│ ├── doctor/
│ │ ├── DoctorRequestDTO.java
│ │ └── DoctorResponseDTO.java
│ └── appointment/
│ ├── AppointmentRequestDTO.java
│ └── AppointmentResponseDTO.java
├── exception/
│ ├── ResourceNotFoundException.java
│ ├── DuplicateResourceException.java
│ ├── AppointmentConflictException.java
│ └── GlobalExceptionHandler.java
├── payload/
│ └── ApiResponse.java
└── HospitalAppointmentSystemApplication.java

```

---

## 🚀 Setup and Installation

### Prerequisites

- Java 17 or higher
- MySQL Server 8.0+
- Maven
- Postman (for testing)

### Step 1: Clone the Repository

```bash
git clone https://github.com/your-team/hospital-appointment-system.git
cd hospital-appointment-system
```
### Step 2: Configure Database
Create a MySQL database:
```
sql
CREATE DATABASE hospital_db;
Step 3: Update application.properties
properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```
Step 4: Build and Run
```
bash
mvn clean install
mvn spring-boot:run
```
Or run directly from IntelliJ IDEA.

Step 5: Verify Application is Running
```
Started HospitalAppointmentSystemApplication in X seconds
```
🗄 Database Configuration
```
Property	Value
Database Name	hospital_db
URL	jdbc:mysql://localhost:3306/hospital_db
Username	root
Password	your_password
DDL Auto	update (creates tables automatically)
```
📡 API Endpoints
Patient APIs
Method	Endpoint	Description
```
POST	/api/patients	Create new patient
GET	/api/patients	Get all patients
GET	/api/patients/{id}	Get patient by ID
PUT	/api/patients/{id}	Update patient
DELETE	/api/patients/{id}	Delete patient
```
Doctor APIs
Method	Endpoint	Description
```
POST	/api/doctors	Create new doctor
GET	/api/doctors	Get all doctors
GET	/api/doctors/{id}	Get doctor by ID
PUT	/api/doctors/{id}	Update doctor
DELETE	/api/doctors/{id}	Delete doctor
```
Appointment APIs
Method	Endpoint	Description
```
POST	/api/appointments	Book appointment
GET	/api/appointments	Get all appointments
GET	/api/appointments/{id}	Get appointment by ID
PUT	/api/appointments/{id}	Update appointment
DELETE	/api/appointments/{id}	Delete appointment
GET	/api/appointments/doctor/{doctorId}	Filter by doctor
GET	/api/appointments/date/{date}	Filter by date
GET	/api/appointments/doctor/{doctorId}/date/{date}	Filter by doctor & date
```
📝 Sample API Requests
Create Patient
```
POST http://localhost:8080/api/patients
```
```
json
{
    "firstName": "Nawodya",
    "lastName": "Rashmina",
    "email": "nawodya@example.com",
    "phoneNumber": "0771234567",
    "dateOfBirth": "2003-05-15",
    "gender": "Male"
}
```
Response:
```
json
{
    "id": 1,
    "firstName": "Nawodya",
    "lastName": "Rashmina",
    "email": "nawodya@example.com",
    "phoneNumber": "0771234567",
    "dateOfBirth": "2003-05-15",
    "gender": "Male"
}
```
Create Doctor
```
POST http://localhost:8080/api/doctors
```
```
json
{
    "firstName": "Kamal",
    "lastName": "Perera",
    "specialization": "Cardiology",
    "email": "kamal@example.com",
    "phoneNumber": "0711234567"
}
```
Response:
```
json
{
    "id": 1,
    "firstName": "Kamal",
    "lastName": "Perera",
    "specialization": "Cardiology",
    "email": "kamal@example.com",
    "phoneNumber": "0711234567"
}
```
Book Appointment
```
POST http://localhost:8080/api/appointments
```
```
json
{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-05-25",
    "startTime": "10:00:00",
    "endTime": "10:30:00",
    "notes": "Chest pain consultation"
}
```
Response:

```
json
{
    "id": 1,
    "patientId": 1,
    "patientName": "Nawodya Rashmina",
    "doctorId": 1,
    "doctorName": "Kamal Perera",
    "doctorSpecialization": "Cardiology",
    "appointmentDate": "2026-05-25",
    "startTime": "10:00:00",
    "endTime": "10:30:00",
    "status": "SCHEDULED",
    "notes": "Chest pain consultation"
}
```
Get Appointments by Doctor
```
GET http://localhost:8080/api/appointments/doctor/1
```

Get Appointments by Date
```
GET http://localhost:8080/api/appointments/date/2026-05-25
```

🧠 Business Logic
Overlap Prevention Logic
The system prevents overlapping appointments for the same doctor using this rule:

Two appointments overlap if:
newStart < existingEnd AND newEnd > existingStart

Example:
Scenario	Result
Existing: 10:00-11:00, New: 10:30-11:30	❌ Rejected (Overlap)
Existing: 10:00-11:00, New: 11:00-12:00	✅ Allowed (Exactly ends when next starts)

🧪 Error Responses
Resource Not Found (404)
```
json
{
    "timestamp": "2026-05-21T10:00:00",
    "status": 404,
    "message": "Patient not found with id: 99",
    "data": null
}
```
Appointment Conflict (409)
```
json
{
    "timestamp": "2026-05-21T10:00:00",
    "status": 409,
    "message": "Appointment overlaps with existing appointment: 10:00 to 10:30",
    "data": null
}
```
Validation Error (400)
```
json
{
    "timestamp": "2026-05-21T10:00:00",
    "status": 400,
    "message": "First name is required",
    "data": null
}
```
👥 Team Members
```
Member	    Responsibility
J A N Rashmina	Project Setup, Database Configuration, GitHub Management
M R Hanan	Patient Entity & Repository
N M Mahuroos	Patient DTO, Service & Controller
D M S A K Dissanayaka	Doctor Entity & Repository
W H N Umedya	Doctor DTO, Service & Controller
K T S U Senanayaka Appointment Entity & Repository
I D N V Lakpura	Appointment Service & Business Logic (Overlap Prevention)
M A C M Silva	Appointment Controller & API Response Wrapper
W A C Ramindu	Exception Handling, Validation, Postman Testing, Documentation
```
📞 Support
For any issues or questions, please contact the team members or refer to the project documentation.

Developed with ☕ and Spring Boot
