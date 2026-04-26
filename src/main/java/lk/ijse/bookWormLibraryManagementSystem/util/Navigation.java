package lk.ijse.bookWormLibraryManagementSystem.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.transform.Scale;
import lk.ijse.bookWormLibraryManagementSystem.controller.admin.AdminGlobalFormController;
import lk.ijse.bookWormLibraryManagementSystem.controller.user.UserGlobalFormController;

import java.io.IOException;

public class Navigation {

    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Navigation.class.getResource("/view/" + path));

        Scene scene = new Scene(root);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        if (path.equals("adminGlobalForm.fxml") || path.equals("userGlobalForm.fxml")) {
            stage.setMaximized(true);
        } else {
            stage.setMaximized(false);
            stage.setWidth(1100);
            stage.setHeight(680);
            stage.centerOnScreen();
        }

        stage.show();
    }

    public static void switchPaging(Pane pane, String path) throws IOException {
        pane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path));
        Parent root = loader.load();

        root.setLayoutX(0);
        root.setLayoutY(0);

        double scaleValue = 1.0;

        // Dashboard admin seulement
        if (path.equals("adminDashboardForm.fxml") || path.equals("userGlobalForm.fxml")) {
            scaleValue = 0.80;
        }
        // Toutes les grandes pages admin/user, sauf popups et sign in
        else if (!path.contains("SignIn")) {
            scaleValue = 0.75;
        }

        if (scaleValue != 1.0) {
            Scale scale = new Scale(scaleValue, scaleValue, 0, 0);
            root.getTransforms().add(scale);
        }

        pane.getChildren().add(root);
    }

    public static void close(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void closePopUpPane() {
        AdminGlobalFormController.getInstance().popUpPane.getChildren().clear();
        AdminGlobalFormController.getInstance().popUpPane.setVisible(false);
        AdminGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserPopUpPane() {
        UserGlobalFormController.getInstance().popUpPane.getChildren().clear();
        UserGlobalFormController.getInstance().popUpPane.setVisible(false);
        UserGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closePopUpLargePane() {
        AdminGlobalFormController.getInstance().popUpLargePane.getChildren().clear();
        AdminGlobalFormController.getInstance().popUpLargePane.setVisible(false);
        AdminGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserPopUpLargePane() {
        UserGlobalFormController.getInstance().popUpLargePane.getChildren().clear();
        UserGlobalFormController.getInstance().popUpLargePane.setVisible(false);
        UserGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeAdminSettingsPane() {
        AdminGlobalFormController.getInstance().settingsPane.getChildren().clear();
        AdminGlobalFormController.getInstance().settingsPane.setVisible(false);
        AdminGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserSettingsPane() {
        UserGlobalFormController.getInstance().settingsPane.getChildren().clear();
        UserGlobalFormController.getInstance().settingsPane.setVisible(false);
        UserGlobalFormController.getInstance().imgTransparent.setVisible(false);
    }

    public static void imgPopUpBackground(String path) throws IOException {
        if (path.startsWith("user")) {
            UserGlobalFormController.getInstance().imgTransparent.setVisible(true);

            if (path.equals("userChangeCredentialsPopUpForm.fxml") | path.equals("userDeleteConfirmationForm.fxml")) {
                UserGlobalFormController.getInstance().popUpPane.setVisible(true);
                switchPaging(UserGlobalFormController.getInstance().popUpPane, path);
            } else if (path.equals("userSettingsPopUpForm.fxml")) {
                UserGlobalFormController.getInstance().settingsPane.setVisible(true);
                switchPaging(UserGlobalFormController.getInstance().settingsPane, path);
            } else {
                UserGlobalFormController.getInstance().popUpLargePane.setVisible(true);
                switchPaging(UserGlobalFormController.getInstance().popUpLargePane, path);
            }
        }
        else {
            AdminGlobalFormController.getInstance().imgTransparent.setVisible(true);

            if (path.equals("adminBorrowedBooksViewPopUpForm.fxml") | path.equals("adminOverdueBooksViewPopUpForm.fxml")) {
                AdminGlobalFormController.getInstance().popUpLargePane.setVisible(true);
                switchPaging(AdminGlobalFormController.getInstance().popUpLargePane, path);
            } else if (path.equals("adminSettingsPopUpForm.fxml")) {
                AdminGlobalFormController.getInstance().settingsPane.setVisible(true);
                switchPaging(AdminGlobalFormController.getInstance().settingsPane, path);
            } else {
                AdminGlobalFormController.getInstance().popUpPane.setVisible(true);
                switchPaging(AdminGlobalFormController.getInstance().popUpPane, path);
            }
        }
    }
}
