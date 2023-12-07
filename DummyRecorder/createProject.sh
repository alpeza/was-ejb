#!/bin/bash

generate(){
  moduleName="$1"
  archetype="$2"

  archetypeGroupId="$(echo "$archetype" | awk -F ":" '{ print $1 }' )"
  archetypeArtifactId="$(echo "$archetype" | awk -F ":" '{ print $2 }' )"
  archetypeVersion="$(echo "$archetype" | awk -F ":" '{ print $3 }' )"

  mvn archetype:generate \
      -DgroupId="com.$(echo "$moduleName" | sed 's/-/./g')" \
      -DartifactId="$moduleName" \
      -DarchetypeGroupId="$archetypeGroupId" \
      -DarchetypeArtifactId="$archetypeArtifactId" \
      -DarchetypeVersion="$archetypeVersion" \
      -Dversion="1.0.0-SNAPSHOT" \
      -DinteractiveMode=false
}

generateProject(){
  projectName="$1"
  echo "Generando el proyecto"
  generate "$projectName" "org.codehaus.mojo.archetypes:pom-root:1.1"
  cd "$projectName" || exit
  echo "Generando el EJB"
  generate "$projectName-ejb" "org.codehaus.mojo.archetypes:ejb-javaee6:1.5"
  echo "Generando el EAR"
  generate "$projectName-ear" "org.codehaus.mojo.archetypes:ear-javaee6:1.5"
}

if [ -z "$1" ]; then
    echo "[ ERROR ]: Se ha de proporcionar un nombre de proyecto."
    exit 1
fi
projectName="$1"
if [ -d "$projectName" ]; then
    echo "[ ERROR ]: El proyecto [ $projectName ] ya existe."
    exit 1
fi

echo "Generando proyecto: $projectName"
rootDir="$(pwd)"
# 1.- Generamos los proyectos como arqutipos de maven:
generateProject "$projectName"
