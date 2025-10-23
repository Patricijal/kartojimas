package org.example.kartojimas;

import org.example.kartojimas.model.CreatureType;
import org.example.kartojimas.model.MagicalCreature;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    public static void writeUserToFile(MagicalCreature magicalCreature, String fileName) {
        ObjectOutputStream out = null;
        try(var file = new FileOutputStream(fileName)) {
            out = new ObjectOutputStream(new BufferedOutputStream(file));
            out.writeObject(magicalCreature);
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    public static List<MagicalCreature> readFromFile() {
        List<MagicalCreature> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("data.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitData = line.split(":");
                String title = splitData[0].trim();
                String[] splitDataFinal = splitData[1].split(";");
                CreatureType creatureType = CreatureType.valueOf(splitDataFinal[0].trim());
                String elixirs = splitDataFinal[1];
                LocalDate dateFound = LocalDate.parse(splitDataFinal[2].trim());
                String wizard = splitDataFinal[3] + " " + splitDataFinal[4];
                boolean isInDanger = splitDataFinal[5].trim().equals("taip");
                MagicalCreature magicalCreature = new MagicalCreature(title, creatureType, elixirs, dateFound, wizard, isInDanger);
                list.add(magicalCreature);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
