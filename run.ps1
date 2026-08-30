[CmdletBinding()]
param(
    [string]$LogDirectory = "logs"
)

$ErrorActionPreference = "Stop"
$projectDirectory = $PSScriptRoot
$resolvedLogDirectory = Join-Path $projectDirectory $LogDirectory

Push-Location $projectDirectory
try {
    & "$projectDirectory\mvnw.cmd" --quiet package
    if ($LASTEXITCODE -ne 0) {
        throw "Maven build failed with exit code $LASTEXITCODE."
    }

    New-Item -ItemType Directory -Path $resolvedLogDirectory -Force | Out-Null
    & java "-DLOG_DIR=$resolvedLogDirectory" -jar "$projectDirectory\target\java-repo-indexer-fixture.jar"
    if ($LASTEXITCODE -ne 0) {
        throw "The sample application failed with exit code $LASTEXITCODE."
    }

    Write-Host "Logs written to $resolvedLogDirectory"
}
finally {
    Pop-Location
}
