/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Csernay Attila
 */
public class BerloAdatlapController implements Initializable {

    private Berlo berlo;
    private boolean canceled;
    private u.AblakMod mod;
    private String eredetiEmail;
    @FXML
    private TableView<Berles> table_berelt;

    public void setMod(u.AblakMod mod) {
        this.mod = mod;
        if (this.mod == u.AblakMod.UJ) {
            this.table_berelt.setDisable(true);
        }
    }

    @FXML
    private TextField textField_name;
    @FXML
    private TextField textField_email;
    @FXML
    private Button button_cancel;
    @FXML
    private Button button_save;

    public boolean isCanceled() {
        return canceled;
    }

    public Berlo getBerlo() {
        return berlo;
    }

    public void setBerlo(Berlo berlo) {
        this.berlo = berlo;
        if (this.berlo != null && this.berlo != new Berlo()) {
            this.textField_name.setText(this.berlo.getNev());
            this.textField_email.setText(this.berlo.getEmail());
            this.eredetiEmail = this.berlo.getEmail();

            for (Berles berles : u.getBerlesek()) {
                if (this.berlo.getEmail().equals(berles.getBerlo())) {
                    this.table_berelt.getItems().add(berles);
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.canceled = true;

        TableColumn rendszam = new TableColumn("Rendszám");
        rendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));

        TableColumn marka = new TableColumn("Márka");
        marka.setCellValueFactory(new PropertyValueFactory<>("marka"));

        TableColumn tipus = new TableColumn("Típus");
        tipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));

        TableColumn kezdet = new TableColumn("Bérlés kezdete");
        kezdet.setCellValueFactory(new PropertyValueFactory<>("kezdet"));

        TableColumn vege = new TableColumn("Bérlés vége");
        vege.setCellValueFactory(new PropertyValueFactory<>("vege"));

        TableColumn koltseg = new TableColumn("Költség");
        koltseg.setCellValueFactory(new PropertyValueFactory<>("koltseg"));

        this.table_berelt.getColumns().addAll(rendszam, marka, tipus, kezdet, vege, koltseg);
    }

    @FXML
    private void button_cancel_OnAction(ActionEvent event) {
        u.berloAdatlap.close();
    }

    @FXML
    private void button_save_OnAction(ActionEvent event) {
        boolean hiba = false;
        if (this.textField_email.getText().contains("@")) {
            if (this.mod == u.AblakMod.UJ) {
                if (!u.egyediEmail(this.textField_email.getText())) {
                    hiba = true;
                }
            } else {
                if (!this.eredetiEmail.equals(this.textField_email.getText())) {
                    if (!u.egyediEmail(this.textField_email.getText())) {
                        hiba = true;
                    }
                }
            }
        } else {
            hiba = true;
        }

        if (hiba) {
            u.Alert("Hibás adatok", "Az email nem megfelelő!", "Az emailnek egyedinek kell lennie, és tartamaznia kell a '@' karaktert!");
        } else {
            this.canceled = false;

            this.berlo.setNev(this.textField_name.getText());
            this.berlo.setEmail(this.textField_email.getText());

            u.berloAdatlap.close();
        }
    }

}
