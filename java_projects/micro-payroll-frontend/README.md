# Micro Payroll frontend

A responsive HTML, CSS, and JavaScript UI for [joyel10-arch/java_micro_payroll](https://github.com/joyel10-arch/java_micro_payroll/tree/main), with a Java HTTP adapter that calls the original `Employee`, `PayrollCalculator`, and `IncomeTaxCalculator` classes.

## Start with Java (recommended)

Install a JDK 17 or newer, open a terminal in this folder, and run:

```sh
bash run.sh
```

Open http://localhost:8080. The header should say **Java backend connected**. No Node installation, Maven, database, or npm dependencies are needed to run the app. Use `bash run.sh 8081` for a different port. Stop with Ctrl+C.

On Windows Command Prompt:

```bat
mkdir .build
javac --add-modules jdk.httpserver -d .build java\*.java
java --add-modules jdk.httpserver -cp .build PayrollWebServer 8080
```

To add to your existing repository, copy this folder into a `frontend/` directory and start it there. The three original calculation classes are included unchanged; update those copies if the original Java logic changes. `PayrollWebServer.java` is the new adapter. This delivery does not push changes to GitHub.

## Features

- Overview with employee count, gross payroll, deductions, net payroll, and breakdown chart.
- Add, edit, delete, and search employees.
- Import the original 10-column employee CSV. Duplicate IDs and invalid rows are skipped with a count.
- Review individual payslips and download text payslips.
- Export all employees with calculated payroll totals to CSV.
- Choose a period for payslip and export labels.
- Browser local storage persists employee records across reloads.
- Responsive navigation and layouts; wide data tables scroll horizontally on small screens.

The three valid sample employees from the repository are loaded initially. Importing the original sample CSV skips the duplicate employee IDs and the invalid numeric row.

## Architecture and boundaries

The source repository is a command-line Java application, with no existing REST API. This project adds:

- `GET /api/health`: `{ "engine": "java" }`.
- `POST /api/calculate`: URL-encoded employee fields; returns gross, PF, professional tax, annual income, taxable income, annual tax, monthly TDS, and net salary.
- Same-origin static file serving from `dist/`.

The Java adapter binds only to your own computer (127.0.0.1). Employee records are held in the browser, not a database or the original CSV file. A calculation request sends the current employee inputs to the local Java adapter. No external services receive employee records.

The hosted private preview serves only `dist/`. It uses equivalent browser calculations and displays **Browser demo**. It does not run Java. To use the original Java classes, run the local adapter above.

The period selector labels output; it does not maintain historical payroll runs. There is no login, shared database, payment execution, or payment tracking.

## Payroll rules

The implementation preserves the repository's illustrative calculations:

- Gross = basic + HRA + DA + conveyance.
- PF = 12% of basic; professional tax = ₹200 per month.
- Taxable annual income = max(0, gross × 12 − min(80C investment, ₹150,000)).
- Illustrative annual tax: 0 through ₹300,000; 5% on the next ₹300,000; 10% on the next ₹300,000; 15% above ₹900,000.
- Monthly TDS = annual tax / 12; net = gross − PF − professional tax − TDS.
- Working days and overtime are recorded but do not change salary, matching the original Java logic.

These are project demo rules, not a claim about current statutory tax rules. Amounts round only for display. Calculations keep the original Java double behavior.

## CSV input

```csv
employeeId,name,designation,basicPay,hra,da,conveyance,workingDays,overtimeHours,investment80C
E101,Arun,Developer,30000,10000,3000,2000,24,5,50000
```

Use the supplied `employees-sample.csv` for a clean starting file. Amounts must be nonnegative finite numbers; working days must be an integer from 0 to 31; overtime must be a nonnegative integer. Quoted commas and UTF-8 BOMs are supported. Export summaries include calculated columns and spreadsheet-formula protection, and are intended as reports rather than import files.

## Checks

With Node 18+ installed:

```sh
npm test
```

Verified: Java compilation and sample API calculation; browser Java connection, employee save, search and payslip preview/download; four automated tests covering sample calculation, slab boundaries, investment cap, validation, and CSV parsing.

The UI uses optional Google Fonts with local sans-serif fallbacks. There are no third-party JavaScript dependencies.
