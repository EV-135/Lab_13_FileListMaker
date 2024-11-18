import java.util.Scanner;
public class SafeInput {
    Scanner pipe = new Scanner(System.in);

    public static String getNonZeroLenString(Scanner pipe, String prompt){
        String retString = "";

        do{
            System.out.println("\n"+prompt+": ");
            retString=pipe.nextLine();

        }while(retString.length()==0);

        return retString;
    }

    public static int getInt(Scanner pipe, String prompt){
        int retInt = 0;
        boolean done = false;
        String trash;

        do{
            System.out.println("\n"+prompt+": ");


            if (pipe.hasNextInt()){
                done = true;
                retInt = pipe.nextInt();
            }else{
                System.out.println("No integer found");
                trash = pipe.nextLine();
            }

        }while(!done);

        return retInt;
    }

    public static double getDouble(Scanner pipe, String prompt){
        double retDub = 0.0;
        boolean done=false;
        String trash;
        do {
            System.out.println("\n"+prompt+": ");

            if (pipe.hasNextDouble()) {
                done = true;
                retDub = pipe.nextDouble();
            }else{
                System.out.println("That is not a valid input");
                trash = pipe.nextLine();
            }
        }while(!done);

        return retDub;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){
        int retRangedInt = 0;
        boolean done = false;
        String trash;

        do{
            System.out.println("\n"+prompt+"["+low+"-"+high+"] :");

            if(pipe.hasNextInt()) {
                retRangedInt = pipe.nextInt();
                if (retRangedInt >= low && retRangedInt <= high) {
                    done = true;
                } else {
                    System.out.println("That is not a valid input");
                    trash = pipe.nextLine();
                }
            }
        }while(!done);

        return retRangedInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high){
        double retRangedDub = 0.0;
        boolean done = false;
        String trash;

        do{
            System.out.println("\n"+prompt+"["+low+"-"+high+"] : ");

            if(pipe.hasNextDouble()) {
                retRangedDub = pipe.nextDouble();
                if (retRangedDub >= low && retRangedDub <= high) {
                    done = true;
                } else {
                    System.out.println("That is not a valid input");
                    trash = pipe.nextLine();
                }
            }
        }while(!done);

        return retRangedDub;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt){
        String retAns="";
        boolean done = false;
        boolean conf = false;
        String trash="";
        do {
            trash = pipe.nextLine();
            System.out.println("\n"+prompt+": ");
            if(pipe.hasNextLine()) {
                retAns = pipe.nextLine();

                if (retAns.equals("Y") || retAns.equals("y")) {
                    done = true;

                } else if (retAns.equals("N") || retAns.equals("n")) {
                    done = true;
                    conf = true;
                } else {
                    System.out.println("That is not a valid input");
                    trash = pipe.nextLine();
                }
            }else{
                System.out.println("That is not a valid input");
                trash = pipe.nextLine();
            }

        }while(!done);

        return conf;

    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx){
        boolean done=false;
        String input;

        do{
            System.out.println("\n"+prompt+": ");
            input = pipe.nextLine();

            if(input.matches(regEx)){
                done = true;
            }else{
                System.out.println("That is an invalid input");
            }

        }while(!done);

        return input;
    }

    public static String prettyHeader(String msg){
        int totalWidth = 60;
        int padding = 3;
        int headerLength = totalWidth - (2 * padding);
        int spacesBefore=0;
        int spacesAfter=0;

        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();

        if (msg.length() <= headerLength) {
            spacesBefore = (headerLength - msg.length()) / 2;
            spacesAfter = headerLength - msg.length() - spacesBefore;
            System.out.print("***");
            for (int i = 0; i < spacesBefore; i++) {
                System.out.print(" ");
            }
            System.out.print(msg);
            for (int i = 0; i < spacesAfter; i++) {
                System.out.print(" ");
            }
            System.out.println("***");
        }

        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }

        return msg;
    }


}

