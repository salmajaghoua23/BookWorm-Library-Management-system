package lk.ijse.bookWormLibraryManagementSystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppInitializer extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        loadScene("/view/userSignInGlobalForm.fxml");
        primaryStage.show();
    }

    public static void loadScene(String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                AppInitializer.class.getResource(fxmlPath)
        );
        Parent root = loader.load();

        double width  = root.prefWidth(-1);
        double height = root.prefHeight(-1);

        // ✅ reset maximized AVANT de changer la scène
        primaryStage.setMaximized(false);

        primaryStage.setScene(new Scene(root, width, height));

        // ✅ forcer la taille explicitement
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.centerOnScreen();
    }
}