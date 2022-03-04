/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Csernay Attila
 */
public class BerlesAdatlapController implements Initializable {

    private boolean cancelled;
    private Berlo berlo;
    private Auto auto;
    private double koltseg;

    @FXML
    private TextArea textArea_berles;
    @FXML
    private DatePicker datePicker_kezdet;
    @FXML
    private DatePicker datePicker_vege;
    @FXML
    private Label label_kolteg;
    @FXML
    private Button button_megse;
    @FXML
    private Button button_mentes;

    public boolean isCancelled() {
        return cancelled;
    }

    public Berlo getBerlo() {
        return berlo;
    }

    public void setBerlo(Berlo berlo) {
        this.berlo = berlo;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void initData(Berlo b, Auto a) {
        this.berlo = b;
        this.auto = a;
        this.textArea_berles.setText(b.toString() + " - " + a.toString());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO   
        this.label_kolteg.setText("-------");
    }

    @FXML
    private void button_mentes_OnAction(ActionEvent event) throws IOException {
        boolean hiba = false;
        if (this.datePicker_kezdet.getValue().isAfter(this.datePicker_vege.getValue())) {
            hiba = true;
        }
        LinkedList<Berles> b = u.findBerles(this.berlo.getEmail());
        for (Berles berles : b) {
            if (this.datePicker_kezdet.getValue().isAfter(berles.getKezdet())
                    && this.datePicker_kezdet.getValue().isBefore(berles.getVege())) {
                hiba = true;
            }
            if (this.datePicker_vege.getValue().isAfter(berles.getKezdet()) && this.datePicker_vege.getValue().isBefore(berles.getVege())) {
                hiba = true;
            }
            if (hiba) {
                break;
            }
        }

        if (hiba) {
            u.Alert("Hiba!", "Nem lehet a bérletet menteni.", "Nem lehet az autó bérlésének időpontja fedésben");
        } else {
            u.addBerles(new Berles(this.berlo.getEmail(), this.auto.getRendszam(), this.datePicker_kezdet.getValue(), this.datePicker_vege.getValue(), this.koltseg));
            u.saveBerlesek();
            u.berlesAdatlap.close();
        }
    }

    @FXML
    private void datePicker_kezdet_OnAction(ActionEvent event) {
        boolean hiba = false;
        if (datePicker_kezdet.getValue() != null && this.datePicker_vege.getValue() != null) {
            if (this.datePicker_kezdet.getValue().isBefore(this.datePicker_vege.getValue())) {
                int nap = Period.between(this.datePicker_kezdet.getValue(), this.datePicker_vege.getValue()).getDays();
                this.koltseg = this.auto.getAr() * nap;
                if (nap >= 7) {
                    this.koltseg = this.koltseg - this.koltseg * 0.2;
                }
                this.label_kolteg.setText(this.koltseg + " HUF");
            } else {
                hiba = true;
            }
        } else {
            hiba = true;
        }
        if (hiba) {
            this.label_kolteg.setText("-------");
        }
    }

}
