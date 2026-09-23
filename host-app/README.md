# Host Application

Angular host application for the Performance Testing Demo.

The host application provides the login page and navigation and dynamically loads the Regulatory Reporting micro frontend using Module Federation.

---

## Technology Stack

* Angular 19
* TypeScript
* Node.js 20+
* npm
* Webpack
* Module Federation

---

## Architecture

```text
Browser
   |
   v
Host App :4200
   |
   | Module Federation
   v
Report MFE :4300
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

The application runs on:

```text
http://localhost:4200
```

---

# Login

Local demonstration credentials:

```text
Username: testuser
Password: password
```

---

# Application Flow

```text
Host Application
       |
       v
Login
       |
       v
Navigation
       |
       v
Regulatory Reports
       |
       | Module Federation
       v
Report MFE
```

---

# Module Federation

The host consumes the remote:

```text
report-mfe
```

Remote entry:

```text
http://localhost:4300/remoteEntry.js
```

The host dynamically loads:

```text
./ReportComponent
```

from the remote.

---

# Local Setup

The Report MFE must be running before selecting Regulatory Reports.

Start:

```text
report-mfe → http://localhost:4300
```

Then start:

```text
host-app → http://localhost:4200
```

---

# Troubleshooting

### MFE does not load

Verify:

```text
http://localhost:4300/remoteEntry.js
```

is accessible.

If it returns 404, start the Report MFE.

### Port 4200 already in use

Start Angular on another port:

```bash
ng serve --port 4201
```

However, the default Module Federation setup expects the host on port 4200.

---

# Development

Main areas:

```text
src/app/
├── login/
├── navigation/
├── app.component.ts
├── app.component.html
└── mfe-loader.service.ts
```

The host is responsible for:

* Login
* Navigation
* Loading the remote MFE
* Hosting the remote component
