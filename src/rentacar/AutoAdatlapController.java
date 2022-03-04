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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Csernay Attila
 */
public class AutoAdatlapController implements Initializable {

    private Auto auto;
    private boolean cancel;
    private u.AblakMod mod;
    private String eredetiRendszam;

    @FXML
    private TextField textField_rendszam;
    @FXML
    private TextField textField_marka;
    @FXML
    private TextField textField_tipus;
    @FXML
    private Button button_megse;
    @FXML
    private Button button_save;
    @FXML
    private TextField textField_egysegar;

    public boolean isCancel() {
        return cancel;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
        if (this.auto != null && this.auto != new Auto()) {
            this.textField_rendszam.setText(this.auto.getRendszam());
            this.textField_marka.setText(this.auto.getMarka());
            this.textField_tipus.setText(this.auto.getTipus());
            if (this.auto.getAr() > 0) {
                this.textField_egysegar.setText(this.auto.getAr() + "");
            }
            this.eredetiRendszam = this.auto.getRendszam();
        }
    }

    public u.AblakMod getMod() {
        return mod;
    }

    public void setMod(u.AblakMod mod) {
        this.mod = mod;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.cancel = true;
    }

    @FXML
    private void button_megse_OnAction(ActionEvent event) {
        u.autoAdatlap.close();
    }

    @FXML
    private void button_save_OnAction(ActionEvent event) {
        this.cancel = false;
        boolean hiba = false;
        int ar = -1;
        if (this.mod == u.AblakMod.UJ) {
            if (!u.egyediRendszam(this.textField_rendszam.getText())) {
                hiba = true;
            }
        } else {
            if (!this.eredetiRendszam.equals(this.textField_tipus.getText())) {
                if (!u.egyediRendszam(this.textField_rendszam.getText())) {
                    hiba = true;
                }
            }
        }

        if (this.textField_rendszam.getText().isEmpty() || !this.textField_rendszam.getText().contains("-")) {
            hiba = true;
        }

        try {
            ar = Integer.parseInt(this.textField_egysegar.getText());
        } catch (Exception ex) {
            hiba = true;
        }

        if (hiba) {
            u.Alert("Hibás adatok", "Hibásan megadott, vagy hiányos az adatok kitöltése!", "A rendszám kitöltése kötelező, és kotelezö tartalmaznia a '-' karaktert!\r\nAz egység ár kitöltése kötelező és csak egész számokat tartalmazhat");
        } else {
            this.auto.setAr(ar);
            this.auto.setRendszam(this.textField_rendszam.getText());
            this.auto.setMarka(this.textField_marka.getText());
            this.auto.setTipus(this.textField_tipus.getText());
            u.autoAdatlap.close();
        }

    }

}
