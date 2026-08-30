# Java Repository Indexer Fixture

This repository is deliberately small but structurally realistic. It exists as input for applications that index Java source files, Maven dependencies, symbols, imports, relationships, and log lines.

## What it contains

- Java 17 source spread across `config`, `model`, `repository`, `service`, and `util` packages.
- Cross-file relationships through imports, interfaces, constructor injection, records, method calls, and exceptions.
- Public third-party libraries: Apache Commons Lang, Gson, SLF4J, and Logback.
- JUnit 5 tests as another dependency and source relationship.
- Logback configuration that writes TRACE, DEBUG, INFO, WARN, and ERROR events.

## Run it

From PowerShell:

```powershell
cd C:\Dev\Repos\java-repo-indexer-fixture
.\run.ps1
```

No global Maven installation is needed. The first invocation downloads Maven through the checked-in wrapper script and may take a little longer.

Generated files:

- `logs/application.log` receives every level from TRACE through ERROR.
- `logs/error.log` receives ERROR events only.

Both files append on each run, which is useful for repeatedly feeding new lines to a log indexer. To choose another output directory:

```powershell
.\run.ps1 -LogDirectory C:\temp\fixture-logs
```

## Build and test only

```powershell
.\mvnw.cmd test
```

The executable fat JAR is created at `target/java-repo-indexer-fixture.jar`.
