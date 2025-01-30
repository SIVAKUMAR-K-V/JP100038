package week3;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileOrganizer {
    private static final Map<String, String> FILE_TYPES = new HashMap<>();
    private static final List<String> organizationLog = new ArrayList<>();
    private Path sourcePath;
    private Path logPath;
    
    static {
        // Initialize file type mappings
        FILE_TYPES.put("docx,pdf,doc,txt", "Documents");
        FILE_TYPES.put("jpg,jpeg,png,gif", "Images");
        FILE_TYPES.put("mp3,wav,m4a,aac", "Audio");
        FILE_TYPES.put("mp4,mkv,mov,wmv", "Videos");
        FILE_TYPES.put("zip,rar,7z,tar,gz", "Archives");
        FILE_TYPES.put("exe,msi,app", "Applications");
    }

    public FileOrganizer(String sourcePath) {
        File file = new File(sourcePath);
        if (file.isFile()) {
            //use  parent directory for a file
            this.sourcePath = file.getParentFile().toPath();
            logOperation("Using parent directory: " + this.sourcePath);
        } else {
            // Give directory path
            this.sourcePath = Paths.get(sourcePath);
        }
        this.logPath = this.sourcePath.resolve("organization_log.txt");
    }

    public void organize(boolean removeDuplicates) {
        try {
            if (!Files.exists(sourcePath)) {
                throw new IllegalArgumentException("Source directory does not exist: " + sourcePath);
            }

            logOperation("Starting file organization process at: " + LocalDateTime.now());

            // Create category folders if they don't exist
            Map<String, Path> categoryPaths = createCategoryFolders();
            
            // Store file hashes if removing duplicates
            Set<String> fileHashes = new HashSet<>();

            // Process  files
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        processFile(file, categoryPaths, fileHashes, removeDuplicates);
                    }
                }
            }

            // Writes log files
            writeLog();
            
            logOperation("File organization completed successfully!");

        } catch (Exception e) {
            logOperation("Error during organization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Map<String, Path> createCategoryFolders() throws IOException {
        Map<String, Path> categoryPaths = new HashMap<>();
        
        for (String category : new HashSet<>(FILE_TYPES.values())) {
            Path categoryPath = sourcePath.resolve(category);
            Files.createDirectories(categoryPath);
            categoryPaths.put(category, categoryPath);
        }
        
        // Create "Other" folder for unknown file types
        Path othersPath = sourcePath.resolve("Others");
        Files.createDirectories(othersPath);
        categoryPaths.put("Others", othersPath);
        
        return categoryPaths;
    }

    private void processFile(Path file, Map<String, Path> categoryPaths, 
                           Set<String> fileHashes, boolean removeDuplicates) {
        try {
            String fileName = file.getFileName().toString();
            
            // Skip the log file 
            if (fileName.equals("organization_log.txt")) {
                return;
            }

            String extension = getFileExtension(fileName).toLowerCase();
            String category = determineCategory(extension);
            Path targetDir = categoryPaths.get(category);

            if (removeDuplicates) {
                String fileHash = calculateFileHash(file);
                if (fileHashes.contains(fileHash)) {
                    logOperation("Removing duplicate file: " + fileName);
                    Files.delete(file);
                    return;
                }
                fileHashes.add(fileHash);
            }

            // Moves file to directory
            Path targetPath = targetDir.resolve(fileName);
            Files.move(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
            logOperation("Moved file: " + fileName + " to " + category);

        } catch (IOException e) {
            logOperation("Error processing file " + file + ": " + e.getMessage());
        }
    }

    private String determineCategory(String extension) {
        for (Map.Entry<String, String> entry : FILE_TYPES.entrySet()) {
            if (entry.getKey().contains(extension)) {
                return entry.getValue();
            }
        }
        return "Others";
    }

    private String calculateFileHash(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(Files.readAllBytes(file));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new IOException("Error calculating file hash", e);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
    }

    private void logOperation(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        organizationLog.add(timestamp + " - " + message);
    }

    private void writeLog() throws IOException {
        Files.write(logPath, organizationLog, StandardOpenOption.CREATE, 
                   StandardOpenOption.TRUNCATE_EXISTING);
    }
}