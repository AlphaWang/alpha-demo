#!/bin/bash

# usage: sh validate.sh $PWD/output 

rootPath=$1
nginxVersion=1.13  # Could also be passed as an argument using $2

result=$(docker run --rm -t -a stdout --name my-nginx -v ${rootPath}/:/etc/nginx/:ro nginx:$nginxVersion nginx -c /etc/nginx/nginx.conf -t)

# Look for the word successful and count the lines that have it
# This validation could be improved if needed
successful=$(echo $result | grep successful | wc -l)

if [ $successful = 0 ]; then
    echo FAILED
    echo "$result"
    exit 1
else
    echo SUCCESS
fi