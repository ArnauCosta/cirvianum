package classes;

import classes.*;
import com.sun.source.tree.SwitchTree;
import control.Control;

import java.io.*;
import java.util.ArrayList;

public class Fitxer {

    File fitxer, carpeta;
    FileWriter fwriter;
    BufferedWriter buffWriter;
    FileReader freader;
    BufferedReader buffReader;

    public File getFitxer() {
        return fitxer;
    }

    public File getCarpeta() {
        return carpeta;
    }

    public FileWriter getFwriter() {
        return fwriter;
    }

    public BufferedWriter getBuffWriter() {
        return buffWriter;
    }

    public FileReader getFreader() {
        return freader;
    }

    public BufferedReader getBuffReader() {
        return buffReader;
    }

    public Fitxer(Hotel h){
        creaDirectori();
        if(!crearFitxer("Client")){
            carregaDades(h,"Client");
        }
        if(!crearFitxer("Habitacio")){
            carregaDades(h,"Habitacio");
        }
        crearFitxer("idRef");
        if(!crearFitxer("Reserva")){
            carregaDades(h,"Reserva");
        }Reserva.setReservaIdCounter(cercaReservaMesAlta());
        if(!crearFitxer("Hotel")){
            carregaDades(h,"Hotel");
        }


    }

    public void update(){
        File fileTemp = new File("dades" + File.separator + "fitxer.txt");
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);
            fwriter = new FileWriter(fileTemp);
            buffWriter = new BufferedWriter(fwriter);

