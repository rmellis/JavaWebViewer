import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.nio.file.Paths;
import java.util.*;

public class LocalViewer extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        List<String> params = getParameters().getRaw();
        Map<String, String> args = parseHybridArgs(params);

        // DEBUG: Configuration summary
        System.out.println("🚀 LocalViewer Configuration:");
        System.out.println("   📄 URL: " + args.getOrDefault("url", "index.htm"));
        System.out.println("   🏷️  Title: " + args.getOrDefault("title", "Local HTML Viewer"));
        System.out.println("   📐 Size: " + args.getOrDefault("width", "1200") + "x" + args.getOrDefault("height", "800"));
        System.out.println("   🔧 Flags: fullscreen=" + args.getOrDefault("fullscreen", "false") + 
                          ", maximized=" + args.getOrDefault("maximized", "false"));

        // Parse arguments (hybrid positional + named flags)
        String url = args.getOrDefault("url", args.getOrDefault("", Paths.get("index.htm").toUri().toString()));
        if (!url.startsWith("http") && !url.startsWith("file://")) {
            url = Paths.get(url).toAbsolutePath().toUri().toString();
        }

        String title = args.getOrDefault("title", "Local HTML Viewer");
        double width = parseDouble(args.getOrDefault("width", "1200"), 1200);
        double height = parseDouble(args.getOrDefault("height", "800"), 800);
        boolean fullscreen = parseBoolean(args.getOrDefault("fullscreen", "false"));
        boolean maximized = parseBoolean(args.getOrDefault("maximized", "false"));
        boolean resizable = !parseBoolean(args.getOrDefault("resizable", "false"));
        boolean alwaysOnTop = parseBoolean(args.getOrDefault("alwaysontop", "false"));
        double x = parseDouble(args.getOrDefault("x", "-1"), -1);
        double y = parseDouble(args.getOrDefault("y", "-1"), -1);
        String icon = args.get("icon");

        // Load content
        webView.getEngine().load(url);
        Scene scene = new Scene(webView, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setAlwaysOnTop(alwaysOnTop);

        // ICON with debug feedback
        if (icon != null && !icon.trim().isEmpty()) {
            try {
                String iconUrl = Paths.get(icon).toAbsolutePath().toUri().toString();
                System.out.println("   🖼️  Loading icon: " + icon);
                
                Image img = new Image(iconUrl);
                if (!img.isError() && img.getWidth() > 0) {
                    stage.getIcons().clear();
                    stage.getIcons().add(img);
                    System.out.println("   ✅ Icon loaded successfully (" + img.getWidth() + "x" + img.getHeight() + ")");
                } else {
                    System.out.println("   ❌ Icon failed to load (error=" + img.isError() + ")");
                }
            } catch (Exception e) {
                System.out.println("   ❌ Icon error: " + e.getMessage());
            }
        } else {
            System.out.println("   ℹ️  No icon specified");
        }

        // Position window
        if (fullscreen) {
            stage.setFullScreen(true);
            System.out.println("   📱 Fullscreen mode enabled");
        } else if (maximized) {
            stage.setMaximized(true);
            System.out.println("   🔳 Maximized mode enabled");
        } else if (x >= 0 || y >= 0) {
            stage.setWidth(width);
            stage.setHeight(height);
            if (x >= 0) stage.setX(x);
            if (y >= 0) stage.setY(y);
            System.out.println("   📍 Position: " + (x >= 0 ? x : "center") + "," + (y >= 0 ? y : "center"));
        } else {
            centerWindow(stage);
            System.out.println("   🎯 Window centered");
        }

        System.out.println("✨ Viewer ready! Press Ctrl+C to exit.");
        stage.show();
    }

    /** Hybrid parsing: positional first, then --key=value flags */
    private Map<String, String> parseHybridArgs(List<String> args) {
        Map<String, String> result = new LinkedHashMap<>();
        
        // Positional args (backward compatible order)
        String[] positionalKeys = {"", "title", "width", "height", "fullscreen", 
                                  "maximized", "resizable", "alwaysontop", "x", "y"};
        
        for (int i = 0; i < Math.min(args.size(), positionalKeys.length); i++) {
            result.put(positionalKeys[i], args.get(i));
        }
        
        // Named flags (--key=value) override positional
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                String key = parts[0].toLowerCase();
                String value = parts.length > 1 ? parts[1] : "true";
                result.put(key, value);
            }
        }
        
        return result;
    }

    private static boolean parseBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "1".equals(value);
    }

    private static double parseDouble(String value, double defaultValue) {
        try {
            return "center".equalsIgnoreCase(value) ? -1 : Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private void centerWindow(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
    }
}
