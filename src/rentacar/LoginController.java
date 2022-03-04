/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Csernay Attila
 */
public class LoginController implements Initializable {

    private Label label;
    @FXML
    private TextField textField_login;
    @FXML
    private PasswordField passwordField_Login;
    @FXML
    private Button button_login;

    private String username = "";
    private String password = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File f = new File("login.ini");
        if (f.exists()) {
            try {
                Scanner s = new Scanner(f);
                while (s.hasNext()) {
                    String[] data = s.next().split("=");
                    switch (data[0].toLowerCase()) {
                        case "username":
                            this.username = new String(Base64.getDecoder().decode(data[1]));
                            break;
                        case "password":
                            this.password = new String(Base64.getDecoder().decode(data[1]));
                            break;
                    }
                }
            } catch (FileNotFoundException ex) {
                u.Alert("Hiba!", "Fájl beolvasása nem sikerült", ex.getMessage());
            }
        } else {
            u.Alert(
                    "Hiányzó állomány!",
                    "Hiányzó login állomány! A program kilép!",
                    "Hiányzó \"login.ini\" állomány miatt nem lehetséges a bejelentkezés.\r\n"
                    + "A program az ablak bezárása után azonnal kilép!"
            );
            Platform.exit();
        }
    }

    @FXML
    private void button_login_OnAction(ActionEvent event) throws Exception {
        boolean siker = true;

        if (!this.textField_login.getText().equals(this.username)) {
            siker = false;
        }
        if (!this.passwordField_Login.getText().equals(this.password)) {
            siker = false;
        }

        if (!siker) {
            u.Alert("Sikertelen bejelentkezés!", "Sikertelen bejelentkezés!\r\nA felhasználónév vagy jelszó helytelen!", "");
        } else {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Rent a Car - Main");
            u.main = stage;
            stage.show();
        }
    }
}
