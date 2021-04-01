package view.controllers;

import it.unicam.cs.pa.jbudget105325.Controller;
import it.unicam.cs.pa.jbudget105325.JsonPersistenceManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * <p>Classe controller per il form JavaFX per l'inserimento di un nuovo tag</p>
 */
public class TagFormController implements Initializable {
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
    private TextField nome;
    @FXML
    private TextField descrizione;
    @FXML
    private Label messageLabel;
    @FXML
    private Button insertButton;
    @FXML
    private Button newButton;

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
     * <p>Inserisce il nuovo tag</p>
     *
     * @param event
     */

    @FXML
    void onClickEventAdd(MouseEvent event) {
        try {
            controller.addTag(nome.getText().toLowerCase(), descrizione.getText().toLowerCase());
            messageLabel.setText("Tag inserito");
        } catch (IllegalArgumentException e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setTitle("Input errato");
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
        insertButton.setDisable(true);
        newButton.setDisable(false);
        newButton.setVisible(true);
        jsonPersistenceManager.save(controller.getLedger());
    }

    /**
     * <p>Reimposta il form per il reinserimento di un nuovo tag</p>
     *
     * @param event
     */
    @FXML
    void onClickEventNew(MouseEvent event) {
        nome.clear();
        descrizione.clear();
        newButton.setVisible(false);
        messageLabel.setText("");
        insertButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
