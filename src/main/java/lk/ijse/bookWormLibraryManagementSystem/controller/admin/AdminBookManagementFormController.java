package lk.ijse.bookWormLibraryManagementSystem.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lk.ijse.bookWormLibraryManagementSystem.dto.BookDto;
import lk.ijse.bookWormLibraryManagementSystem.proxy.BookRepositoryProxy;
import lk.ijse.bookWormLibraryManagementSystem.proxy.UserRole;
import lk.ijse.bookWormLibraryManagementSystem.service.ServiceFactory;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.BookService;
import lk.ijse.bookWormLibraryManagementSystem.strategy.*;
import lk.ijse.bookWormLibraryManagementSystem.util.Navigation;
import lk.ijse.bookWormLibraryManagementSystem.util.RegExPatterns;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminBookManagementFormController implements Initializable {

    @FXML
    private Pane AddBookPane;

    @FXML
    private ImageView imgAdd;

    @FXML
    private Label lblAddBook;

    @FXML
    private Label lblSearchAlert;

    @FXML
    private Pane searchPane;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vBoxBookManage;

    private List<BookDto> list;
    private SearchContext searchContext = new SearchContext();

    //  Déclarer le rôle de cet écran : c'est un admin
    private final UserRole currentRole = UserRole.ADMIN;

    BookService bookService =
            (BookService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.BOOK);

    private static AdminBookManagementFormController controller;

    public AdminBookManagementFormController() {
        controller = this;
    }

    public static AdminBookManagementFormController getInstance() {
        return controller;
    }

    //  Méthode utilitaire : valide le rôle via le proxy avant toute opération sensible
    private void checkAccess(String operation) {
        BookRepositoryProxy proxy = new BookRepositoryProxy(currentRole);
        // Le proxy lèvera SecurityException si le rôle n'est pas ADMIN
        // On l'appelle sur une méthode fictive pour déclencher la vérification
        switch (operation) {
            case "save"  :
                proxy.checkPermission(operation);
                break;// déclenche checkAdmin
            case "update" :
                proxy.checkPermission(operation);
                break;// déclenche checkAdmin
            case "delete" :
                proxy.checkPermission(operation);
                break;// déclenche checkAdmin
        }
    }
    @FXML
    void btnAddBookOnAction(ActionEvent event) throws IOException {
        // ✅ Vérification proxy avant d'ouvrir le formulaire d'ajout
        try {
            checkAccess("save");
            Navigation.imgPopUpBackground("addBookPopUpForm.fxml");
        } catch (SecurityException e) {
            showAccessDenied(e.getMessage());
        }
    }

    @FXML
    void btnAddBookOnMouseEntered(MouseEvent event) {

    }

    @FXML
    void btnAddBookOnMouseExited(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws IOException {
        if (validateSearch()) {
            if (!RegExPatterns.idPattern(txtSearch.getText())) {
                searchContext.setStrategy(new SearchByBookId());
            } else {
                searchContext.setStrategy(new SearchByBookName());
            }

            List<BookDto> activeBooks = list.stream()
                    .filter(dto -> !dto.getStatus().equals("Removed"))
                    .collect(Collectors.toList());

            BookDto found = searchContext.search(txtSearch.getText(), activeBooks);

            if (found != null) {
                AdminBookManagementBarFormController.bookId = found.getId();
                Navigation.imgPopUpBackground("viewBookPopUpForm.fxml");
                txtSearch.clear();
                lblSearchAlert.setText(" ");
                return;
            }

            lblSearchAlert.setText("Invalid Id Or Name!!");
        }
        txtSearch.clear();
    }
    private boolean validateSearch() {
        if (validateName() & validateId()) {
            lblSearchAlert.setText("Invalid Id Or Name!!");
            return false;
        }
        return true;
    }

    public boolean validateName() {
        return RegExPatterns.namePattern(txtSearch.getText());
    }

    public boolean validateId() {
        return RegExPatterns.idPattern(txtSearch.getText());
    }

    @FXML
    void txtSearchOnMouseMoved(MouseEvent event) {
        lblSearchAlert.setText(" ");
    }

    public void allBookId() {
        vBoxBookManage.getChildren().clear();
        list = bookService.getAllBookId();
        if (list == null) return;

        for (BookDto dto : list) {
            if (!dto.getStatus().equals("Removed")) loadDataTable(dto.getId());
        }
    }

    private void loadDataTable(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminBookManagementFormController.class.getResource("/view/adminBookManagementBarForm.fxml"));
            Parent root = loader.load();
            AdminBookManagementBarFormController controller = loader.getController();
            controller.setData(id);
            vBoxBookManage.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // ✅ Affichage d'une alerte si accès refusé par le proxy
    private void showAccessDenied(String message) {
        lblSearchAlert.setText("Accès refusé : " + message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allBookId();
    }

}
