#!/bin/bash

sudo mkdir -p /opt/stacks
echo "/opt/stacks done"

sudo cp -r dockage /opt/stacks/
echo "copied dockage"

sudo cp -r kafka /opt/stacks/
echo "copied kafka"

sudo cp -r postgres /opt/stacks/

echo "Docker stack folders copied to /opt/stacks/"
ls -la /opt/stacks/
