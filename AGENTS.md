# AGENTS.md

## 1. Purpose and General Rules

All code generated must be human maintainable.

Write readable, maintainable, reliable code. Prefer the simplest solution that meets the requirements (KISS). Avoid over-engineering, clever tricks, duplication, dead code, and speculative generality. Follow SOLID where appropriate and keep methods focused.

### Project Specification and Decision Rules

- Treat `SPECS.md` and explicit user decisions as the source of truth for project requirements. `README.md` is project-facing documentation and is not the canonical specification unless the user explicitly says otherwise.
- Read and follow `SPECS.md` before making implementation decisions that depend on project requirements.
- If specifications conflict, follow the most recent explicit user decision. If the conflict cannot be resolved clearly, stop and ask the user.
- Implement only the task explicitly assigned by the user.
- Do not invent or change business rules, architecture, technology choices, interfaces, or database designs without approval.
- If requirements are unclear, contradictory, incomplete, or require an architectural decision, stop and ask the user before proceeding.
- Do not perform unrelated refactoring or introduce additional features.
- Before making changes, inspect the relevant existing code, configuration and tests to understand the current implementation.
- Follow the project's established architecture and conventions.
- Do not introduce new frameworks, libraries, services or infrastructure without approval.

### TOGAFmate Technology Stack

The approved technology stack is:

- **Frontend:** Angular with TypeScript.
- **Backend:** Java.
- **Database:** PostgreSQL.
- **Build tool:** Gradle with Kotlin DSL.

Rules:

- Use PostgreSQL for database development and persistence.
- Use Gradle with Kotlin DSL for Java builds.
- Use Angular and TypeScript for frontend development.
- Do not introduce Quarkus or another backend framework unless explicitly approved by the user.
- Do not replace an approved technology with an alternative without approval.
- Inspect the existing project configuration before adding dependencies or changing build configuration.

### Security Requirements

- Validate input at appropriate application boundaries, especially REST API endpoints.
- Enforce authentication and authorisation in the backend; never rely on frontend controls alone for security.
- Never hardcode, expose, or commit passwords, tokens, secrets, or sensitive configuration.
- Do not log secrets, credentials, or sensitive personal or business data.
- Follow the security requirements in `SPECS.md`.
- If a security requirement is unclear or requires a design decision, ask the user before proceeding.

## 2. Java Development and Testing

### Java Coding Standard

- Use meaningful names: classes `PascalCase`, methods and variables `camelCase`, constants `UPPER_SNAKE_CASE`, packages lowercase.
- Use generics, try-with-resources, enhanced `for` loops, and `StringBuilder` where appropriate.
- Use K&R brace style.
- Document all public classes and methods with Javadoc; do not add Javadoc to private members. Avoid explanatory comments; make the code self-documenting.
- Prefer specific exceptions over error codes. Never swallow exceptions.
- Log errors with appropriate context before throwing exceptions.
- **Successful-execution logging:** Write an appropriate INFO log after successful application-level method execution, especially in service and controller methods that return values. Identify the successful operation and include relevant, non-sensitive context, such as a business identifier or result count, when useful.
- Log success only after the operation has completed successfully. Do not log success before the operation finishes or when it fails.
- Do not log private methods, getters, or setters. Avoid redundant logging at multiple layers for the same operation.
- Never log passwords, authentication tokens, secrets, or sensitive personal or business data.
- Use parameterised logging rather than string concatenation when supported by the logging framework.
- Avoid long methods/classes, long parameter lists, duplicate code, and switch statements where polymorphism is suitable.

### Java Unit Tests and Coverage

- Use JUnit 5 and AssertJ. Structure tests as Arrange / Act / Assert.
- Name tests `should_[expected_outcome]_when_[condition]`.
- Follow Red–Green–Refactor: write one failing test, implement the minimum to pass, then refactor only while tests remain green.
- Run tests after every change. Keep tests fast, independent, repeatable, self-validating, and timely.
- Use Mockito for unit tests where dependencies need to be mocked, and use JaCoCo to measure coverage.
- Aim for 100% applicable line and branch coverage where practical. Add positive, negative, and exception-path tests.
- Do not weaken or remove tests merely to improve coverage. Do not add meaningless tests solely to increase coverage.
- Test external failure cases, including invalid inputs, empty responses, HTTP errors, and exceptions.
- Do not leave tests commented out or ignored.

## 3. Angular Development and Testing

### Angular Coding Standard

- Use TypeScript with strict typing; avoid `any` unless there is a justified reason.
- Follow Angular conventions for components, services, dependency injection, routing, and lifecycle hooks.
- Keep components focused; move reusable business logic and API access into services.
- Use reactive forms and RxJS patterns where appropriate. Clean up subscriptions and resources to prevent leaks.
- Use consistent `PascalCase` for types/classes and `camelCase` for properties and methods.
- Keep templates accessible and avoid complex business logic in templates.
- Follow the Angular version and conventions already established in the project. Do not upgrade Angular or add dependencies without approval.

### Angular Tests

- Unit-test components, services, pipes, validators, and important branches using the project's configured Angular testing framework.
- Mock HTTP and external dependencies. Test success, error, empty, and invalid-input cases.
- Run the project's test and coverage commands; report the actual results.
- Follow the project's configured coverage thresholds.

## 4. Test-Driven Development Rules

For new behaviour, use this cycle:

1. **Red:** Write one test and confirm it fails for the expected reason.
2. **Green:** Write the smallest implementation that passes.
3. **Refactor:** Improve design only after tests pass; keep them green.

Additional rules:

- Do not write production code before a failing test exists for new behaviour.
- Do not write multiple new tests at once.
- Do not add code "just in case".
- Do not refactor unrelated code during a task.
- If the existing project or test infrastructure prevents the prescribed cycle, stop and explain the blocker rather than silently bypassing TDD.
- A human reviewer commits after reviewing the changes.

## 5. Repository and Completion Rules

- Work on the existing feature branch. Do not create sub-branches.
- Do not run Git commands or commit changes. A human reviewer commits after review.
- Do not claim a test, build, coverage report, or scan passed unless it was run and the result was observed.
- Do not report unexecuted tests as passed or imply that coverage targets were met without evidence.

### Task Completion Checklist

Before reporting a task as complete:

1. Verify that the assigned task and its acceptance criteria have been addressed.
2. Review the changed code for correctness, security and consistency with the project architecture.
3. Run the relevant tests and checks that are available.
4. Review the actual test and build results.
5. Identify any incomplete work, blockers or decisions requiring user approval.

At completion, summarise:

- Changes made and files affected.
- Tests and checks executed, with actual results.
- Coverage results, when available.
- Outstanding issues, limitations, or decisions requiring user approval.

If a task cannot be completed safely or correctly because of missing requirements, dependencies, permissions, or environment problems, stop and report the blocker rather than guessing.
