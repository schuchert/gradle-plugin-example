#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"

pushd "$SCRIPT_DIR/.." > /dev/null
./gradlew -q example:greeting --console=plain
popd > /dev/null