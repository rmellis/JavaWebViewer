# Java Web Viewer (viewer.jar)

Portable Java WebView app for local HTML files and web pages via http / https with useful window customization

Quick Start
-----------

    java --module-path lib --add-modules javafx.controls,javafx.web -jar viewer.jar index.htm --title="Java WebApp" --width=1200 --height=760 false flase --icon=icon.png

Opens index.htm from current directory (1200x760 window)

Command Line Arguments
----------------------
The command line arguments can work either by using direct key=value flags or by position (index) as shown below. Here are some examples using both hybrid mode and index-based mode.

    viewer.jar [url] --title="example app" --width=640 --height=480 false false --icon=icon.png

You need to use quotation marks if a key value has more than one word. Otherwise, the space will split it into separate keys and cause a syntax error.

Another example:

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

Console Feedback 
--------------

Since the early releases, console logging has improved based on your configuration. Below is an example of how the console looks when you launch the app with your custom flags.

🚀 LocalViewer Configuration:<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📄 URL: index.htm<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;🏷️ Title: Java WebApp<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📐 Size: auto x auto<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;🔧 Flags: fullscreen=false, maximized=false<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;🖼️ Loading icon: icon.png<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✅ Icon loaded successfully (64x64)<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;🎯 Window centered<br>
   ✨ Viewer ready! Press Ctrl+C to exit.

Distribution Bundle
-------------------
JavaWebViewer.zip<br>
├── viewer.jar<br>
├── index.htm<br>
├── javafx/<br>
├── Launch.bat<br>
├── icon.png<br>
└── Launch-FullScreen.bat

Launch.bat:
@echo off
java --module-path lib --add-modules javafx.controls,javafx.web -jar viewer.jar index.htm --title="Java WebApp" --width=1200 --height=760 false flase --icon=icon.png

Requirements
------------
Java: 21+ (Temurin recommended)<br>
JavaFX: 21+ (pre-included, no need to download)<br>
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