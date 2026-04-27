package lk.ijse.bookWormLibraryManagementSystem.observer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;


import java.util.Observer;

public class OverdueBorrowersUIObserver implements DashboardObserver {
    private final VBox vBoxOverDueBorrowers;

    public OverdueBorrowersUIObserver(VBox vBox) {
        this.vBoxOverDueBorrowers = vBox;
    }

    @Override
    public void onOverdueDetected(TransactionDto dto) {
        // Ajouter une ligne rouge dans le VBox
        Platform.runLater(() -> {
            Label label = new Label(dto.getUser().getName() + " — EN RETARD");
            label.setStyle("-fx-text-fill: #C0392B; -fx-font-weight: bold;");
            vBoxOverDueBorrowers.getChildren().add(label);
        });
    }

    @Override
    public void onDataRefreshed() {
        Platform.runLater(() -> vBoxOverDueBorrowers.getChildren().clear());
    }

}
