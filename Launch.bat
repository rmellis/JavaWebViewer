@echo off
java --module-path lib --add-modules javafx.controls,javafx.web -jar viewer.jar "index.htm" "Java WebApp" 1200 760 false false false center center icon.png