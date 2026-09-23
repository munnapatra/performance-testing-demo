# Report Micro Frontend

Angular micro frontend responsible for the Regulatory Reporting UI.

The application is exposed through Webpack Module Federation and consumed by the host application.

---

## Technology Stack

* Angular 19
* TypeScript
* Node.js 20+
* npm
* Webpack
* Module Federation
* Angular HttpClient

---

## Architecture

```text
Host Application :4200
        |
        | Module Federation
        v
Report MFE :4300
        |
        | REST
        v
Report Service :8081
        |
        v
MySQL :3306
```

---

## Prerequisites

Install:

* Node.js 20+
* npm
* Angular CLI 19

Verify:

```bash
node -version
npm -version
ng version
```

---

# Installation

From this directory:

```bash
npm install
```

---

# Run Locally

```bash
npm start
```

The micro frontend runs on:

```text
http://localhost:4300
```

---

# Module Federation

The remote is named:

```text
report-mfe
```

Remote entry:

```text
http://localhost:4300/remoteEntry.js
```

The exposed component is:

```text
./ReportComponent
```

The host consumes this component dynamically.

---

# Verify Module Federation

Open:

```text
http://localhost:4300/remoteEntry.js
```

You should see JavaScript content rather than a 404 response.

---

# Report UI

The Report MFE provides:

* Report Type selection
* Reporting Date
* Load Report button
* Report data table
* Report data summary

Available reports include:

```text
Unadjusted Report
Adjusted Report
Liquidity Report
```

---

# Backend API

The MFE communicates with:

```text
http://localhost:8081
```

Main APIs:

```http
GET /api/reports
```

and:

```http
GET /api/reports/{reportId}/data/page
```

Example:

```text
http://localhost:8081/api/reports/1/data/page?startDate=2026-01-01&endDate=2026-12-31&page=0&size=50
```

---

# CORS

The Report Service allows requests from:

```text
http://localhost:4200
http://localhost:4300
```

If the browser reports a CORS error, verify that the Spring Boot service is running and that the configured origins match the local application URLs.

---

# Local Startup

Start the backend first:

```text
report-service :8081
```

Then:

```text
report-mfe :4300
```

Then:

```text
host-app :4200
```

Open:

```text
http://localhost:4200
```

Login and select:

```text
Regulatory Reports
```

The host should dynamically load this MFE.

---

# Development

Main files:

```text
src/app/
├── report/
│   ├── report.component.ts
│   ├── report.component.html
│   └── report.component.css
│
└── services/
    └── report-api.service.ts
```

Federation configuration:

```text
webpack.config.js
webpack.prod.config.js
```

---

# Troubleshooting

### `remoteEntry.js` not found

Verify the MFE is running:

```bash
npm start
```

Then open:

```text
http://localhost:4300/remoteEntry.js
```

### `No provider for HttpClient`

The host application dynamically creates the federated component. Ensure the host application provides:

```typescript
provideHttpClient()
```

in its `app.config.ts`.

### API connection failure

Verify:

```text
http://localhost:8081/api/reports
```

is accessible directly.
