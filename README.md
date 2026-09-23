# Performance Testing Demo

A hands-on performance testing demonstration project using **Angular, Module Federation, Spring Boot, MySQL, Apache JMeter and Maven**.

The project simulates an enterprise reporting application where a host application loads a Regulatory Reporting micro frontend. The micro frontend communicates with a Spring Boot report service, which retrieves report data from MySQL.

The project is designed to demonstrate how an application can be built, tested, and performance-tested locally using JMeter.

---

## Architecture

```text
                         Browser
                            |
                            v
                 +---------------------+
                 |     Host App        |
                 |    Angular :4200    |
                 +----------+----------+
                            |
                            | Module Federation
                            v
                 +---------------------+
                 |    Report MFE       |
                 |    Angular :4300    |
                 +----------+----------+
                            |
                            | REST API
                            v
                 +---------------------+
                 |   Report Service    |
                 | Spring Boot :8081   |
                 +----------+----------+
                            |
                            | JPA/JDBC
                            v
                 +---------------------+
                 |       MySQL         |
                 |       :3306         |
                 +---------------------+

                 Performance Testing
                         |
                         v
                 +---------------------+
                 |      Apache JMeter  |
                 |      Maven Plugin   |
                 +---------------------+
```

---

## Projects

| Project             | Technology                  | Port | Purpose                                  |
| ------------------- | --------------------------- | ---: | ---------------------------------------- |
| `host-app`          | Angular                     | 4200 | Main host application                    |
| `report-mfe`        | Angular + Module Federation | 4300 | Regulatory Reporting micro frontend      |
| `report-service`    | Spring Boot + JPA           | 8081 | Report REST API                          |
| MySQL               | MySQL 8.4                   | 3306 | Report database                          |
| `performance-tests` | JMeter + Maven              |    - | Performance test execution and reporting |

---

## Application Flow

The application simulates the following user journey:

```text
Open Host Application
        |
        v
Login
        |
        v
Regulatory Reports
        |
        v
Load Report Micro Frontend
        |
        v
Select Report
        |
        v
Select Reporting Date
        |
        v
Load Report
        |
        v
Spring Boot REST API
        |
        v
MySQL
```

The initial login credentials are:

```text
Username: testuser
Password: password
```

These credentials are for local demonstration purposes only.

---

# Prerequisites

Install the following software:

* Java 17
* Maven 3.9+
* Node.js 20+
* npm
* Angular CLI 19
* Docker Desktop
* Apache JMeter 5.6+

Verify:

```bash
java -version
mvn -version
node -version
npm -version
ng version
docker --version
```

---

# Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd performance-testing-demo
```

---

# 1. Start MySQL

Navigate to the report service:

```bash
cd report-service
```

Start MySQL:

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

You should see:

```text
performance-mysql
```

MySQL configuration:

```text
Host: localhost
Port: 3306
Database: performance_db
Username: perf_user
Password: perf_password
```

---

# 2. Start Report Service

Open a new terminal:

```bash
cd performance-testing-demo/report-service
```

Run:

```bash
mvn spring-boot:run
```

The service starts on:

```text
http://localhost:8081
```

Health check:

```text
http://localhost:8081/actuator/health
```

Reports API:

```text
http://localhost:8081/api/reports
```

Paginated report data:

```text
http://localhost:8081/api/reports/1/data/page?startDate=2026-01-01&endDate=2026-12-31&page=0&size=50
```

---

# 3. Start Report Micro Frontend

Open another terminal:

```bash
cd performance-testing-demo/report-mfe
```

Install dependencies:

```bash
npm install
```

Start:

```bash
npm start
```

The MFE runs on:

```text
http://localhost:4300
```

Module Federation remote entry:

```text
http://localhost:4300/remoteEntry.js
```

---

# 4. Start Host Application

Open another terminal:

```bash
cd performance-testing-demo/host-app
```

Install dependencies:

```bash
npm install
```

Start:

```bash
npm start
```

The host application runs on:

```text
http://localhost:4200
```

Open:

```text
http://localhost:4200
```

Login using:

```text
Username: testuser
Password: password
```

Then:

```text
Regulatory Reports
```

The host loads the Report MFE using Module Federation.

---

# 5. Performance Test Project

The `performance-tests` project contains the JMeter test plan.

```text
performance-tests/
│
├── pom.xml
│
└── src/
    └── test/
        └── resources/
            └── jmeter/
                ├── report-api-load.jmx
                └── test-data.csv
```

The JMeter test currently tests:

```text
GET /api/reports

GET /api/reports/{reportId}/data/page
```

Example:

```text
GET /api/reports/1/data/page
    ?startDate=2026-01-01
    &endDate=2026-12-31
    &page=0
    &size=50
