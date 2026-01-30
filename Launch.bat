@echo off
java --module-path lib --add-modules javafx.controls,javafx.web -jar viewer.jar "index.htm" --title="Java WebApp" --width=1200 --height=760 false flase --icon=icon.png