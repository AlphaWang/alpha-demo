#!/usr/bin/env bash
# run this by `sh xx.sh`
export CART_HOME=cart.alphawang.com
export LOCAL_ASSET=assets
export S3_ASSET=s3.alphawang.com

sudo confd -onetime -config-file=confd/confd.toml

# validate conf file by `nginx -c FILE_PATH -t`