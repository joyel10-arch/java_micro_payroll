#!/usr/bin/env bash
set -eu
cd "$(dirname "$0")"
mkdir -p .build
javac --add-modules jdk.httpserver -d .build java/*.java
exec java --add-modules jdk.httpserver -cp .build PayrollWebServer "${1:-8080}"
