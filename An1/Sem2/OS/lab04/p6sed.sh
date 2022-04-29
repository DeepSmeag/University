#!/bin/bash
sed -E 's/([^:]*):([^:]*):([^:]*)/\1:\3:\2/' /etc/passwd
