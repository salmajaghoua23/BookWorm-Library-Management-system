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

    @FXML
    void btnAddBookOnAction(ActionEvent event) throws IOException {
        Navigation.imgPopUpBackground("addBookPopUpForm.fxml");
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

            // idPattern retourne true = "pas un ID" → donc on inverse avec !
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

            // Si rien trouvé, afficher l'alerte
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allBookId();
    }

}
