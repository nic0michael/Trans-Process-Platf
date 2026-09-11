# Backend Build, JaCoCo and SonarQube

## 1. JaCoCo Configuration

The project uses **JaCoCo** to measure unit-test coverage.

The JaCoCo plugin is applied to each Java backend module:

```groovy
apply plugin: 'jacoco'
```

The modules are:

```text
backend/api
backend/processor
backend/common
```

Each module follows the standard Gradle structure:

```text
src/
├── main/
│   └── java/
│
└── test/
    └── java/
```

For example:

```text
backend/api/src/main/java/com/nm/tranproc/
backend/api/src/test/java/com/nm/tranproc/
```

---

## 2. Running Unit Tests and JaCoCo

Run all unit tests from the project root:

```bash
./gradlew test
```

The test task is configured to generate the JaCoCo report after the tests complete.

JaCoCo HTML reports are generated under:

```text
backend/api/build/reports/jacoco/test/html/index.html
backend/processor/build/reports/jacoco/test/html/index.html
backend/common/build/reports/jacoco/test/html/index.html
```

The HTML report can be opened in a browser to inspect:

* Classes
* Methods
* Lines
* Branches
* Coverage percentages

---

## 3. JaCoCo Coverage Gate

The project uses JaCoCo to enforce minimum test coverage.

Current targets are:

```text
Line coverage:   80%
Branch coverage: 70%
```

The Gradle build verifies these limits using:

```text
jacocoTestCoverageVerification
```

The `check` task depends on the coverage verification:

```bash
./gradlew check
```

The expected build flow is:

```text
Compile
   ↓
Unit Tests
   ↓
JaCoCo Report
   ↓
Coverage Verification
   ↓
BUILD PASSED
```

If the required coverage is not achieved:

```text
BUILD FAILED
```

This is intentional.

The project is using **test coverage as a quality gate**, rather than simply generating a coverage report.

---

## 4. SonarQube

The project uses the SonarQube Gradle plugin:

```groovy
id 'org.sonarqube' version '7.3.1.8318'
```

The SonarQube project is:

```text
Project Key:  trans-process-platf
Project Name: Trans-Process-Platf
```

The SonarQube server URL defaults to:

```text
http://localhost:9000
```

This can be overridden using:

```text
SONAR_HOST_URL
```

For example:

```text
SONAR_HOST_URL=http://192.168.1.100:9000
```

The SonarQube authentication token should **not** be stored in `build.gradle`.

It should be supplied through the environment when required.

---

## 5. Running SonarQube Analysis

From the project root:

```bash
./gradlew sonar
```

The analysis will inspect the Gradle project and send the results to the configured SonarQube server.

The project combines:

```text
Java source
    +
Unit tests
    +
JaCoCo coverage
    +
SonarQube analysis
```

This gives us both automated test coverage and static-code analysis.

---

## 6. Development Quality Flow

The intended development workflow is:

```text
Write requirement
      ↓
Write test
      ↓
Write implementation
      ↓
Run unit tests
      ↓
Generate JaCoCo report
      ↓
Check coverage
      ↓
Run SonarQube
      ↓
Review quality
```

The objective is to practice **TDD and continuous code quality** throughout the project.

We should not reduce the coverage requirement simply to make the build pass.

If coverage fails, the preferred solution is to identify the missing test cases and improve the tests.

---

## 7. Useful Gradle Commands

Run all tests:

```bash
./gradlew test
```

Run the complete verification:

```bash
./gradlew check
```

Generate JaCoCo reports:

```bash
./gradlew jacocoTestReport
```

Run SonarQube:

```bash
./gradlew sonar
```

Run the complete quality process:

```bash
./gradlew check sonar
```

---

## 8. Backend Module Structure

The backend currently consists of three Gradle modules:

```text
backend/
├── api/
│   └── src/
│       ├── main/
│       │   └── java/
│       │       └── com/nm/tranproc/
│       └── test/
│           └── java/
│               └── com/nm/tranproc/
│
├── processor/
│   └── src/
│       ├── main/
│       │   └── java/
│       │       └── com/nm/tranproc/
│       └── test/
│           └── java/
│               └── com/nm/tranproc/
│
└── common/
    └── src/
        ├── main/
        │   └── java/
        │       └── com/nm/tranproc/
        └── test/
            └── java/
                └── com/nm/tranproc/
```

Tests are therefore kept with the module they test.

There is no separate `backend/test` module.

---

## 9. Quality Objective

The backend should eventually provide a repeatable quality pipeline:

```text
              ┌──────────────┐
              │  Source Code │
              └──────┬───────┘
                     ↓
              ┌──────────────┐
              │ Unit Tests   │
              └──────┬───────┘
                     ↓
              ┌──────────────┐
              │    JaCoCo    │
              └──────┬───────┘
                     ↓
              ┌──────────────┐
              │ Coverage Gate│
              └──────┬───────┘
                     ↓
              ┌──────────────┐
              │  SonarQube   │
              └──────┬───────┘
                     ↓
              ┌──────────────┐
              │Quality Review│
              └──────────────┘
```

This forms part of the project's overall engineering practice:

* **TDD**
* **Automated unit testing**
* **JaCoCo coverage**
* **Coverage gates**
* **SonarQube static analysis**
* **Repeatable Gradle builds**
* **Quality checks before deployment**

