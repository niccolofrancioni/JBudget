package view.controllers;

import it.unicam.cs.pa.jbudget105325.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.net.URL;
import java.nio.file.Paths;

/**
 * <p>Classe controller per il form JavaFx per l'inserimento di nuovi account</p>
 */
public class AccountFormController {
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
    private ChoiceBox accountType;
    @FXML
    private TextField balanceField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField bankField;
    @FXML
    private TextField limitField;
    @FXML
    private ChoiceBox accountRelated;
    @FXML
    private Button addAccountButton;
    @FXML
    private Button newAccountButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField ownerField;
    int count = 0;

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
     * <p>Seleziona il tipo di account</p>
     *
     * @param event
     */
    @FXML
    void onClickEventAccountType(MouseEvent event) {
        this.setAllDisabled();

        if (count == 0) {
            this.setAccountType();
            this.setAccountRelated();
            count++;
        }
        this.accountType.show();
        accountType.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> setForm(newValue));

    }

    /**
     * <p>Aggiunge il nuovo account creato</p>
     *
     * @param event
     */
    @FXML
    void onClickEventAddAccount(MouseEvent event) {
        try {
            if (accountType.getValue().equals("Conto corrente")) {
                controller.addAccount(this.createBankAccount());
                this.accountRelated.getItems().add("id:" + this.createBankAccount().getID() + "-" + this.createBankAccount().getDescription() + "-" + this.createBankAccount().getOwner());
            }
            if (accountType.getValue().equals("Carta di credito")) {
                controller.addAccount(this.createCreditCard());
                this.accountRelated.getItems().add("id:" + this.createCreditCard().getID() + "-" + this.createCreditCard().getDescription() + "-" + this.createCreditCard().getOwner());
            }
            if (accountType.getValue().equals("Carta Bancomat")) {
                controller.addAccount(this.createBancomat());
                this.accountRelated.getItems().add("id:" + this.createBancomat().getID() + "-" + this.createBancomat().getDescription() + "-" + this.createBancomat().getOwner());
            }
            jsonPersistenceManager.save(controller.getLedger());
            addAccountButton.setDisable(true);
            messageLabel.setText("Account inserito");
            newAccountButton.setDisable(false);
            newAccountButton.setVisible(true);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.setTitle("Input errato");
            dialog.setContentText(e.getMessage());
            dialog.show();
        }
    }

    /**
     * <p>Reimposta il form per il reinserimento di un nuovo account</p>
     *
     * @param event
     */
    @FXML
    void onClickEventNewAccount(MouseEvent event) {
        addAccountButton.setDisable(false);
        messageLabel.setText("");
        newAccountButton.setDisable(true);
        balanceField.clear();
        ownerField.clear();
        bankField.clear();
        descriptionArea.clear();
        ibanField.clear();
        bankField.clear();
        limitField.clear();
    }

    /**
     * <p>Crea un conto corrente</p>
     *
     * @return
     * @throws Exception
     */
    @FXML
    private BankAccount createBankAccount() throws Exception {
        String owner = ownerField.getText();
        double balance = Double.parseDouble(balanceField.getText());
        String description = descriptionArea.getText();
        String iban = ibanField.getText();
        String bank = bankField.getText();
        if (owner.isEmpty() || iban.isEmpty() || bank.isEmpty()) {
            throw new Exception("Riempire i campi");
        }
        BankAccount bankAccount = new BankAccount(controller.lastAccountID(), owner, balance, description, iban, bank);
        return bankAccount;
    }

    /**
     * <p>Crea una carta di credito</p>
     *
     * @return
     * @throws Exception
     */
    @FXML
    private CreditCard createCreditCard() throws Exception {
        String owner = ownerField.getText();
        double balance = Double.parseDouble(balanceField.getText());
        String description = descriptionArea.getText();
        double limit = Double.parseDouble(limitField.getText());
        String accountSelected = (String) accountRelated.getValue();
        int id = findAccount(accountSelected.substring(3));
        if (owner.isEmpty()) {
            throw new Exception("Riempire i campi");
        }
        CreditCard creditCard = new CreditCard(controller.lastAccountID(), owner, balance, description, limit, id);
        return creditCard;
    }

    /**
     * <p>Crea un bancomat</p>
     *
     * @return
     * @throws Exception
     */
    @FXML
    private Bancomat createBancomat() throws Exception {
        String owner = ownerField.getText();
        double balance = Double.parseDouble(balanceField.getText());
        String description = descriptionArea.getText();
        double limit = Double.parseDouble(limitField.getText());
        String accountSelected = (String) accountRelated.getValue();
        int id = findAccount(accountSelected.substring(3));
        if (owner.isEmpty()) {
            throw new Exception("Riempire i campi");
        }
        Bancomat bancomat = new Bancomat(controller.lastAccountID(), owner, balance, description, limit, id);
        return bancomat;
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
     * <p>Imposta la choice box per il tipo di account</p>
     */
    @FXML
    private void setAccountType() {
        this.accountType.getItems().add("Conto corrente");
        this.accountType.getItems().add("Carta di credito");
        this.accountType.getItems().add("Carta Bancomat");
    }

    /**
     * <p>Imposta la choiche box per l'account da collegare al bancomat o carta di
     * credito</p>
     */
    @FXML
    private void setAccountRelated() {
        try {
            for (int i = 0; i < controller.getAccounts().size(); i++) {
                this.accountRelated.getItems().add("id:" + controller.getAccount(i).getID() + "-" + controller.getAccount(i).getDescription() + "-" + controller.getAccount(i).getOwner());
            }
        } catch (Exception e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    /**
     * <p>Imposta il form a seconda del tipo di account che si andrà
     * ad inserire</p>
     *
     * @param value
     */
    @FXML
    private void setForm(Object value) {
        if (accountType.getValue() != null) {
            if (accountType.getValue().equals("Conto corrente")) {
                this.setForBankAccount();
            }

            if (accountType.getValue().equals("Carta di credito")) {
                this.setForCreditCard();
            }

            if (accountType.getValue().equals("Carta Bancomat")) {
                this.setForBancomat();
            }

        }
    }

    /**
     * <p>Imposta il form per l'inserimento di un conto corrente</p>
     */
    @FXML
    private void setForBankAccount() {
        balanceField.setDisable(false);
        ownerField.setDisable(false);
        descriptionArea.setDisable(false);
        ibanField.setDisable(false);
        bankField.setDisable(false);
    }

    /**
     * <p>Imposta il form per l'inserimento di una carta di credito</p>
     */
    @FXML
    private void setForCreditCard() {
        balanceField.setDisable(false);
        ownerField.setDisable(false);
        descriptionArea.setDisable(false);
        limitField.setDisable(false);
        accountRelated.setDisable(false);
    }

    /**
     * <p>Imposta il form per l'inserimento di un bancomat</p>
     */
    @FXML
    private void setForBancomat() {
        balanceField.setDisable(false);
        ownerField.setDisable(false);
        descriptionArea.setDisable(false);
        limitField.setDisable(false);
        accountRelated.setDisable(false);
    }

    /**
     * <p>Disabilita tutti i campi</p>
     */
    @FXML
    private void setAllDisabled() {
        balanceField.setDisable(true);
        descriptionArea.setDisable(true);
        ibanField.setDisable(true);
        bankField.setDisable(true);
        limitField.setDisable(true);
        accountRelated.setDisable(true);
    }
}
