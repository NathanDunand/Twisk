package twisk.outils;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {

    public KitC() {}

    public void creerEnvironnement() {

        try {

            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));

            String[] liste = {"programmeC.o", "def.h", "codeNatif.o"};
            for (String nom : liste){
                InputStream source = getClass().getResource("/codeC/"+nom).openStream();
                File destination = new File("/tmp/twisk/"+nom);
                copier(source,destination);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void creerFichier(String codeC) throws IOException {
        try {
            FileWriter writer = new FileWriter("/tmp/twisk/client.c");
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(codeC);
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compiler(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o");
            p.waitFor();
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String ligne;
            while ((ligne = output.readLine()) != null){
                System.out.println(ligne);
            }

            while ((ligne = error.readLine()) != null){
                System.out.println(ligne);
            }
        } catch (IOException | InterruptedException e ){
            e.printStackTrace();
        }
    }

    public void construireLaLibrairie(){
        Runtime runtime = Runtime.getRuntime();
        try {
            String cmd="gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk"+FabriqueNumero.getInstance().getNumLibTwiskSuivant()+".so";
            Process p = runtime.exec(cmd);
            p.waitFor();
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            //Thread.sleep(1000000);

            String ligne;
            while ((ligne = output.readLine()) != null){
                System.out.println(ligne);
            }

            while ((ligne = error.readLine()) != null){
                System.out.println(ligne);
            }
        } catch (IOException | InterruptedException e ){
            e.printStackTrace();
        }
    }

    public void copier(InputStream source, File dest) throws IOException {
        InputStream sourceFile = source;
        OutputStream destinationFile = new FileOutputStream(dest);

        byte buffer[] = new byte[512*1024];
        int nbLecture;
        while((nbLecture=sourceFile.read(buffer)) != -1){
            destinationFile.write(buffer, 0,nbLecture);
        }
        destinationFile.close();
        sourceFile.close();
    }
}
