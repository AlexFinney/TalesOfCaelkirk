@echo off

cd ..

rd /s /q "run\mods"
mkdir "run\mods"
copy "external_deps\worldedit-forge-mc1.12.2-6.1.10.jar" "run\mods\worldedit-forge-mc1.12.2-6.1.10.jar"

gradlew setupDecompWorkspace

cd scripts
