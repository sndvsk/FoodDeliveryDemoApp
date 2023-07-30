#!/bin/bash

# Define your PostgreSQL service host
db_host="postgres_db"

# Define your database name
database_name="fd_db"

# Define your PostgreSQL username
postgres_username="postgres"

# Array of SQL files
sql_files=("01_owner_data.sql" "02_restaurant_data.sql")

# Run each SQL file
for sql_file in "${sql_files[@]}"; do
    echo "Processing $sql_file file..."
    PGPASSWORD=123 psql -h $db_host -U $postgres_username -d $database_name -f sql/"$sql_file"
done

echo "All done!"
