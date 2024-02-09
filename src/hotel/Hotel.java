package hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Application to manage an hotel The functionalities are: add customers, display empty rooms, view all rooms, delete customers, find rooms by the name of the customer Stores and Loads information from a file Orders guests ascending alphabetically by their names
 *
 * @author cSilva
 */
public class Hotel {

    static final int NUMBER_OF_ROOMS = 8;
    static final int NUMBER_OF_ROOMS_ARRAY = 8;
    static final int QUEUE_LENGTH = 5;
    final static int INITIALISE_FRONT_END = -1;
    static int end = INITIALISE_FRONT_END;
    static int front = INITIALISE_FRONT_END;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Room[] hotelRooms = new Room[NUMBER_OF_ROOMS];
        Person[] personDetails = new Person[NUMBER_OF_ROOMS];
        Queue[] customerQueue = new Queue[QUEUE_LENGTH];

        for (int i = 0; i < hotelRooms.length; i++) {
            hotelRooms[i] = new Room();
            personDetails[i] = new Person();
        }
        for (int i = 0; i < customerQueue.length; i++) {
            customerQueue[i] = new Queue();
        }
        //boolean to break the while loop
        boolean continueRunning = true;
        int nGuests = 0;

        //final array with the menu option allowed to run the application
        final char[] MENU_OPTIONS = {'A', 'E', 'D', 'F', 'S', 'L', 'O', 'V', 'Q'};
        final int MIN_ROOM_CAPACITY_GUEST = 0;
        final int MAX_ROOM_CAPACITY_GUEST = 5;
        final int MAX_HOTEL_CAPACITY = 8;
        final int MIN_HOTEL_CAPACITY = 1;
        final String MIS_MATCH_ERROR_MESSAGE = "\nInvalid input.\nPlease enter an Integer number\n     NullZeroHotel";
        final int ONE_POSITION_DOWN = 1;
        final int ONE_POSITION_UP = 1;
        final String EMPTY_ROOM = "E";
        final int EMPTY_HOTEL = 0;
        String customerFirstName = "E";
        String customerSurname = "E";
        String customerCreditCardNumber = "E";

