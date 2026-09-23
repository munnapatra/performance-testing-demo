# Report Service

Spring Boot REST service used by the Regulatory Reporting micro frontend.

The service provides report metadata and report data backed by MySQL.

---

## Technology Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL 8.4
* Maven
* Docker

---

## Architecture

```text
Report MFE
    |
    | REST
    v
Spring Boot
    |
    | JPA
    v
MySQL
```

---

## Prerequisites

Install:

* Java 17
* Maven 3.9+
* Docker Desktop

Verify:

```bash
java -version
mvn -version
docker --version
```

---

# Database

MySQL runs through Docker Compose.

Start MySQL:

```bash
docker compose up -d
```

Check:

```bash
docker ps
```

Expected container:

```text
performance-mysql
```

Database configuration:

```text
Host: localhost
Port: 3306
Database: performance_db
Username: perf_user
Password: perf_password
```

---

# Run the Service

From the project directory:

```bash
mvn spring-boot:run
```

Application:

```text
http://localhost:8081
```

Health endpoint:

```text
http://localhost:8081/actuator/health
```

---

# APIs

## Get Reports

```http
GET /api/reports
```

Example:

```text
http://localhost:8081/api/reports
```

Returns the available reports.

---

## Get Report

```http
GET /api/reports/{reportId}
```

Example:

```text
GET /api/reports/1
```

---

## Get Report Data

```http
GET /api/reports/{reportId}/data
```

Example:

```text
GET /api/reports/1/data
```

---

## Get Filtered Report Data

```http
GET /api/reports/{reportId}/data/filter
```

Parameters:

```text
startDate
endDate
```

Example:

```text
GET /api/reports/1/data/filter?startDate=2026-01-01&endDate=2026-03-31
```

---

## Get Paginated Report Data

```http
GET /api/reports/{reportId}/data/page
```

Parameters:

```text
startDate
endDate
page
size
```

Example:

```text
GET /api/reports/1/data/page?startDate=2026-01-01&endDate=2026-12-31&page=0&size=50
```

---

# Database Tables

The application creates tables using JPA/Hibernate.

Main tables:

```text
reports
report_data
```

`reports` contains report metadata.

`report_data` contains reporting records.

---

# Test Data

The application initializer creates sample reports and report data.

Example reports:

```text
Unadjusted Report
Adjusted Report
Liquidity Report
```

Report data contains:

```text
Report ID
Reporting Date
Business Unit
Currency
Amount
Status
```

---

# Performance Dataset

For performance testing, the project can generate a larger dataset.

The target performance-test dataset is:

```text
1,000,000 report_data records
```

Before running a large dataset generation, make sure MySQL has sufficient resources.

---

# Database Index

The report-data pagination query benefits from:

```sql
CREATE INDEX idx_report_date
ON report_data(report_id, reporting_date);
```

Check indexes:

```sql
SHOW INDEX FROM report_data;
```

---

# Configuration

Application properties:

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/performance_db
spring.datasource.username=perf_user
spring.datasource.password=perf_password

spring.jpa.hibernate.ddl-auto=update
```

---

# Verify

After starting the service:

```text
http://localhost:8081/api/reports
```

and:

```text
http://localhost:8081/api/reports/1/data/page?startDate=2026-01-01&endDate=2026-12-31&page=0&size=50
```

Both endpoints should return HTTP 200.

---

# Troubleshooting

### MySQL connection failure

Check:

```bash
docker ps
```

Make sure `performance-mysql` is running.

Test MySQL:

```bash
docker exec -it performance-mysql mysql -u perf_user -p
```

Password:

```text
perf_password
```

### Port 8081 already in use

Find the process using port 8081 and stop it, or change:

```properties
server.port=8081
```

---

# Performance Testing

The service is the primary backend system under test.

The JMeter project is located in the sibling:

```text
performance-tests/
```

It executes requests such as:

```text
GET /api/reports

GET /api/reports/{reportId}/data/page
```

The service should be running before executing JMeter tests.
