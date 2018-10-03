#!/usr/bin/env bash
redis-sentinel conf/sentinel-26379.conf &
redis-sentinel conf/sentinel-26380.conf &
redis-sentinel conf/sentinel-26381.conf &