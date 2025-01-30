package week3;


import java.io.File; 

public class TestFileOrganizer {
    public static void main(String[] args) {
        try {
            // Provide the  folder path
            String directoryPath = "C:\\Users\\User\\Downloads\\Applications";
            
            System.out.println("Starting organization...");
            
            // Verify if directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                System.err.println("Error: Directory does not exist: " + directoryPath);
                return;
            }
            
            // Print the contents of the directory
            System.out.println("\nFiles in directory before organization:");
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }
            
            FileOrganizer organizer = new FileOrganizer(directoryPath);
            organizer.organize(false);
            
            System.out.println("\nOrganization complete!");
            System.out.println("Check the following folders in your Downloads directory:");
            System.out.println("- Documents");
            System.out.println("- Images");
            System.out.println("- Audio");
            System.out.println("- Videos");
            System.out.println("- Archives");
            System.out.println("- Applications");
            System.out.println("- Others");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
