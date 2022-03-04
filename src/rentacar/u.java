/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Csernay Attila
 */
public class u {

    public static Stage login;
    public static Stage main;
    public static Stage berloAdatlap;
    public static Stage autoAdatlap;
    public static Stage berlesAdatlap;
    private static LinkedList<Berlo> berlok;
    private static LinkedList<Auto> autok;
    private static LinkedList<Berles> berlesek;

    public static void addBerles(Berles b) {
        if (berlesek == null) {
            berlesek = new LinkedList<Berles>();
        }
        berlesek.add(b);
    }

    public static LinkedList<Berles> getBerlesek() {
        if (berlesek == null) {
            berlesek = new LinkedList<Berles>();
        }
        return berlesek;
    }

    public static LinkedList<Auto> getAutok() {
        if (autok == null) {
            autok = new LinkedList<Auto>();
        }
        return autok;
    }

    public static void addAuto(Auto a) {
        if (autok == null) {
            autok = new LinkedList<Auto>();
        }
        autok.add(a);
    }

    public static LinkedList<Berlo> getBerlok() {
        if (berlok == null) {
            berlok = new LinkedList<Berlo>();
        }
        return berlok;
    }

    public static void addBerlo(Berlo b) {
        if (berlok == null) {
            berlok = new LinkedList<Berlo>();
        }
        berlok.add(b);
    }

    public static void Alert(String title, String headerText, String contentText) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }

    public static boolean egyediEmail(String email) {
        if (berlok == null) {
            berlok = new LinkedList<>();
        }

        for (Berlo berlo : berlok) {
            if (berlo.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static boolean egyediRendszam(String email) {
        if (autok == null) {
            autok = new LinkedList<>();
        }

        for (Auto auto : autok) {
            if (auto.getRendszam().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static void saveBerlok() throws IOException {
        File b = new File("berlok.dat");

        if (!b.exists()) {
            b.createNewFile();
        }

        FileWriter fw = new FileWriter(b);
        for (Berlo berlo : berlok) {
            fw.append(safeSave(berlo.getNev()) + "#" + safeSave(berlo.getEmail()) + "\r\n");
        }
        fw.flush();
        fw.close();
    }

    public static void saveAutok() throws IOException {
        File a = new File("autok.dat");
        if (!a.exists()) {
            a.createNewFile();
        }

        FileWriter fw = new FileWriter(a);
        for (Auto auto : autok) {
            fw.append(safeSave(auto.getRendszam()) + "#" + safeSave(auto.getMarka()) + "#" + safeSave(auto.getTipus()) + "#" + auto.getAr() + "\r\n");
        }
        fw.flush();
        fw.close();
    }

    public static void saveBerlesek() throws IOException {
        File a = new File("berlesek.dat");
        if (!a.exists()) {
            a.createNewFile();
        }

        FileWriter fw = new FileWriter(a);
        for (Berles berles : berlesek) {
            String d1 = berles.getKezdet().toString();
            String d2 = berles.getVege().toString();
            fw.append(
                    safeSave(berles.getBerlo()) + "#"
                    + safeSave(berles.getAuto()) + "#"
                    + safeSave(d1) + "#"
                    + safeSave(d2) + "#"
                    + berles.getKoltseg() + "\r\n"
            );
        }
        fw.flush();
        fw.close();
    }

    public static void loadBerlesek() throws FileNotFoundException {
        File b = new File("berlesek.dat");
        if (b.exists()) {
            Scanner s = new Scanner(b);
            while (s.hasNext()) {
                String[] data = s.next().split("#");

                if (data.length > 1) {
                    String b1 = safeOpen(data[0]);
                    String r = safeOpen(data[1]);
                    //String[] ds1 = safeOpen(data[2]).split("-");
                    //Date d1 = new Date(Integer.parseInt(ds1[0]), Integer.parseInt(ds1[1]), Integer.parseInt(ds1[2]));
                    LocalDate d1 = LocalDate.parse(data[2]);
                    LocalDate d2 = LocalDate.parse(data[3]);
                    //String[] ds2 = safeOpen(data[3]).split("-");
                    //Date d2 = new Date(Integer.parseInt(ds2[0]), Integer.parseInt(ds2[1]), Integer.parseInt(ds2[2]));
                    double k = Double.parseDouble(data[4]);
                    u.addBerles(new Berles(b1, r, d1, d2, k));
                }
            }
        }
    }

    public static void loadBerlok() throws FileNotFoundException {
        File b = new File("berlok.dat");
        if (b.exists()) {
            Scanner s = new Scanner(b);
            while (s.hasNext()) {
                String[] data = s.next().split("#");

                if (data.length > 1) {
                    u.addBerlo(new Berlo(safeOpen(data[0]), safeOpen(data[1])));
                }
            }
        }
    }

    public static void loadAutok() throws FileNotFoundException {
        File a = new File("autok.dat");
        if (a.exists()) {
            Scanner s = new Scanner(a);
            while (s.hasNext()) {
                String string = s.next().replace("_", " ");
                String[] data = string.split("#");
                System.out.println(string);
                if (data.length > 2) {
                    u.addAuto(new Auto(safeOpen(data[0]), safeOpen(data[1]), safeOpen(data[2]), safeOpen(data[3])));
                }
            }
        }
    }

    private static String safeSave(String s) {
        return s.replace("#", "&hess;").replace("_", "&under;").replace(" ", "_");
    }

    private static String safeOpen(String s) {
        return s.replace("&hess;", "#").replace("&under;", "_").replace("_", " ");
    }

    public static Berlo findBerlo(String email) {
        for (Berlo berlo : berlok) {
            if (berlo.getEmail().equals(email)) {
                return berlo;
            }
        }
        return null;
    }

    public static LinkedList<Berles> findBerles(String email) {
        LinkedList<Berles> b = new LinkedList<>();
        if (u.berlesek == null) {
            u.berlesek = new LinkedList<>();
        }
        for (Berles berles : u.berlesek) {
            if (berles.getBerlo().equals(email)) {
                b.add(berles);
            }
        }
        return b;
    }

    public static Auto findAuto(String rendszam) {
        for (Auto auto : autok) {
            if (auto.getRendszam().equals(rendszam)) {
                return auto;
            }
        }
        return null;
    }

    public static enum AblakMod {
        UJ, SZERKESZT
    }

    public static LinkedList<Berlo> berloFilter(String filter) {
        LinkedList<Berlo> b = new LinkedList<>();
        for (Berlo berlo : berlok) {
            if (berlo.getEmail().toLowerCase().contains(filter.toLowerCase()) || berlo.getNev().toLowerCase().contains(filter.toLowerCase())) {
                b.add(berlo);
            }
        }
        return b;
    }

    public static LinkedList<Auto> autoFilter(String filter) {
        LinkedList<Auto> a = new LinkedList<>();
        for (Auto auto : autok) {
            String s = new String(auto.getAr() + "");
            if (auto.getRendszam().toLowerCase().contains(filter.toLowerCase())
                    || auto.getMarka().toLowerCase().contains(filter.toLowerCase())
                    || auto.getTipus().toLowerCase().contains(filter.toLowerCase())
                    || s.toLowerCase().contains(filter.toLowerCase())) {
                a.add(auto);
            }
        }
        return a;
    }
}
