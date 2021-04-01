package view.controllers;

import it.unicam.cs.pa.jbudget105325.Controller;
import it.unicam.cs.pa.jbudget105325.JsonPersistenceManager;
import it.unicam.cs.pa.jbudget105325.TransactionPredicate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * <p>Classe controller per l'interfaccia JavaFx iniziale dell'applicazione</p>
 */
public class StartWindowController implements Initializable {
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
     * <p>Carica l'interfaccia per l'inserimento di un nuovo account</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventAccount(MouseEvent event) throws Exception {
        this.loadAddAccountForm(event);
    }

    /**
     * <p>Carica l'interfaccia per l'inserimento di un nuovo tag</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventTag(MouseEvent event) throws Exception {
        this.loadAddTagForm(event);
    }

    /**
     * <p>Carica l'interfaccia per l'inserimento di una nuova transazione</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventTransaction(MouseEvent event) throws Exception {
        this.loadAddTransactionForm(event);
    }

    /**
     * <p>Carica l'interfaccia per le statistiche di un account</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventStatistics(MouseEvent event) throws Exception {
        this.loadStatistics(event);
    }

    /**
     * <p>Carica l'interfaccia per la rimozione degli elementi inseriti</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventRemove(MouseEvent event) throws Exception {
        this.loadRemoveItems(event);
    }

    @FXML
    void loadAddAccountForm(MouseEvent event) throws Exception {
        URL url = Paths.get("./src/main/resources/accountform.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void loadAddTransactionForm(MouseEvent event) throws Exception {
        URL url = Paths.get("./src/main/resources/transactionform.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void loadAddTagForm(MouseEvent event) throws Exception {
        URL url = Paths.get("./src/main/resources/tagform.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void loadStatistics(MouseEvent event) throws Exception {
        URL url = Paths.get("./src/main/resources/report.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void loadRemoveItems(MouseEvent event) throws Exception {
        URL url = Paths.get("./src/main/resources/removeitems.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * <p>Controlla ed esegue le transazioni schedulate per la data odierna</p>
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JsonPersistenceManager jsonPersistenceManager = new JsonPersistenceManager();
        Controller controller = new Controller(jsonPersistenceManager.load());
        try {
            controller.executingScheduledTransaction(TransactionPredicate.datePredicate(LocalDate.now()));
            controller.resetLiabilities();
            jsonPersistenceManager.save(controller.getLedger());
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }
}
