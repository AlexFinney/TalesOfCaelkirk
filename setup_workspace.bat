@echo off

rd /s /q "run/mods"
mkdir "run/mods"
REM copy "external_deps\worldedit-forge-mc1.12-6.1.8-dist.jar" "run\mods\worldedit-forge-mc1.12-6.1.8-dist.jar"

gradlew setupDecompWorkspace

