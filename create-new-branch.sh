#!/bin/bash

# Verifica si se han pasado los argumentos necesarios
if [ "$#" -ne 2 ]; then
    echo "Usage: ./create-branch.sh <type> <name>"
    echo "Example: ./create-branch.sh feature add-categories"
    exit 1
fi

# Tipo y nombre
BRANCH_TYPE=$1
BRANCH_NAME=$2

# Crear y cambiar a la nueva rama
git checkout -b backend/$BRANCH_TYPE/$BRANCH_NAME

# Imprimir el nombre de la rama creada
echo "Switched to branch 'backend/$BRANCH_TYPE/$BRANCH_NAME'"
