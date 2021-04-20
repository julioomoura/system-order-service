#!/bin/bash

docker cp schema.sql system-order-service-database/:.

docker exec -it system-order-service-database bash -c "mysql -proot -u root < schema.sql"