            String currentLine;
            while(true){
                try {
                    if (!((currentLine = buffReader.readLine()) !=null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void llegir() {
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentLine;
        while(true){
            try {
                if (!((currentLine = buffReader.readLine()) !=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void escriuFitxer() {
        try {
            fwriter = new FileWriter(fitxer, true);
            buffWriter = new BufferedWriter(fwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write("test");
            buffWriter.write(System.lineSeparator());
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean crearFitxer(String nomFitxer) {
        fitxer = new File("dades" + File.separator + nomFitxer + ".txt");
        if(fitxer.exists()){
            System.out.println("El fitxer " + nomFitxer + " ja existeix");
        }else{
            try {
                if(fitxer.createNewFile()){
                    System.out.println("Fitxer " + nomFitxer + "  creat correctament");
                    return true;
                }
            } catch (IOException e) {
                System.err.println("ERROR: No s'ha pogut crear el fitxer  " + nomFitxer + " .   -->   "+e);
                System.err.println("------------------------------------------------------------------------------");
                e.printStackTrace();
            }
        }
        return false;
    }

    private void creaDirectori() {
        carpeta = new File("dades");
        if(carpeta.mkdir()){
            System.out.println("Carpeta creada correctament");
        }else{
            System.err.println("ERROR: No s'ha pogut crear el directori");
        }
    }

    public static int cercaReservaMesAlta(){
        File fitxer = new File("dades" + File.separator + "Reserva.txt");
        FileReader freader = null; BufferedReader buffReader = null;
        int numActual = 0;
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);

            String currentLine = "";
            while(true) {
                try {
                    if (!((currentLine = buffReader.readLine()) !=null)) break;
                    int posibleSuperior = Integer.parseInt(currentLine.split("--")[0]);
                    if(numActual < posibleSuperior){
                        numActual = posibleSuperior;
                    }numActual++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            buffReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numActual;

    }

    public ArrayList<Integer> idRef() {
        File fitxer = new File("dades" + File.separator + "idRef.txt");
        ArrayList<Integer> a = new ArrayList<Integer>();
           String refString;
            try {
                freader = new FileReader(fitxer);
                buffReader = new BufferedReader(freader);

                try {
                    if ((refString = buffReader.readLine()) !=null){
                        for (int i = 0; i < refString.length(); i++) {
                            a.add(Character.getNumericValue(refString.charAt(i)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffReader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return a;
    }

    public void carregaDades(Hotel h, String tipus){
        File fitxer = new File("dades" + File.separator + tipus +".txt");
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);

            String currentLine = "";
            while(true){
                try {
                    if (!((currentLine = buffReader.readLine()) !=null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                switch(tipus.toLowerCase()){
                    case "habitacio":
                        // numHabitacio--numPersonesMax
                        String[] dadesHabitacio = currentLine.split("--");
                        Habitacio ha = new Habitacio(Integer.parseInt(dadesHabitacio[0]));
                        ha.setNumPersonesMax(Integer.parseInt(dadesHabitacio[1]));
                        h.addHabitacio(ha);
                        break;

                    case "client":
                        //  dni--nom--cog
                        String[] dadesClient = currentLine.split("--");
                        Client cl = new Client(dadesClient[0]);
                        cl.setNom(dadesClient[1]);
                        cl.setCog(dadesClient[2]);
                        h.addNouClient(cl);
                        break;

                    case "reserva":
                        //   idReserva--diaEntrada--numNits--numPersones--numHabitacio--dni--confirmada
                        String[] dadesReserva = currentLine.split("--");
                        Reserva reservaDesada = new Reserva(Integer.parseInt(dadesReserva[0]));
                        reservaDesada.setDiaEntrada(Control.arrayToLocalDate(dadesReserva[1].split("-")));
                        reservaDesada.setNumNits(Integer.parseInt(dadesReserva[2]));
                        reservaDesada.calculaDiaSortida();
                        reservaDesada.setNumPersones(Integer.parseInt(dadesReserva[3]));
                        reservaDesada.setHabitacio(Control.getHabitacioByNum(Integer.parseInt(dadesReserva[4])));
                        reservaDesada.setClient(Control.getClientByDni(dadesReserva[5]));
                        if(dadesReserva[6].equals("true"))reservaDesada.setConfirmada(true);
                        else{reservaDesada.setConfirmada(false);}
                        h.addReserva(reservaDesada);
                        break;

                    case "hotel":
                        h.setNomHotel(currentLine);
                        break;
                }
            }
            buffReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void desaHabitacio(Habitacio h) {
        File fitxer = new File("dades" + File.separator + "Habitacio.txt");
        try {
            fwriter = new FileWriter(fitxer, true);
            buffWriter = new BufferedWriter(fwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(h.toString());
            buffWriter.write(System.lineSeparator());
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualitzaHabitacio(int numHabitacio, int numPersones) {
        File fitxer = new File("dades" + File.separator + "Habitacio.txt");
        File fileTemp = new File("dades" + File.separator + "fitxerTemp.txt");
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);
            fwriter = new FileWriter(fileTemp);
            buffWriter = new BufferedWriter(fwriter);

            String currentLine;
            while(true){
                try {
                    if (!((currentLine = buffReader.readLine()) !=null)) break;
                        if(Integer.parseInt(currentLine.split("--")[0]) != numHabitacio){
                            buffWriter.write(currentLine);
                            buffWriter.write(System.lineSeparator());
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            buffWriter.write(numHabitacio+"--"+numPersones);
            buffWriter.close();
            buffReader.close();
            fitxer.delete();
            fileTemp.renameTo(fitxer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desaClient(Client c) {
        File fitxer = new File("dades" + File.separator + "Client.txt");
        try {
            fwriter = new FileWriter(fitxer, true);
            buffWriter = new BufferedWriter(fwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(c.toStringClie());
            buffWriter.write(System.lineSeparator());
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desaReserva(Reserva r) {
        File fitxer = new File("dades" + File.separator + "Reserva.txt");
        try {
            fwriter = new FileWriter(fitxer, true);
            buffWriter = new BufferedWriter(fwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(r.toStringRes());
            buffWriter.write(System.lineSeparator());
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminaReserva(int id) {
        File fitxer = new File("dades" + File.separator + "Reserva.txt");
        File fileTemp = new File("dades" + File.separator + "fitxerTemp.txt");
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);
            fwriter = new FileWriter(fileTemp);
            buffWriter = new BufferedWriter(fwriter);

            String currentLine;
            while(true){
                try {
                    if (!((currentLine = buffReader.readLine()) !=null)) break;
                    if(!currentLine.split("--")[0].equals(id+"")){
                        buffWriter.write(currentLine);
                        buffWriter.write(System.lineSeparator());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            buffWriter.close();
            buffReader.close();
            fitxer.delete();
            fileTemp.renameTo(fitxer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desaIdRef(int id) {
        File fitxer = new File("dades" + File.separator + "idRef.txt");
        try {
            fwriter = new FileWriter(fitxer, true);
            buffWriter = new BufferedWriter(fwriter);

            try {
                buffWriter.write(id+"");
                buffWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void eliminaIdRef(int id) {
        fitxer = new File("dades" + File.separator + "idRef.txt");
        File fileTemp = new File("dades" + File.separator + "fitxerTemp.txt");
        try {
            freader = new FileReader(fitxer);
            buffReader = new BufferedReader(freader);
            fwriter = new FileWriter(fileTemp);
            buffWriter = new BufferedWriter(fwriter);
            String currentLine;
                try {
                    if ((currentLine = buffReader.readLine()) !=null){
                        for (int i = 0; i < currentLine.length(); i++) {
                            if(Character.getNumericValue(currentLine.charAt(i))!=id){
                                System.out.println(id);
                                buffWriter.write(currentLine.charAt(i));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            buffWriter.close();
            buffReader.close();
            fitxer.delete();
            fileTemp.renameTo(fitxer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desaNomHotel(String nom) {
        File fitxer = new File("dades" + File.separator + "Hotel.txt");
        try {
            fwriter = new FileWriter(fitxer, false);
            buffWriter = new BufferedWriter(fwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(nom);
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
