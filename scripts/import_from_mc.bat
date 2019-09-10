@echo off

set "home=%~dp0"

IF EXIST "%home%\..\run\saves\TOCSpawn" (
cd "%home%\..\run\saves\TOCSpawn"
DEL /F/Q/S *.* > NUL
)

IF EXIST "%home%\..\run\saves\TOCMainland" (
cd "%home%\..\run\saves\TOCMainland"
DEL /F/Q/S *.* > NUL
)

xcopy "%appdata%\.minecraft\saves\TOCSpawn" "%home%\..\run\saves\TOCSpawn" /O /X /E /H /K
xcopy "%appdata%\.minecraft\saves\TOCMainland" "%home%\..\run\saves\TOCMainland" /O /X /E /H /K


PAUSE