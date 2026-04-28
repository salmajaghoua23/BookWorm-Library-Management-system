package lk.ijse.bookWormLibraryManagementSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.bookWormLibraryManagementSystem.controller.admin.AdminSignInFormController;
import lk.ijse.bookWormLibraryManagementSystem.controller.user.UserSignInFormController;
import lk.ijse.bookWormLibraryManagementSystem.dto.AdminDto;
import lk.ijse.bookWormLibraryManagementSystem.dto.UserDto;
import lk.ijse.bookWormLibraryManagementSystem.service.ServiceFactory;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.AdminService;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.UserService;
import lk.ijse.bookWormLibraryManagementSystem.util.Navigation;
import lk.ijse.bookWormLibraryManagementSystem.util.RegExPatterns;

import java.io.IOException;

public class SignInFormController {

    @FXML
    private Label lblSignIn;

    @FXML
    private Label lblSignUp;

    @FXML
    private Label lblUsernameAlert;

    @FXML
    private Label lblPasswordAlert;

    @FXML
    private Pane signInPane;

    @FXML
    private Pane signUpPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private final AdminService adminService =
            (AdminService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.ADMIN);

    private final UserService userService =
            (UserService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.USER);

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {

        if (validateCredentials()) {

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (adminService.checkUsernameAndPassword(username, password)) {
                AdminSignInFormController.admin = adminService.getAdmin(username);
                Navigation.switchNavigation("adminGlobalForm.fxml", event);
                return;
            }

            if (userService.checkUsernameAndPassword(username, password)) {
                UserSignInFormController.user = userService.getUser(username);
                Navigation.switchNavigation("userGlobalForm.fxml", event);
                return;
            }

            lblPasswordAlert.setText("Incorrect Credentials!");
        }

        txtUsername.clear();
        txtPassword.clear();
    }

    private boolean validateCredentials() {

        boolean valid = true;

        lblUsernameAlert.setText(" ");
        lblPasswordAlert.setText(" ");

        if (RegExPatterns.namePattern(txtUsername.getText())) {
            lblUsernameAlert.setText("Invalid Username!");
            valid = false;
        }

        if (RegExPatterns.passwordPattern(txtPassword.getText())) {
            lblPasswordAlert.setText("Invalid Password!");
            valid = false;
        }

        return valid;
    }

    @FXML
    void txtUsernameOnKeyPressed(KeyEvent event) {
        if (RegExPatterns.namePattern(txtUsername.getText())) {
            lblUsernameAlert.setText("Invalid Username!");
        } else {
            lblUsernameAlert.setText(" ");
        }
    }

    @FXML
    void txtPasswordOnKeyPressed(KeyEvent event) {
        if (RegExPatterns.passwordPattern(txtPassword.getText())) {
            lblPasswordAlert.setText("Invalid Password!");
        } else {
            lblPasswordAlert.setText(" ");
        }
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("userSignUpGlobalForm.fxml", event);
    }

    @FXML
    void hyperForgotPasswordOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("userForgotPasswordForm.fxml", event);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnSignInOnAction(event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void btnPowerOffOnAction(ActionEvent event) {
        Navigation.exit();
    }

    @FXML
    void btnSignInOnMouseEntered(MouseEvent event) {}

    @FXML
    void btnSignInOnMouseExited(MouseEvent event) {}

    @FXML
    void btnSignUpOnMouseEntered(MouseEvent event) {}

    @FXML
    void btnSignUpOnMouseExited(MouseEvent event) {}
}