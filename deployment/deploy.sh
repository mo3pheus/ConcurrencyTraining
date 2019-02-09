#!/usr/bin/env bash

java -jar target/concurrency.training-1.0-SNAPSHOT-shaded.jar false src/main/resources/project.properties --messageFile src/main/resources/message.txt --offsetUpperBound 1000 --offsetLowerBound -1000
