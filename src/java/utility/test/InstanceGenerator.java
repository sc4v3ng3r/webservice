/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Vehicle;

/**
 *
 * @author scavenger
 */
public class InstanceGenerator {

    private static char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
        'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static char numbers[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public static final List<Client> getRandomClients(int number) {
     List<Client> list = new ArrayList<>();
        try {
            List<String> namesList = generateNames();
            Random random = new Random();
            for(int i=0; i < number; i++){
                Client c = new Client();
                c.setName(  namesList.get(  random.nextInt( namesList.size() )) );
                list.add(c);
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println( ex );
            
        }
     return list;
    }

    private static List<String> generateNames() throws FileNotFoundException{
        String basePath = System.getProperty("user.dir");
        String filePath = "/src/java/utility/test/wordlist.txt";
        BufferedReader br = new BufferedReader(new FileReader( basePath + filePath ));
        List<String> namesList = new ArrayList<>();
        
        try {
            String line = br.readLine();
            while (line != null) {
                namesList.add(line);
                line = br.readLine();
            } 
            br.close();
        }  
        catch(IOException ex){
            System.out.println(ex);
        }
        
        return namesList;
    }
    
    
    public static final Vehicle getRandomVehicle() {
        return new Vehicle(generateLicensePlate());
    }

    private static String generateLicensePlate() {
        Random random = new Random();
        String licensePlate = "";
        for (int counter = 0; counter < 3; counter++) {
            licensePlate += letters[random.nextInt(26)];
        }

        for (int counter = 0; counter < 4; counter++) {
            licensePlate += numbers[random.nextInt(10)];
        }

        return licensePlate;
    }

}
