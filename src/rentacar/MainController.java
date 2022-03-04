/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Csernay Attila
 */
public class MainController implements Initializable {

    @FXML
    private Button button_ujBerlo;
    @FXML
    private TableView<Berlo> tableView_berlo;
    @FXML
    private TableView<Auto> tableView_auto;
    @FXML
    private Button button_berloSzerkeszt;
    @FXML
    private Button button_torles;
    @FXML
    private TextField textfield_berlo;
    @FXML
    private Button button_ujAuto;
    @FXML
    private Button button_autoSzerkeszt;
    @FXML
    private Button button_autoTorles;
    @FXML
    private TextField textfield_autok;
    @FXML
    private Button button_berles;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        u.login.close();
        try {
            u.loadBerlok();
            u.loadAutok();
            u.loadBerlesek();
        } catch (FileNotFoundException ex) {
            u.Alert("", "", "");
        }

        TableColumn nev = new TableColumn("Név");
        nev.setCellValueFactory(new PropertyValueFactory<>("nev"));

        TableColumn email = new TableColumn("Email cím");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        this.tableView_berlo.getColumns().addAll(nev, email);

        TableColumn rendszam = new TableColumn("Rendszám");
        rendszam.setCellValueFactory(new PropertyValueFactory<>("rendszam"));

        TableColumn marka = new TableColumn("Márka");
        marka.setCellValueFactory(new PropertyValueFactory<>("marka"));

        TableColumn tipus = new TableColumn("Típus");
        tipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));

        TableColumn ar = new TableColumn("Egységár");
        ar.setCellValueFactory(new PropertyValueFactory<>("ar"));

        this.tableView_auto.getColumns().addAll(rendszam, marka, tipus, ar);

        this.berloTableReset();
        this.autoTablaReset();
    }

    @FXML
    private void button_ujBerlo_OnAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BerloAdatlap.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        BerloAdatlapController adat = loader.getController();
        u.berloAdatlap = stage;
        adat.setBerlo(new Berlo());
        adat.setMod(u.AblakMod.UJ);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Rent a Car - Új bérlő");
        stage.showAndWait();
        // validálás utáni állapot
        if (!adat.isCanceled()) {
            u.addBerlo(adat.getBerlo());
            this.berloTableReset();
            u.saveBerlok();
        }
        u.berloAdatlap = null;
    }

    private void berloTableReset() {
        //this.textfield_berlo.setText("");
        this.tableView_berlo.getItems().clear();

        for (Berlo berlo : u.getBerlok()) {            
            this.tableView_berlo.getItems().add(berlo);
        }

        this.tableView_berlo.autosize();
    }

    private void autoTablaReset() {
        this.tableView_auto.getItems().clear();

        for (Auto auto : u.getAutok()) {
            this.tableView_auto.getItems().add(auto);
        }

        this.tableView_auto.autosize();
    }

    @FXML
    private void button_berloSzerkeszt_OnAction(ActionEvent event) throws Exception {
        if (this.tableView_berlo.getSelectionModel().getSelectedIndex() > -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BerloAdatlap.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            BerloAdatlapController adat = loader.getController();
            u.berloAdatlap = stage;
            adat.setBerlo(this.tableView_berlo.getSelectionModel().getSelectedItem());
            adat.setMod(u.AblakMod.SZERKESZT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rent a Car - Bérlő szerkesztés");
            stage.showAndWait();
            // validálás utáni állapot
            if (!adat.isCanceled()) {
                u.getBerlok().remove(this.tableView_berlo.getSelectionModel().getSelectedItem());
                u.addBerlo(adat.getBerlo());
                this.berloTableReset();
                u.saveBerlok();
            }
            u.berloAdatlap = null;
        } else {
            u.Alert("Figyelem!", "Válasszon ki egy bérlőt", "Válasszon ki egy bérlőt a táblázatból!");
        }
    }

    @FXML
    private void button_torles_OnAction(ActionEvent event) throws IOException {
        if (this.tableView_berlo.getSelectionModel().getSelectedIndex() > -1) {
            u.getBerlok().remove(this.tableView_berlo.getSelectionModel().getSelectedItem());
            u.saveBerlok();
            this.berloTableReset();
        } else {
            u.Alert("Figyelem!", "Válasszon ki egy bérlőt", "Válasszon ki egy bérlőt a táblázatból!");
        }
    }

    @FXML
    private void textfield_berlo_OnActioin(KeyEvent event) {
        this.tableView_berlo.getItems().clear();
        for (Berlo berlo : u.berloFilter(this.textfield_berlo.getText())) {
            this.tableView_berlo.getItems().add(berlo);
        }
    }

    @FXML
    private void button_ujAuto_OnAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AutoAdatlap.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        AutoAdatlapController adat = loader.getController();
        u.autoAdatlap = stage;
        adat.setAuto(new Auto());
        adat.setMod(u.AblakMod.UJ);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Rent a Car - Új autó");
        stage.showAndWait();
        // validálás utáni állapot
        if (!adat.isCancel()) {
            u.addAuto(adat.getAuto());
            this.autoTablaReset();
            u.saveAutok();
        }
        u.autoAdatlap = null;
    }

    @FXML
    private void button_autoSzerkeszt_OnAction(ActionEvent event) throws IOException {
        if (this.tableView_auto.getSelectionModel().getSelectedIndex() > -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AutoAdatlap.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            AutoAdatlapController adat = loader.getController();
            u.autoAdatlap = stage;
            adat.setAuto(this.tableView_auto.getSelectionModel().getSelectedItem());
            adat.setMod(u.AblakMod.SZERKESZT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rent a Car - Autó szerkesztés");
            stage.showAndWait();
            // validálás utáni állapot
            if (!adat.isCancel()) {
                u.getAutok().remove(this.tableView_auto.getSelectionModel().getSelectedItem());
                u.addAuto(adat.getAuto());
                this.autoTablaReset();
                u.saveAutok();
            }
            u.autoAdatlap = null;
        } else {
            u.Alert("Figyelem!", "Válasszon ki egy autót", "Válasszon ki egy autót a táblázatból!");
        }
    }

    @FXML
    private void button_autoTorles_OnAction(ActionEvent event) throws IOException {
        if (this.tableView_auto.getSelectionModel().getSelectedIndex() > -1) {
            u.getBerlok().remove(this.tableView_auto.getSelectionModel().getSelectedItem());
            u.saveAutok();
            this.autoTablaReset();
        } else {
            u.Alert("Figyelem!", "Válasszon ki egy autót", "Válasszon ki egy autót a táblázatból!");
        }
    }

    @FXML
    private void textfield_autok_OnActioin(KeyEvent event) {
        this.tableView_auto.getItems().clear();
        for (Auto auto : u.autoFilter(this.textfield_autok.getText())) {
            this.tableView_auto.getItems().add(auto);
        }
    }

    @FXML
    private void button_berles_OnAction(ActionEvent event) throws IOException {
        if (this.tableView_auto.getSelectionModel().getSelectedIndex() > -1 && this.tableView_berlo.getSelectionModel().getSelectedIndex() > -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BerlesAdatlap.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            BerlesAdatlapController adat = loader.getController();
            u.berlesAdatlap = stage;
            adat.initData(this.tableView_berlo.getSelectionModel().getSelectedItem(), this.tableView_auto.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rent a Car - Bérlés");
            stage.showAndWait();
            u.berlesAdatlap = null;
        } else {
            u.Alert("Nincs kiválasztva minden adat!", "Válasszon ki egy bérlőt és egy autót!", "Válasszon ki egy bérlőt és egy autót!");
        }
    }

}
