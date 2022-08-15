import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args){
        GameProgress g1 = new GameProgress(90, 10, 8, 325);
        GameProgress g2 = new GameProgress(40, 20, 80, 425);
        GameProgress g3 = new GameProgress(10, 30, 120, 600);

        ArrayList<String> savesList = new ArrayList<String>();

        saveGame(g1, "save1.dat", savesList);
        saveGame(g2, "save2.dat", savesList);
        saveGame(g3, "save3.dat", savesList);

        zipFiles(savesList, "./zip_outzip.zip");

        deleteFiles(savesList);
    }

    public static void deleteFiles(List list){
        if (!list.isEmpty()){
            for (Object elem:list) {
                File file = new File(elem.toString());
                try {
                    Files.delete(file.toPath());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void saveGame(GameProgress g, String nameFiles, List savesList) {
        String pathToFile = "./Games/savegames/" + nameFiles;

        try (FileOutputStream fos = new FileOutputStream(pathToFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(g);

            savesList.add(pathToFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(ArrayList list, String pathToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for (Object elem:list) {
                String pathToFiles = elem.toString();
                FileInputStream fis = new FileInputStream(pathToFiles);
                ZipEntry entry = new ZipEntry(pathToFiles.substring(pathToFiles.lastIndexOf("/")+1,
                        pathToFiles.length()));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();

                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
