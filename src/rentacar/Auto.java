/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.util.Objects;

/**
 *
 * @author Csernay Attila
 */
public class Auto {

    private String rendszam;
    private String marka;
    private String tipus;
    private int ar;

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public Auto(String rendszam, String marka, String tipus, String ar) {
        this.rendszam = rendszam;
        this.marka = marka;
        this.tipus = tipus;
        this.ar = Integer.parseInt(ar);
    }

    public Auto() {
        this.rendszam = null;
        this.marka = null;
        this.tipus = null;
        this.ar = -1;
    }

    @Override
    public String toString() {
        return marka + " " + tipus + " (" + rendszam + ")";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auto other = (Auto) obj;
        if (!Objects.equals(this.rendszam, other.rendszam)) {
            return false;
        }
        return true;
    }
    
}
