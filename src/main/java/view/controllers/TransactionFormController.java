package view.controllers;

import it.unicam.cs.pa.jbudget105325.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * <p>Classe controller per il form JavaFX per l'inserimento di una transazione</p>
 *
 * @param <T>
 */
public class TransactionFormController<T extends AccountInterface> implements Initializable {

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
    private ChoiceBox movementTypeBox;
    @FXML
    private ListView tagList;
    @FXML
    private Button addMovementButton;
    @FXML
    private Button newMovementButton;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label messageLabel;
    @FXML
    private ListView addedMovements;
    @FXML
    private Button addTransaction;
    @FXML
    private Label confirmAddedTransaction;
    @FXML
    private Button newTransaction;
    @FXML
    private ContextMenu contextMenu;
    int count = 0;
    ArrayList<Movement> movements = new ArrayList<>();

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
     * <p>Imposta la choice box per l'account da selezionare</p>
     *
     * @param event
     */
    @FXML
    void onClickEventBox(MouseEvent event) {
        if (count == 0) {
            this.setAccountBox();
            this.accountBox.show();
            count++;
        } else {
            this.accountBox.show();
        }
    }

    /**
     * <p>Inserisce un movimento all'interno della transazione</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventAddMovement(MouseEvent event) throws Exception {
        try {
            String accountSelected = (String) accountBox.getValue();

            int id_account = findAccount(accountSelected.substring(3));

            int id = controller.getSingleAccount(id_account).lastMovementId();

            double amount = Double.parseDouble(amountField.getText());

            MovementType type = (MovementType) movementTypeBox.getValue();

            LocalDate date = datePicker.getValue();

            ArrayList<Tag> tags = this.findTag(tagList.getSelectionModel().getSelectedItems());

            if (date == null || tags.isEmpty() || type == null) {
                throw new Exception("Riempire i campi");
            }

            Movement movement = new Movement(id, id_account, amount, type, date, tags, descriptionArea.getText());

            movements.add(movement);

            this.setAddedMovements(movement);

            addMovementButton.setDisable(true);

            newMovementButton.setDisable(false);

            newMovementButton.setVisible(true);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setTitle("Input errato");
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
    }

    /**
     * <p>Reimposta il form per il reinserimento di un nuovo movimento</p>
     *
     * @param event
     */
    @FXML
    void onClickEventNewMovement(MouseEvent event) {
        newMovementButton.setDisable(true);

        addMovementButton.setDisable(false);

        amountField.clear();

        descriptionArea.clear();

    }

    /**
     * <p>Inserisce la transazione creata</p>
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onClickEventAddTransaction(MouseEvent event) throws Exception {
        try {
            controller.createTransaction(movements);
            jsonPersistenceManager.save(controller.getLedger());
            confirmAddedTransaction.setText("Transazione eseguita");
            addTransaction.setDisable(true);
            newTransaction.setDisable(false);
            newTransaction.setVisible(true);
        } catch (IndexOutOfBoundsException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
    }

    /**
     * <p>Reimposta il form per il reinserimento di una nuova transazione</p>
     *
     * @param event
     */
    @FXML
    void onClickEventNewTransaction(MouseEvent event) {
        addTransaction.setDisable(false);
        newTransaction.setDisable(true);
        addedMovements.getItems().clear();
        confirmAddedTransaction.setText("");

    }

    /**
     * <p>Svuota la lista dei movimenti inseriti</p>
     * @param event
     */
    @FXML
    void onRightClick(ActionEvent event) {
      addedMovements.getItems().clear();
    }


    /**
     * <p>Imposta la list view per selezionare un tag</p>
     */
    @FXML
    private void setTagList() {
        this.tagList.setOrientation(Orientation.VERTICAL);
        this.tagList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (int i = 0; i < controller.getTags().size(); i++) {
            tagList.getItems().add(controller.getSingleTag(i).getName());
        }

    }

    /**
     * <p>Imposta la choice box per la selezione degli account</p>
     */
    @FXML
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
     * <p>Imposta la choice box per la selezione del tipo di movimento</p>
     */
    @FXML
    private void setMovementTypeBox() {
        this.movementTypeBox.getItems().add(MovementType.INCOME);
        this.movementTypeBox.getItems().add(MovementType.SPENDING);
    }

    /**
     * <p>Imposta la list view dei movimenti già inseriti</p>
     *
     * @param movement
     */
    @FXML
    private void setAddedMovements(Movement movement) {
        this.addedMovements.setOrientation(Orientation.VERTICAL);
        this.addedMovements.getItems().add("AccountID:" + movement.getIdAccount() + "-Importo:" + movement.getAmount() + "-Tipo:" + movement.getType());

    }

    /**
     * <p>Trova l'ID dell'account selezionato</p>
     *
     * @param account
     * @return int
     */
    @FXML
    private int findAccount(String account) {
        String[] strings = account.split("-");
        return Integer.parseInt(strings[0]);
    }

    /**
     * <p>Restituisce i tag selezionati</p>
     *
     * @param selectedTags
     * @return ArrayList<Tag>
     */
    @FXML
    private ArrayList<Tag> findTag(ObservableList selectedTags) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < selectedTags.size(); i++) {
            for (int j = 0; j < controller.getTags().size(); j++) {
                if (selectedTags.get(i).equals(controller.getSingleTag(j).getName())) {
                    tags.add(controller.getSingleTag(j));
                }
            }
        }
        return tags;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTagList();
        this.setMovementTypeBox();
    }
}
