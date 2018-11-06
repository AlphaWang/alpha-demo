#!/usr/bin/env bash

redis-cli -p 6379 shutdown
redis-cli -p 6380 shutdown
redis-cli -p 6381 shutdown
redis-cli -p 6382 shutdown