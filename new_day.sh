#!/bin/bash

# Check if the environment variable is set
if [ -z "${DAY}" ]; then
    echo "Error: Environment variable DAY is not set"
    exit 1
else
    echo "Generating for day $DAY"
fi

export DAY_STRING=$(printf "%02d" $DAY)

SRC_PATH="./app/src/main/kotlin/aoc2024/d${DAY_STRING}.kt"
TEST_PATH="./app/src/test/kotlin/aoc2024/d${DAY_STRING}.kt"
RESOURCES_PATH="./app/src/test/resources/d${DAY_STRING}"

cat ./templates/day.kt | envsubst > "$SRC_PATH"
echo "Source file generated at $SRC_PATH"

cat ./templates/test.kt | envsubst > "$TEST_PATH"
echo "Test file generated at $TEST_PATH"

mkdir $RESOURCES_PATH
touch ${RESOURCES_PATH}/example.txt
touch ${RESOURCES_PATH}/input.txt