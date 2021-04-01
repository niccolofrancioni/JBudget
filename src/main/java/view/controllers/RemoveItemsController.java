package view.controllers;

import it.unicam.cs.pa.jbudget105325.Controller;
import it.unicam.cs.pa.jbudget105325.JsonPersistenceManager;
import org.controlsfx.dialog.*;
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

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * <p>Classe controller per l'interfaccia JavaFX per la rimozione di elementi inseriti</p>
 */
public class RemoveItemsController implements Initializable {
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
    private ListView accountList;
    @FXML
    private ListView tagList;
    @FXML
    private ListView transactionList;
    @FXML
    private ListView movementList;
    @FXML
    private ListView scheduledTransactionList;


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
     * <p>Imposta la list view dei movimenti dell'account selezionato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventAccountList(MouseEvent event) {
        try {
            this.movementList.getItems().clear();
            String value = (String) this.accountList.getSelectionModel().getSelectedItem();
            this.setMovementList(findAccount(value.substring(3)));
        } catch (Exception e) {
        }
    }

    /**
     * <p>Rimuove l'account selezionato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventAccountRemoveButton(MouseEvent event) {
        try {
            String accountSelected = (String) this.accountList.getSelectionModel().getSelectedItem();
            int id = findAccount(accountSelected.substring(3));
            controller.removeAccount(id);
            jsonPersistenceManager.save(controller.getLedger());
            this.setAccountList();
            this.movementList.getItems().clear();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Rimuove il tag selezionato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventTagRemoveButton(MouseEvent event) {
        try {
            String tagSelected = (String) this.tagList.getSelectionModel().getSelectedItem();
            controller.removeTag(tagSelected);
            jsonPersistenceManager.save(controller.getLedger());
            this.setTagList();
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Rimuove il movimento selezionato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventMovementRemoveButton(MouseEvent event) {
        try {
            String accountSelected = (String) this.accountList.getSelectionModel().getSelectedItem();
            int id_account = findAccount(accountSelected.substring(3));
            String[] value = this.movementList.getSelectionModel().getSelectedItem().toString().split(",");
            int id = Integer.parseInt(value[0].substring(12));
            controller.getSingleAccount(id_account).removeMovement(id);
            jsonPersistenceManager.save(controller.getLedger());
            this.setMovementList(id_account);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Rimuove la transazione selezionata</p>
     *
     * @param event
     */
    @FXML
    void onClickEventTransactionRemoveButton(MouseEvent event) {
        try {
            String transactionSelected = (String) this.transactionList.getSelectionModel().getSelectedItem();
            int id = findTransaction(transactionSelected.substring(3));
            controller.removeTransaction(id);
            jsonPersistenceManager.save(controller.getLedger());
            this.setTransactionList();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Rimuove la transazione schedulata selezionata</p>
     *
     * @param event
     */
    @FXML
    void onClickEventScheduledTransactionRemoveButton(MouseEvent event) {
        try {
            String transactionSelected = (String) this.scheduledTransactionList.getSelectionModel().getSelectedItem();
            int id = findTransaction(transactionSelected.substring(3));
            controller.removeScheduledTransaction(id);
            jsonPersistenceManager.save(controller.getLedger());
            this.setScheduledTransactionList();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }


    /**
     * <p>Imposta la list view degli account</p>
     */
    private void setAccountList() {
        this.accountList.setOrientation(Orientation.VERTICAL);
        this.accountList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.accountList.getItems().clear();
        for (int i = 0; i < controller.getAccounts().size(); i++) {
            try {
                this.accountList.getItems().add("id:" + controller.getAccount(i).getID() + "-" + controller.getAccount(i).getDescription() + "-" + controller.getAccount(i).getOwner());
            } catch (Exception e) {
                ExceptionDialog dialog = new ExceptionDialog(e);
                dialog.show();
            }
        }

    }

    /**
     * <p>Imposta la list view dei tag</p>
     */
    private void setTagList() {
        try {
            this.tagList.setOrientation(Orientation.VERTICAL);
            this.tagList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.tagList.getItems().clear();
            for (int i = 0; i < controller.getTags().size(); i++) {
                tagList.getItems().add(controller.getSingleTag(i).getName());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Imposta la list view delle transazioni</p>
     */
    private void setTransactionList() {
        try {
            this.transactionList.setOrientation(Orientation.VERTICAL);
            this.transactionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.transactionList.getItems().clear();
            for (int i = 0; i < controller.getTransactions().size(); i++) {
                this.transactionList.getItems().add("id:" + controller.getSingleTransaction(i).getID() + "-Data: " + controller.getSingleTransaction(i).getDate() + "-" + controller.getSingleTransaction(i).movements().toString());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Imposta la list view dei movimenti</p>
     *
     * @param id
     */
    private void setMovementList(int id) {
        try {
            this.movementList.setOrientation(Orientation.VERTICAL);
            this.movementList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.movementList.getItems().clear();
            for (int i = 0; i < controller.getSingleAccount(id).getMovements().size(); i++) {
                this.movementList.getItems().add(controller.getSingleAccount(id).getMovements().get(i).toString());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Imposta la list view delle transazioni schedulate</p>
     */
    private void setScheduledTransactionList() {
        try {
            this.scheduledTransactionList.setOrientation(Orientation.VERTICAL);
            this.scheduledTransactionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.scheduledTransactionList.getItems().clear();
            for (int i = 0; i < controller.getScheduledTransactions().size(); i++) {
                this.scheduledTransactionList.getItems().add("id:" + controller.getSingleScheduledTransaction(i).getID() + "-Data: " + controller.getSingleScheduledTransaction(i).getDate() + "-" + controller.getSingleScheduledTransaction(i).movements().toString());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
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
     * <p>Trova l'ID della transazione selezonata</p>
     *
     * @param account
     * @return int
     */
    @FXML
    private int findTransaction(String account) {
        String[] strings = account.split("-");
        return Integer.parseInt(strings[0]);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.setAccountList();
            this.setScheduledTransactionList();
            this.setTagList();
            this.setTransactionList();
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }
}
