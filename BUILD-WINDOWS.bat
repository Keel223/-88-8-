@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"
title Legendary Frontiers - Build
if not exist "gradlew.bat" (
 echo ERROR: Extract the entire project ZIP first. Keep this file next to build.gradle and gradlew.bat.
 pause
 exit /b 1
)
if not exist "build.gradle" (
 echo ERROR: build.gradle is missing. Extract the entire project ZIP first.
 pause
 exit /b 1
)
if defined JAVA_HOME (
 if not exist "%JAVA_HOME%\bin\java.exe" (
  echo ERROR: JAVA_HOME does not point to a valid JDK. Install JDK 25 and fix JAVA_HOME.
  pause
  exit /b 1
 )
 "%JAVA_HOME%\bin\java.exe" -version
) else (
 where java >nul 2>&1
 if errorlevel 1 (
  echo ERROR: Java not found. Install JDK 25 and add it to PATH, then reopen this file.
  pause
  exit /b 1
 )
 java -version
)
echo.
echo This project requires JDK 25 and internet access.
echo First build downloads Gradle, Minecraft and Forge. It may take several minutes.
echo Full output will be saved in build-output.log.
echo.
call gradlew.bat --no-daemon --no-configuration-cache --console=plain --stacktrace --info --refresh-dependencies build > "build-output.log" 2>&1
set "BUILD_RESULT=%ERRORLEVEL%"
if not "%BUILD_RESULT%"=="0" (
 echo BUILD FAILED. Send build-output.log for troubleshooting.
 start "" notepad.exe "%CD%\build-output.log"
 pause
 exit /b %BUILD_RESULT%
)
if not exist "build\libs\*.jar" (
 echo Build completed but no JAR was found. Check build-output.log.
 start "" notepad.exe "%CD%\build-output.log"
 pause
 exit /b 1
)
echo BUILD SUCCESSFUL. Your JAR files are in build\libs.
start "" explorer.exe "%CD%\build\libs"
pause
exit /b 0
