package view.controllers;

import it.unicam.cs.pa.jbudget105325.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * <p>Classe controller per l'interfaccia JavaFX per le statistiche di un account</p>
 *
 * @param <T>
 */
public class ReportController<T extends Account> implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu;
    @FXML
    private MenuItem addAccountMenuItem;
    @FXML
    private MenuItem addTransactionMenuItem;
    @FXML
    private MenuItem addTagMenuItem;
    @FXML
    private MenuItem statisticsMenuItem;
    @FXML
    private MenuItem removeItemsMenuItem;
    @FXML
    private ChoiceBox accountBox;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label limitLabel;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis tag;
    @FXML
    private NumberAxis totale;

    JsonPersistenceManager jsonPersistenceManager = new JsonPersistenceManager();
    Controller controller = new Controller(jsonPersistenceManager.load());

    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                URL url = null;
                if (event.getSource() == addAccountMenuItem) {
                    url = Paths.get("./src/main/resources/accountform.fxml").toUri().toURL();
                }

                if (event.getSource() == addTransactionMenuItem) {
                    url = Paths.get("./src/main/resources/transactionform.fxml").toUri().toURL();
                }

                if (event.getSource() == addTagMenuItem) {
                    url = Paths.get("./src/main/resources/tagform.fxml").toUri().toURL();
                }

                if (event.getSource() == statisticsMenuItem) {
                    url = Paths.get("./src/main/resources/report.fxml").toUri().toURL();
                }

                if (event.getSource() == removeItemsMenuItem) {
                    url = Paths.get("./src/main/resources/removeitems.fxml").toUri().toURL();
                }

                Parent root = FXMLLoader.load(url);
                Stage stage = (Stage) menuBar.getScene().getWindow();
                stage.setTitle("JBudget");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    /**
     * <p>Carica la scena selezionata dal menu bar</p>
     *
     * @param event
     */
    @FXML
    void onClickEventMenu(ActionEvent event) {
        addAccountMenuItem.setOnAction(handler);
        addTransactionMenuItem.setOnAction(handler);
        addTagMenuItem.setOnAction(handler);
        statisticsMenuItem.setOnAction(handler);
        removeItemsMenuItem.setOnAction(handler);
    }

    /**
     * <p>Genera il report dell'account selezionato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventGenerateReport(MouseEvent event) {
        try {
            String accountSelected = (String) accountBox.getValue();
            T account = findAccount(accountSelected.substring(3));
            ArrayList<Report> reports = new ArrayList<>();
            String balance = String.valueOf(account.getBalance());
            balanceLabel.setText(balance);
            if (account instanceof Bancomat ) {
                String text = "Soglia limite:" + String.valueOf(((Bancomat) account).getCreditLimit() + " Soglia raggiunta:" + String.valueOf(account.getBalance()));
                limitLabel.setText(text);
            }
            if (account instanceof CreditCard ) {
                String text = "Soglia limite:" + String.valueOf(((CreditCard) account).getCreditLimit() + " Soglia raggiunta:" + String.valueOf(account.getBalance()));
                limitLabel.setText(text);
            }

            for (int i = 0; i < controller.getTags().size(); i++) {
                Report report = new Report();
                if (fromDate.getValue() == null && toDate.getValue() == null)
                    reports.add(report.generateReport(account, controller.getSingleTag(i)));
                else {
                    reports.add(report.generateReport(account, controller.getSingleTag(i), fromDate.getValue(), toDate.getValue()));
                }
            }

            this.setBarChart(reports);
        }catch(NullPointerException e){}
        catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
    }

    /**
     * <p>Trova l'ID dell'account selezionato</p>
     *
     * @param account
     * @return int
     * @throws Exception
     */
    @FXML
    private T findAccount(String account) throws Exception {
        String[] strings = account.split("-");
        int id = Integer.parseInt(strings[0]);
        return (T) controller.getSingleAccount(id);
    }

    /**
     * <p>Imposta la choice box per l'account da selezionare</p>
     */
    private void setAccountBox() {
        try {
            for (int i = 0; i < controller.getAccounts().size(); i++) {
                this.accountBox.getItems().add("id:" + controller.getAccount(i).getID() + "-" + controller.getAccount(i).getDescription() + "-" + controller.getAccount(i).getOwner());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
    }

    /**
     * <p>Imposta il grafico per il report</p>
     *
     * @param reports
     */
    private void setBarChart(ArrayList<Report> reports) {
        barChart.getData().clear();

        XYChart.Series spese = new XYChart.Series();
        spese.setName("Spese");

        for (int i = 0; i < reports.size(); i++) {
            spese.getData().add(new XYChart.Data(reports.get(i).getTag().getName(), reports.get(i).getSpending()));
        }

        XYChart.Series entrate = new XYChart.Series();
        entrate.setName("Entrate");


        for (int i = 0; i < reports.size(); i++) {
            entrate.getData().add(new XYChart.Data(reports.get(i).getTag().getName(), reports.get(i).getIncomes()));
        }


        barChart.getData().addAll(spese, entrate);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.setAccountBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
