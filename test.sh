#!/usr/bin/env bash
set -euo pipefail

# if the previous commands in the `script` section of .travis.yaml failed, then abort.
# the variable is not set in early stages of the build, so we default to 0 there.
# see: https://docs.travis-ci.com/user/environment-variables/
if [[ ${TRAVIS_TEST_RESULT=0} == 1 ]]; then
  exit 1;
fi

pushd ./example

./gradlew --version
./gradlew clean build
./gradlew :app:lint
./gradlew :app:build
./gradlew build connectedCheck

popd