```

---

# 6. Run JMeter from Maven

Navigate to:

```bash
cd performance-testing-demo/performance-tests
```

Run:

```bash
mvn clean verify
```

The Maven JMeter plugin executes the `.jmx` test plan.

The generated result files and HTML report depend on the configured JMeter Maven Plugin output directories.

After execution, inspect:

```text
performance-tests/target/
```

For example:

```bash
dir /s target
```

or on Linux/macOS:

```bash
find target -type f
```

Look for:

```text
.jtl
index.html
```

The `.jtl` file contains raw JMeter results.

The generated `index.html` is the JMeter HTML dashboard.

---

# JMeter Test Flow

The current JMeter test represents:

```text
Thread Group
    |
    +-- HTTP Request Defaults
    |
    +-- CSV Data Set Config
    |
    +-- Get Reports
    |
    +-- Get Report Data
    |
    +-- Assertions / Results
```

Example workload:

```text
10 virtual users
10 second ramp-up
1 iteration
```

---

# Test Data

The JMeter test uses CSV-driven parameters.

Example:

```csv
1,2026-01-01,2026-03-31,0,50
2,2026-01-01,2026-06-30,0,50
3,2026-01-01,2026-09-30,0,50
```

The variables are:

```text
reportId
startDate
endDate
page
size
```

This allows different virtual users to execute different report queries.

---

# Performance Test Scenarios

The project can be extended with the following scenarios:

| Test     |          Users | Purpose                                   |
| -------- | -------------: | ----------------------------------------- |
| Smoke    |            1-5 | Verify test and environment               |
| Baseline |             10 | Establish normal performance              |
| Load     |             50 | Normal expected workload                  |
| Load     |            100 | Higher expected workload                  |
| Stress   |            250 | Determine system behavior under high load |
| Stress   |           500+ | Identify saturation point                 |
| Spike    | Rapid increase | Test sudden traffic                       |
| Soak     |  Long duration | Detect degradation over time              |

---

# Important Performance Metrics

The JMeter report should be used to analyze:

### Response Time

Average, minimum and maximum response time.

### Percentiles

Especially:

```text
P90
P95
P99
```

### Throughput

Requests processed per second.

### Error Rate

Percentage of failed requests.

### Server Metrics

During larger tests, monitor:

```text
Spring Boot JVM
CPU
Heap
GC
Thread count

MySQL
CPU
Memory
Connections
Query execution
Locks
```

---

# Important Note

JMeter is primarily being used here for **HTTP/API performance testing**.

It does not reproduce browser rendering performance.

The architecture is:

```text
Browser
   |
   +-- Angular UI
   |
   +-- Module Federation
   |
   +-- REST APIs
```

JMeter focuses on:

```text
REST API
   ↓
Spring Boot
   ↓
MySQL
```

Browser rendering and frontend performance can be measured separately using tools such as Lighthouse or browser-based performance testing tools.

---

# Future Enhancements

Planned improvements include:

* Increase database dataset size
* Add realistic pagination
* Add authentication/token handling
* Add dynamic test data
* Add response assertions
* Parameterize virtual users
* Parameterize ramp-up
* Parameterize test duration
* Add smoke/load/stress/soak profiles
* Add HTML report publishing
* Add Jenkins pipeline
* Add CI/CD execution
* Monitor JVM metrics
* Monitor MySQL metrics
* Add Apache Superset dashboard
* Compare API performance across builds

---

# Repository Structure

```text
performance-testing-demo/
│
├── README.md
│
├── report-service/
│   ├── pom.xml
│   ├── docker-compose.yml
│   └── src/
│
├── host-app/
│   ├── package.json
│   └── src/
│
├── report-mfe/
│   ├── package.json
│   ├── webpack.config.js
│   └── src/
│
└── performance-tests/
    ├── pom.xml
    └── src/
        └── test/
            └── resources/
                └── jmeter/
                    ├── report-api-load.jmx
                    └── test-data.csv
```

---

# Local Startup Order

For a clean local startup:

```text
1. Docker / MySQL
       ↓
2. report-service
       ↓
3. report-mfe
       ↓
4. host-app
       ↓
5. performance-tests
```

Commands:

```bash
# Terminal 1
cd report-service
docker compose up -d

# Terminal 2
cd report-service
mvn spring-boot:run

# Terminal 3
cd report-mfe
npm install
npm start

# Terminal 4
cd host-app
npm install
npm start

# Terminal 5
cd performance-tests
mvn clean verify
```

---

## Purpose of the Project

This repository is intended as a practical demonstration of:

* Micro frontend architecture
* REST API development
* Database-backed reporting
* Module Federation
* Apache JMeter
* Performance test automation
* Maven-based JMeter execution
* HTML performance reporting
* CI/CD-ready performance testing
