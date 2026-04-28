package lk.ijse.bookWormLibraryManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookWormLibraryManagementSystem.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInGlobalFormController implements Initializable {

    @FXML
    public AnchorPane signInSignUpPane;

    private static SignInGlobalFormController controller;

    public SignInGlobalFormController() {
        controller = this;
    }

    public static SignInGlobalFormController getInstance() {
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Navigation.switchPaging(signInSignUpPane, "userSignInForm.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}