# Java Web Viewer (viewer.jar)

Portable Java WebView app for local HTML files and web pages via http / https with useful window customization

Quick Start
-----------
java --module-path javafx --add-modules javafx.controls,javafx.web -jar viewer.jar

Opens index.htm from current directory (1200x800 window)

Command Line Arguments
----------------------
viewer.jar "[url]" "[title]" [width] [height] [fullscreen] [maximized] [resizable] [alwaysontop] [x] [y] [icon]

1:  url              (URL/path)            Default: index.htm<br>
2:  title            (string)              Default: "Local HTML Viewer"<br>
3:  width            (px/auto)             Default: 1200<br>
4:  height           (px/auto)             Default: 800<br>
5:  fullscreen       (true/false)          Default: false<br>
6:  maximized        (true/false)          Default: false<br>
7:  resizable        (true/false)          Default: true<br>
8:  alwaysontop      (true/false)          Default: false<br>
9:  x                (px/center)           Default: center<br>
10: y                (px/center)           Default: center<br>
11: icon             (URL / path / auto)   Default: null<br>

Usage Examples
--------------
Basic:
viewer.jar "folder/index.html" "My WebApp"

Basic with icon:
viewer.jar "folder/index.html" "My WebApp" auto auto false false false center center icon.png

Kiosk Mode:
viewer.jar "kiosk/dashboard.html" "Dashboard" true true false

Fixed Debug Window:
viewer.jar "logs/debug.html" "Live Logs" 400 300 false false false true true 10 10

Distribution Bundle
-------------------
JavaWebViewer.zip
├── viewer.jar
├── index.htm
├── javafx/
├── Launch.bat
├── icon.png
└── Launch-FullScreen.bat

Launch.bat:
@echo off
java --module-path lib --add-modules javafx.controls,javafx.web -jar viewer.jar "index.htm" "Java WebApp" 1200 760 false false false center center icon.png

Requirements
------------
Java: 21+ (Temurin recommended)
JavaFX: 21+ (included)
OS: Windows 10/11, macOS, Linux

Note: this has only yet been tested on Windows so far, it may need additional libraries for Linux and MacOS but i'll look into that in the future.

File Paths
----------
Windows: C:/Users/You/site/index.htm
Linux:   /home/user/site/index.htm  
Relative: index.htm

Build from Source
-----------------
javac --add-modules javafx.controls,javafx.web --module-path javafx-sdk-21/lib LocalViewer.java

jar cfe viewer.jar LocalViewer *.class