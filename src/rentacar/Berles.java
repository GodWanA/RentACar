/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Csernay Attila
 */
public class Berles {

    private String berlo;
    private String auto;
    private LocalDate kezdet;
    private LocalDate vege;
    private double koltseg;

    public String getBerlo() {
        return berlo;
    }

    public String getAuto() {
        return auto;
    }

    public LocalDate getKezdet() {
        return kezdet;
    }

    public LocalDate getVege() {
        return vege;
    }

    public double getKoltseg() {
        return koltseg;
    }

    public Berles() {
        this.berlo = null;
        this.auto = null;
        this.kezdet = null;
        this.vege = null;
    }

    public Berles(String email, String rendszam, LocalDate kezdet, LocalDate vege, double koltseg) {
        this.berlo = email;
        this.auto = rendszam;
        this.kezdet = kezdet;
        this.vege = vege;
        this.koltseg = koltseg;
    }

    public String getMarka() {
        return u.findAuto(this.auto).getMarka();
    }

    public String getRendszam() {
        return u.findAuto(this.auto).getRendszam();
    }

    public String getTipus() {
        return u.findAuto(this.auto).getTipus();
    }
}
