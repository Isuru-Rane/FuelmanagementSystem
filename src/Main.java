import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static double remainingFuel = 0;
    public static Queue<Passenger> waitingQueue=new LinkedList<Passenger>();
    public static FuelQueue[] queues;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        queues=  initiateQueue();
        boolean bool = true;
        System.out.println("----Welcome to the Filling Station----\n Select From the menu\n");
        while (bool) {

            System.out.print("""
                    100 : View all fuel Queues
                    101 : View all empty Queues
                    102 : Add customer to a Queue
                    103 : Remove a customer from a Queue
                    104 : Remove a served Customer
                    105 : View customer sorted in alphabetical order
                    106 : Store program data into a file
                    107 : Load program data from a file
                    108 : View Remaining Fuel stock
                    109 : Add fuel stock
                    110 : Income of the each Fuel Queue
                    999 : Exit the program

                    Select :""");
            String menu = input.next();
            switch (menu) {
                case "100":
                case "VFQ":
                    viewAllFuelQueues(queues);
                    break;
                case "101":
                    viewAllEmptyQueues(queues);
                    break;
                case "102":
                    queues = addCustomer(input, queues);
                    break;
                case "103":
                    queues = removeCustomerFromQueue(input, queues);
                    break;
                case "104":
                    queues = removeServedCustomer(input, queues);
                    break;
                case "105":
                    viewCustomersInAlphabeticalOrder(queues);
                    break;
                case "106":
                    storeData(queues);
                    break;
                case "107":
                    queues = loadData();
                    break;
                case "108":
                    System.out.println("Remaining Fuel Stock: " + remainingFuel);
                    break;
                case "109":
                    addFuelStock(input);
                    break;
                case "110":
                    displayTotalIncome(queues);
                    break;
                case "999":
                    System.out.println("Exit");
                    bool = false;
                    break;
                default:
                    System.out.println("Invalid");
                    break;
            }
        }
    }

    //-----------------------------View All Fuel Queues--------------------------
    private static void viewAllFuelQueues(FuelQueue[] queues) {
        for (int i = 0; i < queues.length; i++) {
            System.out.println("Queue No." + queues[i].getQueueNo()+"\n");
            double totalIncome=0;
            for (int x = 0; x < queues[i].getPassengerQueue().length; x++) {
                if (queues[i].getPassengerQueue()[x] != null) {
                    System.out.println(
                            "First Name:" + queues[i].getPassengerQueue()[x].getFirstName() + "\nLast Name:" +
                                    queues[i].getPassengerQueue()[x].getSecondName() + "\nVehicle No:" +
                                    queues[i].getPassengerQueue()[x].getVehicleNo() + "\nNo.liters:" +
                                    queues[i].getPassengerQueue()[x].getNoLiters());
                    totalIncome+=(queues[i].getPassengerQueue()[x].getNoLiters()*430);
                } else {
                    System.out.println("Empty");
                }
            }
            System.out.println("Total income: "+totalIncome+"\n");
        }
    }


    //------------------------View all Empty Queues---------------------------------
    private static void viewAllEmptyQueues(FuelQueue[] queues) {
        for (int i = 0; i < queues.length; i++) {
            if (findEmptySlots(queues[i]) == 6) {
                System.out.println("Queue " + queues[i].getQueueNo());
            }
        }
    }


    //------------------------Add Customer to a Queue--------------------------------
    private static FuelQueue[] addCustomer(Scanner input, FuelQueue[] queues) throws IOException {
        boolean bool = true;
        while (bool) {
            if(remainingFuel==0){
                System.out.println("No Fuel remaining");
            }else{
                FuelQueue minimumQueue = findMinimumQueue(queues);
                System.out.print("Enter Customer First Name: ");
                String fName = input.next().toLowerCase();
                System.out.print("Enter Customer Last Name: ");
                String lName = input.next().toLowerCase();
                System.out.print("Enter Customer Vehicle No: ");
                String vehicleNo = input.next();
                boolean fuelLoop=true;
                double noLiters=0;
                while(fuelLoop){
                    System.out.print("Enter Customer Required Liters: ");
                    noLiters = input.nextDouble();
                    if(noLiters<=remainingFuel){
                        fuelLoop=false;
                    }else{
                        System.out.println("Cannot input that much fuel");
                    }
                }

                Passenger passenger = new Passenger(fName, lName, vehicleNo, noLiters);

                if(minimumQueue.isPassengerQueueFull()){
                    waitingQueue.add(passenger);
                    updateWaitingList();
                    System.out.println("Customer added to Waiting Queue");
                }else{
                    Passenger[] passengerQueue = minimumQueue.getPassengerQueue();
                    int emptySlots = findEmptySlots(minimumQueue);
                    passengerQueue[(passengerQueue.length - emptySlots)] = passenger;
                    minimumQueue.setPassengerQueue(passengerQueue);
                    int queueNo = minimumQueue.getQueueNo();
                    queues[(queueNo - 1)] = minimumQueue;
                    remainingFuel -= passenger.getNoLiters();
                }
            }
            System.out.println("");
            System.out.print("Do you want to add another customer(y /any letter ) :");

            String reply = input.next().toLowerCase();
            if (reply.equals("y")) {
                continue;
            } else  {
                bool = false;
            }
        }
        return queues;
    }


    //--------------------------Remove a customer from a Queue-------------------------
    private static FuelQueue[] removeServedCustomer(Scanner input, FuelQueue[] queues) throws IOException {
        System.out.print("Enter Queue No: ");
        int queueNo = input.nextInt();
        FuelQueue queue = queues[(queueNo - 1)];
        Passenger[] passengerQueue = queue.getPassengerQueue();
        passengerQueue[0] = null;
        for (int i = 0; i <= passengerQueue.length; i++) {
            if (i == (passengerQueue.length-1)) {
                break;
            } else if (passengerQueue[i] == null) {
                passengerQueue[i] = passengerQueue[i + 1];
                passengerQueue[i + 1] = null;
            }
        }
        passengerQueue[(passengerQueue.length-1)]=waitingQueue.remove();
        updateWaitingList();
        queue.setPassengerQueue(passengerQueue);
        queues[(queue.getQueueNo() - 1)] = queue;
        return queues;
    }


    //--------------------------Remove a served Customer-------------------------------
    private static FuelQueue[] removeCustomerFromQueue(Scanner input, FuelQueue[] queues) throws IOException {
        System.out.print("Enter Queue No: ");
        int queueNo = input.nextInt();
        FuelQueue queue = queues[(queueNo - 1)];
        System.out.print("Enter Customer Location: ");
        int passengerIndex = input.nextInt();
        Passenger[] passengerQueue = queue.getPassengerQueue();
        Passenger passenger = passengerQueue[passengerIndex - 1];
        remainingFuel += passenger.getNoLiters();
        passengerQueue[passengerIndex - 1] = null;
        for (int i = 0; i <= passengerQueue.length; i++) {
            if (i == (passengerQueue.length-1)) {
                break;
            } else if (passengerQueue[i] == null) {
                passengerQueue[i] = passengerQueue[i + 1];
                passengerQueue[i + 1] = null;
            }
        }
        passengerQueue[(passengerQueue.length-1)]=waitingQueue.remove();
        updateWaitingList();
        queue.setPassengerQueue(passengerQueue);
        queues[(queue.getQueueNo() - 1)] = queue;
        return queues;
    }



    //-------------------View customer sorted in alphabetical order------------------------
    private static void viewCustomersInAlphabeticalOrder(FuelQueue[] queues) {
        for (int i = 0; i < queues.length; i++) {
            System.out.println("Queue " + queues[i].getQueueNo());
            Passenger[] passengerQueue = queues[i].getPassengerQueue();
            String[] names = new String[6];
            int index = 0;
            for (int x = 0; x < passengerQueue.length; x++) {
                if (passengerQueue[x] != null) {
                    names[index] = passengerQueue[x].getFirstName() + " " + passengerQueue[x].getSecondName();
                } else {
                    names[index] = "empty";
                }
                index++;
            }
            for (int n = 0; n < names.length; n++) {
                for (int x = (n + 1); x < names.length; x++) {
                    if ((names[n] != null && names[x] != null) && names[n].compareTo(names[x]) > 0) {
                        String tempName = names[n];
                        names[n] = names[x];
                        names[x] = tempName;
                    }
                }
            }

            for (int v = 0; v < names.length; v++) {
                if (!names[v].equals("empty")) {
                    System.out.println(names[v]);
                }
            }
        }
    }


    //------------------------Store program data into a file-----------------------------
    private static void storeData(FuelQueue[] queues) throws IOException {
        FileWriter fileWriter = new FileWriter("fillingstation.txt");
        for (int i = 0; i < queues.length; i++) {
            fileWriter.write("Queue " + (i + 1) + ":");
            for (int x = 0; x < queues[i].getPassengerQueue().length; x++) {
                if (queues[i].getPassengerQueue()[x] != null) {
                    Passenger passenger = queues[i].getPassengerQueue()[x];
                    fileWriter.write(passenger.getFirstName() + " " + passenger.getSecondName() + "," + passenger.getVehicleNo() + "," + passenger.getNoLiters() + "/");
                } else {
                    fileWriter.write("empty/");
                }

            }
            fileWriter.write("\n");
        }
        fileWriter.close();
        System.out.println("Data stored successfully");
    }


    //------------------Load program data from a file----------------------------
    private static FuelQueue[] loadData() throws FileNotFoundException {
        File file = new File("fillingstation.txt");
        FuelQueue[] queues = new FuelQueue[5];
        int queueIndex=0;
        Scanner dataFile = new Scanner(file);
        while(dataFile.hasNextLine()){
            String s = dataFile.nextLine();
            Passenger[] passengers=new Passenger[6];
            int index=0;

            String[] split = s.split(":");
            String[] s1 = split[0].split(" ");
            FuelQueue fuelQueue=new FuelQueue(Integer.parseInt(s1[1]));

            String[] split1 = split[1].split("/");
            for(int i=0;i<split1.length-1;i++){
                if(split1[i].equals("empty")){
                    passengers[index]=null;
                }else{
                    String[] split2 = split1[i].split(",");
                    String[] name = split2[0].split(" ");
                    Passenger passenger=new Passenger(name[0],name[1],split2[1],Double.parseDouble(split2[2]));
                    passengers[index]=passenger;
                }
                index++;
            }
            fuelQueue.setPassengerQueue(passengers);
            queues[queueIndex]=fuelQueue;
            queueIndex++;
        }


        return queues;
    }



    //-------------------------Add fuel stock-------------------------
    private static void addFuelStock(Scanner input) {
        System.out.print("Enter new fuel stock: ");
        double stock = input.nextDouble();
        remainingFuel += stock;
    }


    //---------------------Income of the each Fuel Queue-----------------------
    private static void displayTotalIncome(FuelQueue[] queues) {

        for (int i = 0; i < queues.length; i++) {
            double totalIncome=0;
            for (int x = 0; x < queues[i].getPassengerQueue().length; x++) {
                if (queues[i].getPassengerQueue()[x] != null) {
                    totalIncome+=(queues[i].getPassengerQueue()[x].getNoLiters()*430);
                }
            }
            System.out.println("Queue "+queues[i].getQueueNo()+" total income: "+totalIncome);
        }
    }




    //---------------------Extra for Easy-------------------------------------
    //---------------------initiate the Queues---------------------------------
    private static FuelQueue[] initiateQueue() {
        FuelQueue[] queues = new FuelQueue[5];
        for (int i = 0; i < 5; i++) {
            FuelQueue fuelQueue = new FuelQueue((i + 1));
            queues[i] = fuelQueue;
        }
        return queues;
    }


    //------------------Find the minimum Queue---------------------------
    private static FuelQueue findMinimumQueue(FuelQueue[] queues) {
        FuelQueue queue = new FuelQueue();
        for (int i = 0; i < queues.length; i++) {

            if (queue.getQueueNo() == 0) {
                queue = queues[i];
            } else if (findEmptySlots(queues[i]) > findEmptySlots(queue)) { //Compare the n and n+1 Queues
                queue = queues[i];
            }

        }

        return queue;
    }

    private static int findEmptySlots(FuelQueue queue) {
        Passenger[] passengerQueue = queue.getPassengerQueue();
        int count = 0;
        for (int n = 0; n < passengerQueue.length; n++) {
            if (passengerQueue[n] == null) {
                count++;
            }
        }
        return count;
    }




    //--------------------Read the txt file----------------------
    public static void updateWaitingList() throws IOException {
        FileWriter fileWriter=new FileWriter("waitinglist.txt");
        for(Passenger passenger:waitingQueue){
            fileWriter.write(passenger.getFirstName()+" "+passenger.getSecondName()+","+passenger.getVehicleNo()+","+passenger.getNoLiters()+"\n");
        }
        fileWriter.close();
    }
}
