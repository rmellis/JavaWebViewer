import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LocalViewer extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        List<String> params = getParameters().getRaw();

        // ARG 1: URL (index 0)
        String url = params.isEmpty() ? Paths.get("index.htm").toUri().toString() : params.get(0);
        if (!url.startsWith("http") && !url.startsWith("file://")) {
            url = Paths.get(url).toAbsolutePath().toUri().toString();
        }

        // ARG 2: Title (index 1)
        String title = params.size() > 1 ? params.get(1) : "Local HTML Viewer";

        // ARG 3-4: Width/Height (index 2,3)
        double width = safeParseDouble(safeGetString(params, 2, "1200"), 1200);
        double height = safeParseDouble(safeGetString(params, 3, "800"), 800);

        // ARG 5-8: Flags (index 4-7)
        boolean fullscreen = safeGetBoolean(params, 4, false);
        boolean maximized = safeGetBoolean(params, 5, false);
        boolean resizable = !safeGetBoolean(params, 6, false);
        boolean alwaysOnTop = safeGetBoolean(params, 7, false);

        // ARG 9-10: X/Y (index 8,9) - skip if icon present
        double x = -1, y = -1;
        int iconIndex = params.size() > 9 ? 9 : -1;  // Icon at index 9 (10th arg)
        
        if (params.size() > 8 && iconIndex < 0) x = safeParseDouble(params.get(8), -1);
        if (params.size() > 9 && iconIndex < 0) y = safeParseDouble(params.get(9), -1);

        // ICON - FIXED: Check index 9 (10th argument)
        String iconArg = params.size() > 9 ? params.get(9) : null;
        System.out.println("Icon arg at index 9: '" + iconArg + "'");

        // Load scene first
        webView.getEngine().load(url);
        Scene scene = new Scene(webView, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setAlwaysOnTop(alwaysOnTop);

        // ICON - Load & apply AFTER scene
        if (iconArg != null && !iconArg.trim().isEmpty()) {
            try {
                String iconUrl = Paths.get(iconArg).toAbsolutePath().toUri().toString();
                System.out.println("Loading icon from: " + iconUrl);
                
                Image icon = new Image(iconUrl);
                System.out.println("Icon loaded - Error: " + icon.isError() + ", Width: " + icon.getWidth());
                
                if (!icon.isError() && icon.getWidth() > 0) {
                    stage.getIcons().clear();
                    stage.getIcons().add(icon);
                    System.out.println("✅ ICON SUCCESSFULLY SET!");
                } else {
                    System.out.println("❌ Icon invalid (error or zero size)");
                }
            } catch (Exception e) {
                System.out.println("❌ Icon exception: " + e.getMessage());
            }
        }

        // Position & show
        if (fullscreen) stage.setFullScreen(true);
        else if (maximized) stage.setMaximized(true);
        else {
            stage.setWidth(width);
            stage.setHeight(height);
            if (x >= 0 && y >= 0) {
                stage.setX(x);
                stage.setY(y);
            } else {
                centerWindow(stage);
            }
        }

        stage.show();
    }

    private static String safeGetString(List<String> params, int index, String defaultValue) {
        return params.size() > index ? params.get(index) : defaultValue;
    }

    private static boolean safeGetBoolean(List<String> params, int index, boolean defaultValue) {
        return params.size() > index && params.get(index).equalsIgnoreCase("true");
    }

    private static double safeParseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
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
