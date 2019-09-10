@echo off

set "home=%~dp0"

IF EXIST "%appdata%\.minecraft\saves\TOCSpawn" (
cd "%appdata%\.minecraft\saves\TOCSpawn"
DEL /F/Q/S *.* > NUL
)

IF EXIST "%appdata%\.minecraft\saves\TOCMainland" (
cd "%appdata%\.minecraft\saves\TOCMainland"
DEL /F/Q/S *.* > NUL
)

xcopy "%home%\..\run\saves\TOCSpawn" "%appdata%\.minecraft\saves\TOCSpawn" /O /X /E /H /K
xcopy "%home%\..\run\saves\TOCMainland" "%appdata%\.minecraft\saves\TOCMainland" /O /X /E /H /K


PAUSE