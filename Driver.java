
/**
(1) Name: Dakota Morgan  
(2) Date: 11/4/2019 
(3) Instructor: Ms Tucker 
(4) Class: CIT249 Java II  
(5) Purpose: Display a GUI for editing an ArrayList and seeing the results displayed. 
    Will display confirmation before removing any record, and before saving changes.
    Writes to new Volunteers2.csv file.
**/

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver extends Application
{
    private static FileReader inFile = null;
    private static ArrayList<String> volunteerList;
    private static Scanner fileScan;
    private static PrintWriter outFile = null;

    public void start(Stage primaryStage) throws IOException
    {
        try
        {
            /** 
             * Create scanner object
             */
            fileScan = new Scanner(new File("Volunteers.csv"));
            
            /** 
             * Initialize new ArrayList for customer records
             */
            volunteerList = new ArrayList<String>();

            while (fileScan.hasNext())
            {
                /** 
                 * Read each line and store in volunteerList ArrayList
                 */
                volunteerList.add(fileScan.nextLine());
            }
        }
        finally
        {
            /** 
             * Close input file after processing
             */
            if (inFile != null)
            {
                inFile.close();
            }
        }
        
        /** 
         * Set scene on stage and set title.
         */
        Scene scene = new Scene(new VolunteerListPane(), 550, 500);

        primaryStage.setTitle("Volunteer List");
        primaryStage.setScene(scene);

        /** 
         * Show the Stage (window).
         */
        primaryStage.show();
    }
    
    public static void main (String[] args)
    {
        Application.launch(args);
    }
    
    /** 
     * Accessor for volunteerList
     */
    public final static ArrayList<String> getVolunteerList(){
        return volunteerList;
    }
    
    /** 
     * Get item from list
     */
    public final static String getVolunteerListItem(int index){
        return volunteerList.get(index);
    }
    
    /** 
     * Get list size
     */
    public final static int getVolunteerListSize(){
        return volunteerList.size();
    }
    
    /** 
     * Remove item from list
     */
    public final static void removeVolunteerListItem(int index){
        volunteerList.remove(index);
    }
    
    /** 
     * Add item to list
     */
    public final static void addVolunteerListItem(String record){
        volunteerList.add(record);
    }
    
    /** 
     * Create new Volunteers2 file with updated ArrayList
     */
    public final static void createOutputFile() throws IOException
    {
        try{
            outFile = new PrintWriter(new FileWriter("Volunteers2.csv"));
            
            for(int i = 0; i < volunteerList.size(); i++){
                outFile.println(volunteerList.get(i));
            }
        }
        finally{
            if (outFile != null)
            {
                outFile.close();
            }
        }
    }
}