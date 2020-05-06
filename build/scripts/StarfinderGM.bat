@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  StarfinderGM startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and STARFINDER_GM_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\StarfinderGM-0.0.2.jar;%APP_HOME%\lib\nekohtml-1.9.22.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\jsoup-1.13.1.jar;%APP_HOME%\lib\javax.faces-api-2.1.jar;%APP_HOME%\lib\cdi-api-2.0.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\persistence-api-1.0.2.jar;%APP_HOME%\lib\ejb-api-3.0.jar;%APP_HOME%\lib\primefaces-6.1.jar;%APP_HOME%\lib\javax.servlet-api-4.0.0.jar;%APP_HOME%\lib\javax.mail-api-1.6.0.jar;%APP_HOME%\lib\jsf-api-2.1.jar;%APP_HOME%\lib\javafx-fxml-14-win.jar;%APP_HOME%\lib\javafx-controls-14-win.jar;%APP_HOME%\lib\javafx-controls-14.jar;%APP_HOME%\lib\javafx-graphics-14-win.jar;%APP_HOME%\lib\javafx-graphics-14.jar;%APP_HOME%\lib\javafx-base-14-win.jar;%APP_HOME%\lib\javafx-base-14.jar;%APP_HOME%\lib\xercesImpl-2.11.0.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\javax.el-api-3.0.0.jar;%APP_HOME%\lib\javax.interceptor-api-1.2.jar;%APP_HOME%\lib\commons-io-1.3.2.jar;%APP_HOME%\lib\xml-apis-1.4.01.jar

@rem Execute StarfinderGM
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %STARFINDER_GM_OPTS%  -classpath "%CLASSPATH%" main/java/Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable STARFINDER_GM_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%STARFINDER_GM_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
