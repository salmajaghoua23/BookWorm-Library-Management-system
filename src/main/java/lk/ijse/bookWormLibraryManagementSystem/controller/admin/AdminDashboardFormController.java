package lk.ijse.bookWormLibraryManagementSystem.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lk.ijse.bookWormLibraryManagementSystem.decorator.LoggedDashboardService;
import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;
import lk.ijse.bookWormLibraryManagementSystem.observer.*;
import lk.ijse.bookWormLibraryManagementSystem.projection.AdminProjection;
import lk.ijse.bookWormLibraryManagementSystem.service.ServiceFactory;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.DashboardService;
import lk.ijse.bookWormLibraryManagementSystem.state.*;


import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AdminDashboardFormController implements Initializable {

    @FXML
    private Label lblTotalBookCount;

    @FXML
    private Label lblTotalBranchCount;

    @FXML
    private Label lblTotalUserCount;

    @FXML
    private VBox vBoxAdmins;

    @FXML
    private VBox vBoxOverDueBorrowers;

    @FXML
    private PieChart pieChart;

    @FXML
    private Pane pieChartPane;
    @FXML
    private Pane rootPane;
    DashboardService dashboardService = new LoggedDashboardService(
            (DashboardService) ServiceFactory.getInstance()
                    .getService(ServiceFactory.ServiceTypes.DASHBOARD)
    );


    private void setLabelValues() {
        lblTotalUserCount.setText("0" + dashboardService.getAllUserCount());
        lblTotalBookCount.setText("0" + dashboardService.getAllBookCount());
        lblTotalBranchCount.setText("0" + dashboardService.getAllBranchCount());
    }

    private void allAdmins() {
        vBoxAdmins.getChildren().clear();
        List<AdminProjection> adminIdAndNameList = dashboardService.getAdminIdAndName();
        if (adminIdAndNameList == null) return;

        for (AdminProjection projection : adminIdAndNameList) {
            loadDataTable(projection);
        }
    }

    private void loadDataTable(AdminProjection projection) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminDashboardFormController.class.getResource("/view/adminDashboardAdminsBarForm.fxml"));
            Parent root = loader.load();
            AdminDashboardAdminsBarFormController controller = loader.getController();
            controller.setData(projection);
            vBoxAdmins.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void allOverDueBorrowers() {
        vBoxOverDueBorrowers.getChildren().clear();
        List<TransactionDto> allOverDueBorrowers = dashboardService.getAllOverDueBorrowers();
        if (allOverDueBorrowers == null) return;

        for (TransactionDto dto : allOverDueBorrowers) {
            loadOverDueDataTable(dto);
        }
    }

    private void loadOverDueDataTable(TransactionDto dto) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminDashboardFormController.class.getResource("/view/adminDashboardOverdueBorrowersBarForm.fxml"));
            Parent root = loader.load();
            AdminDashboardOverdueBorrowersBarFormController controller = loader.getController();
            controller.setData(dto);
            vBoxOverDueBorrowers.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPieChart() {
        ObservableList<PieChart.Data> pieChartData = addPieChartData();

        pieChart = new PieChart(pieChartData); /* Create a pie chart with the data */

        pieChart.getData().get(0).getNode().setStyle(
                "-fx-pie-color: #3D3E3E;" +
                        "-fx-border-color: transaparent");
        pieChart.getData().get(1).getNode().setStyle(
                "-fx-pie-color: #151619;" +
                        "-fx-border-color: transaparent");
        pieChart.setLabelLineLength(0);
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(false);
        pieChart.setClockwise(true);
        pieChart.setPrefSize(380, 380);


        pieChartPane.getChildren().add(pieChart);
    }

    /** Add data for the Pie Chart */
    private ObservableList<PieChart.Data> addPieChartData() {
        TransactionContext context = new TransactionContext();
        List<TransactionDto> allTransactions = dashboardService.getAllTransactions();

        for (TransactionDto dto : allTransactions) {
            TransactionState state = TransactionStateFactory.fromType(dto.getTransactionType());
            state.process(context);  // plus de if/else String !
        }

        return FXCollections.observableArrayList(
                new PieChart.Data("Total Borrowed Books", context.getBorrowCount()),
                new PieChart.Data("Total Returned Books", context.getReturnCount())
        );
    }


    private void setData() {
        setLabelValues();
        setPieChart();
        allAdmins();
        //allOverDueBorrowers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Enregistrer l'observer
        DashboardEventManager manager = DashboardEventManager.getInstance();
        manager.addObserver(new OverdueBorrowersUIObserver(vBoxOverDueBorrowers));
        System.out.println("observer enregistré");

        setData();
        // 🔥 Zoom global
        rootPane.setScaleX(0.7);
        rootPane.setScaleY(0.7);
        // Notifier apres chargement
        List<TransactionDto> overdue = dashboardService.getAllOverDueBorrowers();
        overdue.forEach(dto -> manager.notifyOverdue(dto));
    }
}
