#!/bin/bash

result=$(echo "$1" | sed '2,$ s/^/    /')
echo $result

