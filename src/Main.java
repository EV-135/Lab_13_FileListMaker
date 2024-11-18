import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static Scanner in = new Scanner(System.in);
    private static String currentFileName = null;

    public static void main(String[] args) {
        boolean quit = false;

        do {
            displayMenu();
            String choice = SafeInput.getRegExString(in, "Enter your choice", "[AaDdIiMmOoSsCcVvQq]");
            try {
                switch (choice.toUpperCase()) {
                    case "A":
                        addItem();
                        break;
                    case "D":
                        deleteItem();
                        break;
                    case "I":
                        insertItem();
                        break;
                    case "M":
                        moveItem();
                        break;
                    case "O":
                        openFile();
                        break;
                    case "S":
                        saveFile();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "V":
                        viewList();
                        break;
                    case "Q":
                        quit = quitProgram();
                        break;
                }
            } catch (IOException e) {
                System.out.println("File operation error: " + e.getMessage());
            }
        } while (!quit);

        System.out.println("Goodbye!");
    }

    private static void displayMenu() {
        System.out.println("\nCurrent List:");
        viewList();
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file");
        System.out.println("S - Save the list");
        System.out.println("C - Clear the list");
        System.out.println("V - View the list");
        System.out.println("Q - Quit");
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter the item to add");
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }
        printNumberedList();
        int index = SafeInput.getRangedInt(in, "Enter the number of the item to delete", 1, list.size());
        list.remove(index - 1);
        needsToBeSaved = true;
        System.out.println("Item deleted.");
    }

    private static void insertItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Adding item as the first entry.");
            addItem();
            return;
        }
        printNumberedList();
        int index = SafeInput.getRangedInt(in, "Enter the position to insert the item (1-" + (list.size() + 1) + ")", 1, list.size() + 1);
        String item = SafeInput.getNonZeroLenString(in, "Enter the item to insert");
        list.add(index - 1, item);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }

    private static void moveItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
            return;
        }
        printNumberedList();
        int fromIndex = SafeInput.getRangedInt(in, "Enter the number of the item to move", 1, list.size());
        int toIndex = SafeInput.getRangedInt(in, "Enter the new position (1-" + list.size() + ")", 1, list.size());
        String item = list.remove(fromIndex - 1);
        list.add(toIndex - 1, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void openFile() throws IOException {
        if (needsToBeSaved && !confirmSaveBeforeAction()) return;

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the file name to open (without .txt)");
        File file = new File(fileName + ".txt");
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }
        list.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        }
        currentFileName = fileName;
        needsToBeSaved = false;
        System.out.println("File loaded.");
    }

    private static void saveFile() throws IOException {
        if (currentFileName == null) {
            currentFileName = SafeInput.getNonZeroLenString(in, "Enter the file name to save (without .txt)");
        }
        File file = new File(currentFileName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
        }
        needsToBeSaved = false;
        System.out.println("File saved.");
    }

    private static void clearList() {
        if (!list.isEmpty()) {
            if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list? (Y/N)")) {
                list.clear();
                needsToBeSaved = true;
                System.out.println("List cleared.");
            }
        } else {
            System.out.println("The list is already empty.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("[Empty List]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d: %s%n", i + 1, list.get(i));
            }
        }
    }

    private static boolean quitProgram() {
        if (needsToBeSaved) {
            boolean saveChanges = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save before quitting? (Y/N)");
            if (saveChanges) {
                try {
                    saveFile();
                } catch (IOException e) {
                    System.out.println("Error saving file: " + e.getMessage());
                    return false; 
                }
            } else {
                System.out.println("Changes will be discarded.");
            }
        }


        return SafeInput.getYNConfirm(in, "Are you sure you want to quit? (Y/N)");
    }


    private static boolean confirmSaveBeforeAction() {
        if (SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save before proceeding? (Y/N)")) {
            try {
                saveFile();
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    private static void printNumberedList() {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, list.get(i));
        }
    }
}