        //main loop with a menu working along with a switch case 
        //verifies which option the user has chosen
        loadDataFromFile(hotelRooms, personDetails);
        do {
            menu();
            char option = scan.next().charAt(0);
            option = Character.toUpperCase(option);

            if (containsValue(MENU_OPTIONS, option)) {
                switch (option) {
                    case 'A':
                        System.out.println("\n");

                        int selectedRoom = 0;
                        boolean response = true;
                        boolean isInRange = false;
                        int getIfFull = getIndex(hotelRooms, EMPTY_ROOM);

                        if (getIfFull <= MAX_HOTEL_CAPACITY) {
                            do {
                                System.out.println("Available Rooms: ");
                                displayArrayEmpty(hotelRooms);
                                System.out.println("\nAdd customer to room");
                                System.out.print("Enter the requested room: ");
                                if (scan.hasNextInt()) {
                                    selectedRoom = scan.nextInt();
                                    if (!verifyIfIndexIsEmpty(hotelRooms, selectedRoom)) {
                                        System.out.println("Room " + selectedRoom + " is not valid. \nPlease, limit your choices between 1 to 8.\n");
                                    } else {
                                        isInRange = true;
                                    }
                                } else {
                                    System.out.println(MIS_MATCH_ERROR_MESSAGE);
                                    scan.next();
                                }
                            } while (!isInRange);

                            System.out.println("\n\nAdd customer to room\n");
                            System.out.print("First name: ");
                            customerFirstName = scan.next();

                            System.out.print("Surname: ");
                            customerSurname = scan.next();

                            System.out.print("Credit Card Number: ");
                            customerCreditCardNumber = scan.next();
                            response = false;

                            do {
                                System.out.println("\nRooms capacity: 0 - 5 guests allowed");
                                System.out.print("Number of Guests: ");
                                if (scan.hasNextInt()) {
                                    nGuests = scan.nextInt();
                                    if (!verifyRangeRoom(nGuests)) {
                                        System.out.println("\nInvalid number of guests.");
                                        System.out.println("Please, try again.");
                                        response = true;
                                    } else {
                                        response = false;
                                    }
                                } else {
                                    System.out.println(MIS_MATCH_ERROR_MESSAGE);
                                    scan.next();
                                }
                            } while (response);
                            customerFirstName = customerFirstName.toUpperCase();
                            customerSurname = customerSurname.toUpperCase();
                            if ((addToArray(hotelRooms, customerFirstName, nGuests, selectedRoom)) && (addToArray(personDetails, customerFirstName, customerSurname, customerCreditCardNumber, selectedRoom))) {
                                System.out.print("Customer, " + customerFirstName + " entered with success!");
                            } else {
                                System.out.println("\nWe are sorry to inform that we are fully booked. \nCustomer, " + customerFirstName + " will be added to the queue");
                            }
                        } else {
                            System.out.println("There are no rooms available.\nCustomer will be added to the queue.");
                            System.out.println("\nAdd customer to the queue\n");
                            System.out.print("First name: ");
                            customerFirstName = scan.next();

                            System.out.print("Surname: ");
                            customerSurname = scan.next();

                            System.out.print("Credit Card Number: ");
                            customerCreditCardNumber = scan.next();
                            response = true;
                            do {
                                System.out.println("\nRooms capacity: 0 - 5 guests allowed");
                                System.out.print("Number of Guests: ");
                                if (scan.hasNextInt()) {
                                    nGuests = scan.nextInt();
                                    if (!verifyRangeRoom(nGuests)) {
                                        System.out.println("\nInvalid number of guests.");
                                        System.out.println("Please, try again.");
                                    } else {
                                        response = false;
                                    }
                                } else {
                                    System.out.println(MIS_MATCH_ERROR_MESSAGE);
                                    scan.next();
                                }
                            } while (response);
                            customerFirstName = customerFirstName.toUpperCase();
                            customerSurname = customerSurname.toUpperCase();
                            if (addToQueue(customerQueue, customerFirstName, customerSurname, customerCreditCardNumber, nGuests)) {
                                System.out.println("Customer, " + customerFirstName + " " + customerSurname + " was added to the Queue.");
                            } else {
                                System.out.println("\nWe are sorry to inform, our queue is currently full. \nPlease, try again later.\n     NullZeroHotel");
                            }
                        }
                        break;
                    case 'E':
                        System.out.println("\n\nEmpty rooms\n");
                        getEmptySlots(hotelRooms);
                        break;

                    case 'D':
                        System.out.println("\n\nDelete customer from a room\n");
                        if(displayArrayNotEmpty(hotelRooms, personDetails) != EMPTY_HOTEL){
                            boolean validInput = false;
                            do {
                                System.out.print("\nWhich room would you like to set as empty? ");
                                if (scan.hasNextInt()) {
                                    int deleteRoom = scan.nextInt();
                                    deleteRoom--;
                                    if (removeFromArray(hotelRooms, personDetails, deleteRoom)) {
                                        System.out.println("The client from Room " + (deleteRoom + ONE_POSITION_UP) + " checked out with success!");
                                        removeFromQueue(customerQueue, personDetails, hotelRooms, deleteRoom);
                                        validInput = true;
                                    } else {
                                        System.out.println("\nThe customer chosen does not exist. Please, try again.");
                                    }
                                } else {
                                    System.out.println(MIS_MATCH_ERROR_MESSAGE);
                                    scan.next();
                                }
                            } while (!validInput);
                        }else {
                            System.out.println("\nAll rooms are empty. The operation is not valid.\n");
                        }
                        break;

                    case 'F':
                        System.out.println("\n\nFind room number by Customer name");
                        System.out.print("\nFirst name: ");
                        customerFirstName = scan.next();
                        customerFirstName = customerFirstName.toUpperCase();

                        System.out.print("Surname: ");
                        customerSurname = scan.next();
                        customerSurname = customerSurname.toUpperCase();
                        System.out.println("");
                        int getRoomNumber = getIndex(personDetails, customerFirstName, customerSurname);
                        if (getRoomNumber <= NUMBER_OF_ROOMS) {
                            System.out.println("The customer, " + personDetails[getRoomNumber - ONE_POSITION_DOWN].getFirstName() + " " + personDetails[getRoomNumber - ONE_POSITION_DOWN].getSurname() + ", is at Room number " + getRoomNumber);
                        } else {
                            System.out.println("The customer " + customerFirstName + " " + customerSurname + " is not on our booking system.");
                        }
                        break;

                    case 'S':
                        System.out.println("\n\nStore program data into file");
                        if (storeToFile(hotelRooms, personDetails)) {
                            System.out.println("Data was saved successfully!\n");
                        } else {
                            System.out.println("Error occured! Please, try again later.\n");
                        }
                        break;

                    case 'L':
                        System.out.println("\n\nLoad program data from file");
                        loadDataFromFile(hotelRooms, personDetails);
                        break;

                    case 'O':
                        System.out.println("\n\nView guests ordered alphabetically by name");
                        orderArrayAlphabetically(personDetails);
                        break;
                    case 'V':
                        System.out.println("\n\nViewer\n");
                        response = true;
                        do {
                            System.out.println("What would you like to view?");
                            System.out.println("___________________________________________");
                            System.out.println("   1 - View All Rooms\n   2 - View Queue");
                            System.out.println("___________________________________________");
                            System.out.print("Option: ");
                            if (scan.hasNextInt()) {
                                int viewerOption = scan.nextInt();
                                if ((viewerOption == 1) || (viewerOption == 2)) {
                                    switch (viewerOption) {
                                        case 1:
                                            System.out.println("");
                                            viewArray(hotelRooms, personDetails);
                                            response = true;
                                            break;
                                        case 2:
                                            System.out.println("");
                                            viewQueue(customerQueue);
                                            response = true;
                                            break;
                                    }
                                } else {
                                    System.out.println("\nOption chosen is not valid. \nPlease, try again.\n");
                                    response = false;
                                }
                            } else {
                                response = false;
                                System.out.println(MIS_MATCH_ERROR_MESSAGE);
                                System.out.println("");
                                scan.next();
                            }
                        } while (!response);
                        //add power of choice between rooms and queue

                        System.out.println("\n\n");

                        break;
                    case 'Q':
                        System.out.println("\n\n    Goodbye!");
                        System.out.print("  NullZero Hotel\n\n");
                        continueRunning = false;
                        break;
                    default:
                        System.out.println("Error!");
                }
            } else {
                System.out.println("\n\n" + option + " is not valid! \nPlease, enter a valid option." + "\n\n");
            }
        } while (continueRunning);
    }

    /**
     * Displays the menu
     */
    public static void menu() {
        System.out.println("\n\n             Welcome to NullZero Hotel!");
        System.out.println("_______________________________________________________");
        System.out.println("   Customer management:");
        System.out.println("     A:   Add customer to a room");
        System.out.println("     E:   Display Empty rooms");
        System.out.println("     D:   Delete customer from a room");
        System.out.println("     F:   Find room from customer name");
        System.out.println("     O:   View guests ordered alphabetically by name");
        System.out.println("     V:   View all rooms / Queue");
        System.out.println("\n   Data management:");
        System.out.println("     S:   Store program data into file");
        System.out.println("     L:   Load program data from file\n");
        System.out.println("     Q:   Quit application");
        System.out.println("_______________________________________________________");
        System.out.print("     Option: ");
    }

    /**
     * displays an array of Room, listing the position and value where the array is not empty
     *
     * @param rooms String array to be displayed
     */
    public static void displayArrayEmpty(Room[] rooms) {
        int roomCounter = 1;
        int emptyRoom = 0;
        final int NONE = 0;

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getFirstName().equals("E")) {
                System.out.println("Room number: " + roomCounter + "\n");
                emptyRoom++;
            }
            roomCounter++;
        }
        if (emptyRoom == NONE) {
            System.out.println("\nNo rooms available!");
            System.out.println("\nEnter the customer details to be added to the queue list.\nPlease enter the details.");
        }
    }

    /**
     * method to verify if a room Number is empty or not
     *
     * @param rooms array of type Rooms
     * @param roomNumber Integer containing the room number
     * @return boolean
     */
    public static boolean verifyIfIndexIsEmpty(Room[] rooms, int roomNumber) {
        final int FIRST_ROOM_AVAILABLE = 0;
        final int LAST_LAST_AVAILABLE = NUMBER_OF_ROOMS_ARRAY - 1;

        if ((roomNumber - 1 < FIRST_ROOM_AVAILABLE) || (roomNumber - 1 > LAST_LAST_AVAILABLE)) {
            return false;
        } else if (rooms[roomNumber - 1].getFirstName().equals("E")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * verify if a char belongs to an array of chars.
     *
     * @param charArray the array to search
     * @param option the option chosen by the user
     * @return boolean response: true if it contains, false if it does not contain
     */
    public static boolean containsValue(char charArray[], char option) {
        for (char optionArray : charArray) {
            if (optionArray == option) {
                return true;
            }
        }
        return false;
    }

    /**
     * displays the empty position of an array of Rooms
     *
     * @param rooms array of Rooms to be searched and printed if its position is empty
     */
    public static void getEmptySlots(Room rooms[]) {
        int emptyRooms = 0;
        int roomCounter = 1;

        String temp = "";
        for (Room customer : rooms) {
            temp = customer.getFirstName();
            if (temp.equals("E")) {
                System.out.println("Room number: " + roomCounter);
                emptyRooms++;
            }
            roomCounter++;
        }
        if (emptyRooms == 0) {
            System.out.println("All rooms are full.");
        }

    }

    /**
     * displays an array, listing the position and value where the array is not empty
     *
     * @param rooms Array of type Room
     * @param personDetails Array of type Person
     */
    public static int displayArrayNotEmpty(Room[] rooms, Person[] personDetails) {
        int roomCounter = 1;
        int numberOfRoomsAvailable = 0;
        for (int i = 0; i < rooms.length; i++) {
            if (!personDetails[i].getFirstName().equals("E")) {
                System.out.println("Room number: " + roomCounter + "\n    First Name: " + personDetails[i].getFirstName() + "\n    Surname: " + personDetails[i].getSurname() + "\n");
                numberOfRoomsAvailable++;
            }
            roomCounter++;
        }
        return numberOfRoomsAvailable;
    }

    /**
     * Displays an array not empty
     *
     * @param orderAlphabetical Array of Person to be displayed
     * @param personDetails Array of Person, containing all of the data, which will be used to verify if position is empty or not
     */
    public static void displayArrayOrder(Person[] orderAlphabetical, Person[] personDetails) {

        for (int i = 0; i < orderAlphabetical.length; i++) {
            int getRoom = getIndex(personDetails, orderAlphabetical[i].getFirstName(), orderAlphabetical[i].getSurname());
            if (!orderAlphabetical[i].getFirstName().equals("E")) {
                System.out.println("Room number: " + getRoom);
                System.out.println("     First name: " + orderAlphabetical[i].getFirstName() + "\n     Surname: " + orderAlphabetical[i].getSurname() + "\n");
            }
        }
    }

    /**
     * Boolean method that removes an element from the array, inserts an E on the position chosen or 0 if it type is integer
     *
     * @param personDetails Array of type Person
     * @param rooms Array of type Rooms
     * @param position index to be removed
     * @return true if an error was not found, false if the index is out of boundaries
     */
    public static boolean removeFromArray(Room rooms[], Person[] personDetails, int position) {
        try {
            rooms[position].setName("E");
            rooms[position].setNumberOfGuests(0);
            personDetails[position].setFirstName("E");
            personDetails[position].setSurname("E");
            personDetails[position].setCreditCardNumber("E");
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    /**
     * searches the array and finds the position of the value chosen by the user
     *
     * @param rooms Array of Room
     * @param firstName String to search
     * @return position of the name sent to the method
     */
    public static int getIndex(Room[] rooms, String firstName) {
        int roomCounter = 1;
        String temp = "";
        for (Room customer : rooms) {
            temp = customer.getFirstName();
            if (temp.equals(firstName)) {
                return roomCounter;
            }
            roomCounter++;
        }
        return roomCounter;
    }

    /**
     * searches the array and finds the position of the value chosen by the user
     *
     * @param personDetails Array of Person
     * @param firstName String to search
     * @param surname String to search
     * @return position of the name sent to the method
     */
    public static int getIndex(Person[] personDetails, String firstName, String surname) {
        int roomCounter = 1;

        for (Person customer : personDetails) {
            if ((customer.getFirstName().equals(firstName) && (customer.getSurname().equals(surname)))) {
                break;
            }
            roomCounter++;
        }
        return roomCounter;
    }

    /**
     * displays the whole array
     *
     * @param rooms Array of Room to be displayed
     * @param personDetails Array of Person to be displayed
     */
    public static void viewArray(Room[] rooms, Person[] personDetails) {
        int roomCounter = 1;

        for (int i = 0; i < rooms.length; i++) {
            System.out.println("Room number: " + roomCounter);
            System.out.println("   First Name: " + personDetails[i].getFirstName() + "\n   Surname: " + personDetails[i].getSurname() + "\n   Credit Card Number: " + personDetails[i].getCreditCardNumber() + "\n   Number of Guests: " + rooms[i].getNumberOfGuests() + "\n");
            roomCounter++;
        }
    }

    /**
     * boolean method to add a new element to a Person array
     *
     * @param rooms Array of type Room
     * @param customerName String containing the first value to be inserted into the array
     * @param nGuests Integer containing the second value to be inserted into the array
     * @param roomRequested Integer containing the array index to store the data
     * @return returns true
     */
    public static boolean addToArray(Room[] rooms, String customerName, int nGuests, int roomRequested) {
        roomRequested--;
        rooms[roomRequested].setName(customerName);
        rooms[roomRequested].setNumberOfGuests(nGuests);
        return true;
    }

    /**
     * boolean method to add a new element to a Person array
     *
     * @param person Array of type Person
     * @param customerFirstName String containing the first value to be inserted into the array
     * @param customerSurname String containing the second value to be inserted into the array
     * @param customerCreditCardNumber String containing the third value to be inserted into the array
     * @param roomRequested Integer containing the array index to store the data
     * @return returns true
     */
    public static boolean addToArray(Person[] person, String customerFirstName, String customerSurname, String customerCreditCardNumber, int roomRequested) {
        roomRequested--;
        person[roomRequested].setFirstName(customerFirstName);
        person[roomRequested].setSurname(customerSurname);
        person[roomRequested].setCreditCardNumber(customerCreditCardNumber);
        return true;
    }

    /**
     * Boolean method to store the data of the arrays into a file
     *
     * @param rooms. Array of type Room to be stored into a file
     * @param personDetails, Array of type Person to be stored into a file
     * @return true if successful, false if an error occurred
     */
    public static boolean storeToFile(Room[] rooms, Person[] personDetails) {

        try {
            PrintWriter oFile = new PrintWriter("StoreData\\hotelRooms.txt");
            for (int i = 0; i < rooms.length; i++) {
                oFile.print(personDetails[i].getFirstName());
                oFile.print(" " + personDetails[i].getSurname());
                oFile.print(" " + personDetails[i].getCreditCardNumber());
                oFile.println(" " + rooms[i].getNumberOfGuests());
            }
            oFile.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * method to load data from a file saves the data into two different arrays accordingly to the data wanted.
     *
     * @param rooms, Array of type Room
     * @param personDetails, Array of type Person
     */
    public static void loadDataFromFile(Room[] rooms, Person[] personDetails) {
      
        try {
            Scanner loadData = new Scanner(new BufferedReader(new FileReader("C:\\Users\\crist\\Desktop\\University\\Software Development II\\CourseWork\\hotel\\StoreData\\hotelRooms.txt")));
            String dataLine;
            int fileLineCounter = 0;
            final int GET_FRIST_NAME_INDEX = 0, GET_SURNAME_INDEX = 1, GET_CREDIT_CARD_INDEX = 2, GET_NUMBER_GUESTS_INDEX = 3;
            while (loadData.hasNext()) {
                dataLine = loadData.nextLine();
                String[] fileDetails = dataLine.split(" ");
                personDetails[fileLineCounter].setFirstName(fileDetails[GET_FRIST_NAME_INDEX]);
                personDetails[fileLineCounter].setSurname(fileDetails[GET_SURNAME_INDEX]);
                personDetails[fileLineCounter].setCreditCardNumber(fileDetails[GET_CREDIT_CARD_INDEX]);
                rooms[fileLineCounter].setName(fileDetails[GET_FRIST_NAME_INDEX]);
                rooms[fileLineCounter].setNumberOfGuests(Integer.parseInt(fileDetails[GET_NUMBER_GUESTS_INDEX]));
                fileLineCounter++;
            }
            System.out.print("Data loaded successfully!\n");
        } catch (IOException e) {
            System.out.print("An error has occurred! Please, try again!");
        }
    }

    /**
     * method to order an array alphabetically. uses displayArrayOrder() to display the new ordered array
     *
     * @param personDetails Array of type Person, to be ordered
     */
    public static void orderArrayAlphabetically(Person[] personDetails) {
        Person[] customerToOrder = new Person[NUMBER_OF_ROOMS];
        final int NOT_GREATER_THAN_RESPONSE = 0;

        for (int i = 0; i < customerToOrder.length; i++) {
            customerToOrder[i] = new Person();
        }
        for (int i = 0; i < customerToOrder.length; i++) {
            customerToOrder[i].setFirstName(personDetails[i].getFirstName());
            customerToOrder[i].setSurname(personDetails[i].getSurname());
        }
        String tempFirstName = "";
        String tempSurname = "";
        for (int i = 0; i < customerToOrder.length; i++) {
            for (int j = i + 1; j < customerToOrder.length; j++) {
                if (customerToOrder[i].getFirstName().compareTo(customerToOrder[j].getFirstName()) > NOT_GREATER_THAN_RESPONSE) {
                    tempFirstName = customerToOrder[i].getFirstName();
                    tempSurname = customerToOrder[i].getSurname();
                    customerToOrder[i].setFirstName(customerToOrder[j].getFirstName());
                    customerToOrder[i].setSurname(customerToOrder[j].getSurname());
                    customerToOrder[j].setFirstName(tempFirstName);
                    customerToOrder[j].setSurname(tempSurname);
                }
            }
        }
        System.out.print("\nGuests ordered alphabetically: \n\n");
        displayArrayOrder(customerToOrder, personDetails);
    }

    /**
     * Boolean method to verify if a room is in range (0 - 5) allowed
     *
     * @param requestedRoom integer containing the room requested
     * @return boolean verifying if its in range
     */
    public static boolean verifyRangeRoom(int requestedRoom) {
        final int MIN_NUMBER_GUESTS = 0;
        final int MAX_NUMBER_GUESTS = 5;
        return (requestedRoom >= MIN_NUMBER_GUESTS) && (requestedRoom <= MAX_NUMBER_GUESTS);
    }

    /**
     * Method to check if a queue is full
     *
     * @param queue array of type Queue
     * @return boolean if end + 1 modulus the length of the queue is equal to the front. +1 because the front is initialised to -1.
    *
     */
    public static boolean isItFull(Queue[] queue) {
        return ((end + 1) % queue.length) == front;
    }

    /**
     * Method to check if a queue is empty
     *
     * @param queue array of type Queue
     * @return boolean if front and end is equal to the initialise value, which is -1
     */
    public static boolean isItEmpty(Queue[] queue) {
        return (front == INITIALISE_FRONT_END) && (end == INITIALISE_FRONT_END);
    }

    /**
     * Method to add an element into a Queue (LIFO) Calls IsItEmpty() to check if the queue is empty, if so, set front and end to 0 and is ready to store values. Calls IsItFull() to check if the queue is full.
     *
     * @param queue array of type Queue
     * @param firstName String containing (First Name of the Customer) to be entered into the queue
     * @param surname String containing (Surname of the Customer) to be entered into the queue
     * @param CreditCardNumber String containing (Customer Credit Card Number) to be entered into the queue
     * @param numberOfGuests integer containing (Number of Guests) to be entered into the queue
     * @return boolean
     */
    public static boolean addToQueue(Queue[] queue, String firstName, String surname, String CreditCardNumber, int numberOfGuests) {
        if (isItEmpty(queue)) {
            front = 0;
            end = 0;
            queue[end].setFirstName(firstName);
            queue[end].setSurname(surname);
            queue[end].setCreditCardNumber(CreditCardNumber);
            queue[end].setNumberOfGuests(numberOfGuests);
            return true;
        } else if (isItFull(queue)) {
            return false;
        } else {
            end = (end + 1) % queue.length;
            queue[end].setFirstName(firstName);
            queue[end].setSurname(surname);
            queue[end].setCreditCardNumber(CreditCardNumber);
            queue[end].setNumberOfGuests(numberOfGuests);
            return true;
        }
    }

    /**
     * Method to clear the position front, by setting Strings to "E" and Integer to 0
     *
     * @param queue Array of type Queue
     */
    public static void resetQueueInPosition(Queue[] queue) {
        final String EMPTY_POSITION_STRING = "E";
        final int EMPTY_POSITION_INT = 0;

        queue[front].setFirstName(EMPTY_POSITION_STRING);
        queue[front].setSurname(EMPTY_POSITION_STRING);
        queue[front].setCreditCardNumber(EMPTY_POSITION_STRING);
        queue[front].setNumberOfGuests(EMPTY_POSITION_INT);
    }

    /**
     * Method to remove an element from a Queue (LIFO) Calls method to addToArray() the item removed into a different array Calls isItEmpty() to check is the queue is empty Calls resetQueueInPosition() to clear the front position calling the addToArray()
     *
     * @param queue array of type Queue
     * @param person array of type Person
     * @param room array of type Room
     * @param roomAvailable integer corresponding to the room for the element removed to be stored
     * @return boolean
     */
    public static boolean removeFromQueue(Queue[] queue, Person[] person, Room[] room, int roomAvailable) {
        roomAvailable++;
        if (isItEmpty(queue)) {
            return false;
        } else if (front == end) {
            addToArray(person, queue[front].getFirstName(), queue[front].getSurname(), queue[front].getCreditCardNumber(), roomAvailable);
            addToArray(room, queue[front].getFirstName(), queue[front].getNumberOfGuests(), roomAvailable);
            resetQueueInPosition(queue);
            front = INITIALISE_FRONT_END;
            end = INITIALISE_FRONT_END;
            return true;
        } else {
            addToArray(person, queue[front].getFirstName(), queue[front].getSurname(), queue[front].getCreditCardNumber(), roomAvailable);
            addToArray(room, queue[front].getFirstName(), queue[front].getNumberOfGuests(), roomAvailable);
            resetQueueInPosition(queue);
            front = (front + 1) % queue.length;
            return true;
        }
    }

    /**
     * Display an array of type Queue
     *
     * @param queue array of type Queue
     */
    public static void viewQueue(Queue[] queue) {
        int indexQueue = 1;
        for (Queue elements : queue) {
            System.out.println("Queue: " + indexQueue);
            System.out.println("   First Name: " + elements.getFirstName() + "\n   Surname: " + elements.getSurname() + "\n   Credit Card Number: " + elements.getCreditCardNumber() + "\n   Number of Guests: " + elements.getNumberOfGuests() + "\n");
            indexQueue++;
        }
    }
}