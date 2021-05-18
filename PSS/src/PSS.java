import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import netscape.javascript.JSObject;

public class PSS {
    
    public static ArrayList<Task> taskList = new ArrayList<Task>(); 

    public static void main(String[] args) throws IOException, ParseException{
        Scanner scanner = new Scanner(System.in); 
        int input;
        String taskStartDate;
        String taskName;
    
        displayMenu();
        input = scanner.nextInt();
        
        
        while(input != 10){

            if(input == 1){
                // clear buffer or else code breaks
                scanner.nextLine();
                createTask(scanner);
                displayMenu();
                input = scanner.nextInt();
            }
            else if(input == 2){
                scanner.nextLine();
                System.out.println("Enter task's name: ");
                // clear buffer or else code breaks
                taskName = scanner.nextLine();
                findTask(taskName);
                displayMenu();
                input = scanner.nextInt();
            }
            else if(input == 3){
                // clear buffer or else code breaks
                System.out.println("Enter task's name: ");
                scanner.nextLine();
                taskName = scanner.nextLine();
                deleteTask(taskName);
                displayMenu();
                input = scanner.nextInt();
            }
            else if(input == 4){
                // clear buffer or else code breaks
                System.out.println("Enter task's name: ");
                scanner.nextLine();
                taskName = scanner.nextLine();
                editTask(taskName, scanner);
                displayMenu();
                input = scanner.nextInt();
            }
            else if(input ==5){
                scanner.nextLine(); 
                sorting();
                System.out.println("Enter file's + destination: ");
                System.out.println("Example: D:/Output.json");
                String userInput = scanner.nextLine();
                writeSchedule(userInput);
                displayMenu();
                input = scanner.nextInt();
            }
            else if(input == 6){
                System.out.println("Enter the schedule file's name: "); 
                scanner.nextLine(); 
                String fileName = scanner.nextLine(); 
                readSchedule(fileName);
                displayMenu(); 
                input = scanner.nextInt(); 
            }
            else if(input == 7){
                scanner.nextLine();
                sorting();
                System.out.println("Enter date to view or write: "); 
                taskStartDate = scanner.nextLine();
                viewDaily(taskStartDate, scanner);
                displayMenu(); 
                input = scanner.nextInt(); 
            }
            else if(input == 8){
                scanner.nextLine();
                sorting();
                System.out.println("Enter date to view or write: "); 
                taskStartDate = scanner.nextLine();
                viewWeekly(taskStartDate, scanner);
                displayMenu(); 
                input = scanner.nextInt(); 
            }
            else if(input == 9){
                scanner.nextLine();
                sorting();
                System.out.println("Enter date to view or write: "); 
                taskStartDate = scanner.nextLine();
                viewMonthly(taskStartDate, scanner);
                displayMenu(); 
                input = scanner.nextInt(); 
            }
        }
        scanner.close();
    }
    public static List<Task> findMonthly(String taskStartDate){
        String startMonth;
        String startDate;

        List<Task> dailyList = new ArrayList<Task>();

        // parsing the user input into int
        String userMonth = taskStartDate.substring(4,6);
        if(userMonth.substring(0).equals("0")){
            userMonth = taskStartDate.substring(5, 6);
        }
        int userMonthInt = Integer.parseInt(userMonth);

        String userDate = taskStartDate.substring(6,8);
        if(userDate.substring(0).equals("0")){
            userDate = taskStartDate.substring(7, 8);
        }
        int userDateInt = Integer.parseInt(userDate);
        int correctDate = userDateInt + 30;

        for(int i = 0; i < taskList.size(); i++){
            // parsing the dates and month of task list into int to compare 
            startMonth = taskList.get(i).getStartDate().substring(4,6);
            if(startMonth.substring(0).equals("0")){
                startMonth = taskList.get(i).getStartDate().substring(5,6); 
            } 
            int startMonthInt = Integer.parseInt(startMonth);

            startDate = taskList.get(i).getStartDate().substring(6,8);
            if(startDate.substring(0).equals("0")){
                startDate = taskList.get(i).getStartDate().substring(7,8);
            }
            int startDateInt = Integer.parseInt(startDate);

    

            if((startDateInt <= userDateInt && userMonthInt < startMonthInt) && (userMonthInt == 1 || userMonthInt == 3 || userMonthInt == 5 || userMonthInt == 7 || userMonthInt == 8 || userMonthInt == 10 || userMonthInt == 12)){
                
                correctDate %= 31;

            }
            else if ((startDateInt <= userDateInt && userMonthInt < startMonthInt) && (userMonthInt == 4 || userMonthInt == 6 || userMonthInt == 9 || userMonthInt ==11)){
                
                correctDate %= 30; 
            }
            else if ((startDateInt <= userDateInt && userMonthInt < startMonthInt) && startMonthInt == 2){
            
                correctDate %= 28;
            }
        
          if((startDateInt >= userDateInt) && userMonthInt == startMonthInt || (startDateInt <= userDateInt && userMonthInt < startMonthInt)){
                if(startDateInt <= correctDate){     
                        dailyList.add(taskList.get(i));
                }
               
            }
            


        }
            
                
        return dailyList;
    }
    public static List<Task> findWeekly(String taskStartDate){
        String startMonth;
        String startDate;

        List<Task> dailyList = new ArrayList<Task>();

        // parsing the user input into int
        String userMonth = taskStartDate.substring(4,6);
        if(userMonth.substring(0).equals("0")){
            userMonth = taskStartDate.substring(5, 6);
        }
        int userMonthInt = Integer.parseInt(userMonth);

        String userDate = taskStartDate.substring(6,8);
        if(userDate.substring(0).equals("0")){
            userDate = taskStartDate.substring(7, 8);
        }
        int userDateInt = Integer.parseInt(userDate);
        int correctDate = userDateInt + 7;

        for(int i = 0; i < taskList.size(); i++){
            // parsing the dates and month of task list into int to compare 
            startMonth = taskList.get(i).getStartDate().substring(4,6);
            if(startMonth.substring(0).equals("0")){
                startMonth = taskList.get(i).getStartDate().substring(5,6); 
            } 
            int startMonthInt = Integer.parseInt(startMonth);

            startDate = taskList.get(i).getStartDate().substring(6,8);
            if(startDate.substring(0).equals("0")){
                startDate = taskList.get(i).getStartDate().substring(7,8);
            }
            int startDateInt = Integer.parseInt(startDate);

    

            if((startDateInt <= userDateInt && userMonthInt < startMonthInt) && (userMonthInt == 1 || userMonthInt == 3 || userMonthInt == 5 || userMonthInt == 7 || userMonthInt == 8 || userMonthInt == 10 || userMonthInt == 12)){
                
                correctDate %= 31;

            }
            else if ((startDateInt <= userDateInt && userMonthInt < startMonthInt) && (userMonthInt == 4 || userMonthInt == 6 || userMonthInt == 9 || userMonthInt ==11)){
                
                correctDate %= 30; 
            }
            else if ((startDateInt <= userDateInt && userMonthInt < startMonthInt) && startMonthInt == 2){
            
                correctDate %= 28;
            }
        
          if((startDateInt >= userDateInt) && userMonthInt == startMonthInt || (startDateInt < userDateInt && userMonthInt < startMonthInt)){
                if(startDateInt <= correctDate){     
                        dailyList.add(taskList.get(i));
                }
               
            }
            


        }
            
                
        return dailyList;
        }
        
    public static List<Task> findDaily(String taskStartDate){
        List<Task> dailyList = new ArrayList<Task>();

        for(int i = 0; i < taskList.size(); i++){

            if(taskStartDate.equals(taskList.get(i).getStartDate())){
                dailyList.add(taskList.get(i));
            }
        }

        return dailyList;

    }
    public static void displayFromSchedule(List<Task> dailyTask, int i){
        if(dailyTask.get(i).getCategory().equalsIgnoreCase("Recurring") || dailyTask.get(i).getCategory().equalsIgnoreCase("recurring")){
            System.out.println("Name: " + dailyTask.get(i).getName());
            System.out.println("Type: " + dailyTask.get(i).getType());
            System.out.println("Start date: " + dailyTask.get(i).getStartDate());
            System.out.println("Start time: " + dailyTask.get(i).getStartTime());
            System.out.println("Duration: " + dailyTask.get(i).getDuration());
            //System.out.println("End date: " + taskList.get(i).getEndDate());
            //System.out.println("Frequency: " + taskList.get(i).getFrequency());
        }
        else if (dailyTask.get(i).getCategory().equalsIgnoreCase("Transient") || dailyTask.get(i).getCategory().equalsIgnoreCase("transient")) {
            System.out.println("Name: " + dailyTask.get(i).getName());
            System.out.println("Type: " + dailyTask.get(i).getType());
            System.out.println("Start date: " + dailyTask.get(i).getStartDate());
            System.out.println("Start time: " + dailyTask.get(i).getStartTime());
            System.out.println("Duration: " + dailyTask.get(i).getDuration());
        }
    }
    public static void viewDaily(String taskStartDate, Scanner scanner){
        displayMenuDaily();
        int input = scanner.nextInt();

        String userMonth = taskStartDate.substring(4,6);
        if(userMonth.substring(0).equals("0")){
            userMonth = taskStartDate.substring(5, 6);
        }
        int userMonthInt = Integer.parseInt(userMonth);

        String userDate = taskStartDate.substring(6,8);
        if(userDate.substring(0).equals("0")){
            userDate = taskStartDate.substring(7, 8);
        }
        int userDateInt = Integer.parseInt(userMonth);

        List<Task> dailyList = findDaily(taskStartDate);
        while(input != 3){

            if(input == 1){
                
                for(int i = 0; i < dailyList.size(); i++){
                    System.out.println("-----------------------------");
                    displayFromSchedule(dailyList, i);
                }
                displayMenuDaily();
                input = scanner.nextInt();
                
            }
            else if(input == 2){
                writeScheduleDaily(userDateInt, userMonthInt, dailyList);
                displayMenuDaily();
                input = scanner.nextInt();
            }
        }
  
    }
    public static void viewWeekly(String taskStartDate, Scanner scanner){
        displayMenuDaily();
        int input = scanner.nextInt();

        String userMonth = taskStartDate.substring(4,6);
        if(userMonth.substring(0).equals("0")){
            userMonth = taskStartDate.substring(5, 6);
        }
        int userMonthInt = Integer.parseInt(userMonth);

        String userDate = taskStartDate.substring(6,8);
        if(userDate.substring(0).equals("0")){
            userDate = taskStartDate.substring(7, 8);
        }
        int userDateInt = Integer.parseInt(userDate);

        List<Task> dailyList = findWeekly(taskStartDate);
        while(input != 3){

            if(input == 1){
                
                for(int i = 0; i < dailyList.size(); i++){
                    System.out.println("-----------------------------");
                    displayFromSchedule(dailyList, i);
                }
                displayMenuDaily();
                input = scanner.nextInt();
                
            }
            else if(input == 2){
                writeScheduleWeekly(userDateInt, userMonthInt, dailyList);
                displayMenuDaily();
                input = scanner.nextInt();
            }
        }
  
    }
    public static void viewMonthly(String taskStartDate, Scanner scanner){
        displayMenuDaily();
        int input = scanner.nextInt();

        String userMonth = taskStartDate.substring(4,6);
        if(userMonth.substring(0).equals("0")){
            userMonth = taskStartDate.substring(5, 6);
        }
        int userMonthInt = Integer.parseInt(userMonth);

        String userDate = taskStartDate.substring(6,8);
        if(userDate.substring(0).equals("0")){
            userDate = taskStartDate.substring(7, 8);
        }
        int userDateInt = Integer.parseInt(userMonth);

        List<Task> dailyList = findMonthly(taskStartDate);
        while(input != 3){

            if(input == 1){
                
                for(int i = 0; i < dailyList.size(); i++){
                    System.out.println("-----------------------------");
                    displayFromSchedule(dailyList, i);
                }
                displayMenuDaily();
                input = scanner.nextInt();
                
            }
            else if(input == 2){
                writeScheduleMonthly(userDateInt, userMonthInt, dailyList);
                displayMenuDaily();
                input = scanner.nextInt();
            }
        }
  
    }
    public static void displayMenuWeekly(){
        System.out.println("-----------------------------");
        System.out.println("1 - Enter 1 to view schedule for a specifc week: ");
        System.out.println("2 - Enter 2 to write schedule for a specific week: ");
        System.out.println("3 - exit");
        System.out.println("-----------------------------");
    }
    public static void displayMenuMonthly(){
        System.out.println("-----------------------------");
        System.out.println("1 - Enter 1 to view schedule for a specific month: ");
        System.out.println("2 - Enter 2 to write schedule for a specific month: ");
        System.out.println("3 - exit");
        System.out.println("-----------------------------");
    }
    public static void displayMenuDaily(){
        System.out.println("-----------------------------");
        System.out.println("1 - Enter 1 to view schedule for a specific date: ");
        System.out.println("2 - Enter 2 to write schedule for a specific date: ");
        System.out.println("3 - exit");
        System.out.println("-----------------------------");
    }
    public static void displayMenu(){
        System.out.println("-----------------------------");
        System.out.println("1 - Enter 1 to create Task: ");
        System.out.println("2 - Enter 2 to view Task: ");
        System.out.println("3 - Enter 3 to delete Task: ");
        System.out.println("4 - Enter 4 to edit Task: ");
        System.out.println("5 - Enter 5 to write Task: ");
        System.out.println("6 - Enter 6 to read Task: ");
        System.out.println("7 - Enter 7 to view or read daily");
        System.out.println("8 - Enter 8 to view or read weekly");
        System.out.println("9 - Enter 9 to view or read monthly");
        System.out.println("10 - exit");
        System.out.println("-----------------------------");
    }
    public static void createTask(Scanner scanner){
        // for reading user input


        System.out.println("Type of task? (Recurring/Transient/Anti-Task)"); 
        String taskCategory = scanner.nextLine(); 

        if(taskCategory.equalsIgnoreCase("Recurring") || taskCategory.equalsIgnoreCase("recurring")){
            
            RecurringTask newTask = new RecurringTask(); 

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 
            // Check if the user-inputted name is unique
            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(taskName)){
                    System.out.println("Task name is not unique. Re-enter a new name."); 
                    taskName = scanner.nextLine(); 
                }
            }



            String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"}; 
            System.out.println("Input the type of the task: \n");
            String taskType = scanner.nextLine();  

            // check if taskType is valid (within the list of accepted)
            while(!(Arrays.asList(validTypes).contains(taskType))){
                    System.out.println("Invalid task type! Input a new task type. \n");
                    taskType = scanner.nextLine();                
            }

            System.out.println("Input the start date: \n"); 
            String taskStartDate = scanner.nextLine(); 
            // check if start date is valid
            try{
                verifyDate(taskStartDate, scanner);
            }
            catch(Exception e){
                System.out.println("Invalid date. Please input a valid start date.");
                taskStartDate = scanner.nextLine();
            }
            // entering input for start time
            System.out.println("Input the start time: \n");
            Float taskStartTime = scanner.nextFloat();
            verifyCollision(taskStartDate, taskStartTime, scanner);
            while(taskStartTime > 23.75 || taskStartTime < 0.25 || taskStartTime % (.25) != 0){
                System.out.println("Invalid start time. Please input a valid start time between 0.25 and 23.75"); 
                taskStartTime= scanner.nextFloat(); 
            }
            
            System.out.println("Input the duration: \n");
            
            float taskDuration = scanner.nextFloat(); 
            verifyCollision(taskDuration, taskStartDate, taskStartTime, scanner);
            
            while(taskDuration > 23.75 || taskDuration < 0.25 || taskDuration % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskDuration = scanner.nextFloat(); 
            }
            scanner.nextLine(); 
            System.out.println("Input the end date: \n");
            String taskEndDate  = scanner.nextLine(); 
            verifyDate(taskEndDate, scanner);
            //verifyEndDate(taskEndDate, taskStartDate, scanner); 
            
            System.out.println("Input the frequency: \n"); 
            int taskFreq = scanner.nextInt();  
            
            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setDuration(taskDuration);
            newTask.setEndDate(taskEndDate);
            newTask.setFrequency(taskFreq); 
            newTask.setCategory(taskCategory);
            newTask.setStartTime(taskStartTime);
            if(taskFreq == 1){
                createRecurringDaily(newTask);
            }
            else {
                createRecurringWeekly(newTask);
            }

        }
        
        // Transient task and anti-task have the same attributes, so we will just create anti-tasks as transient under the hood but with
        // specifying the type as "cancellation"
        else if(taskCategory.equalsIgnoreCase("Transient") || taskCategory.equalsIgnoreCase("transient")){

            TransientTask newTask = new TransientTask();

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 

            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(taskName)){
                    System.out.println("Task name is not unique. Re-enter a new name."); 
                    taskName = scanner.nextLine(); 
                }
            }
            
            if(taskCategory.equalsIgnoreCase("Anti-task")){
                
            }
            String[] validTypes = {"Visit", "Shopping", "Appointment"}; 
            System.out.println("Input the type of the task: ");
            String taskType = scanner.nextLine();  

            while(!(Arrays.asList(validTypes).contains(taskType))){
                System.out.println("Invalid task type! Input a new task type. \n");
                taskType = scanner.nextLine();                
            }

            System.out.println("Input the start date: "); 
            String taskStartDate = scanner.nextLine(); 
            verifyDate(taskStartDate, scanner);

            System.out.println("Input the start time: \n");
            Float taskStartTime = scanner.nextFloat();
            if(!(taskType.equalsIgnoreCase("Cancellation"))){
                verifyCollision(taskStartDate, taskStartTime, scanner);
            }

            while(taskStartTime > 23.75 || taskStartTime < 0.25 || taskStartTime % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskStartTime= scanner.nextFloat(); 
            }
            

            System.out.println("Input the duration: ");
            Float taskDuration = scanner.nextFloat();  
            if(!(taskType.equalsIgnoreCase("Cancellation"))){
                verifyCollision(taskDuration, taskStartDate, taskStartTime, scanner);
            }
        
            while(taskDuration > 23.75 || taskDuration < 0.25 || taskDuration % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskDuration = scanner.nextFloat(); 
            }

            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setStartTime(taskStartTime);
            newTask.setDuration(taskDuration);
            newTask.setCategory(taskCategory);
            
            taskList.add(newTask); 
        
        } 

        else if(taskCategory.equalsIgnoreCase("Anti-Task") || taskCategory.equalsIgnoreCase("anti-task") ){

            AntiTask newTask = new AntiTask(); 

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 

            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(taskName)){
                    System.out.println("Task name is not unique. Re-enter a new name."); 
                    taskName = scanner.nextLine(); 
                }
            }
            
            if(taskCategory.equalsIgnoreCase("Anti-task")){
                
            }
            String[] validTypes = {"Cancellation"}; 
            System.out.println("Input the type of the task: ");
            String taskType = scanner.nextLine();  

            while(!(Arrays.asList(validTypes).contains(taskType))){
                System.out.println("Invalid task type! Input a new task type. \n");
                taskType = scanner.nextLine();                
            }

            System.out.println("Input the start date: "); 
            String taskStartDate = scanner.nextLine(); 
            verifyDate(taskStartDate, scanner);

            System.out.println("Input the start time: \n");
            Float taskStartTime = scanner.nextFloat();
            
            while(taskStartTime > 23.75 || taskStartTime < 0.25 || taskStartTime % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskStartTime= scanner.nextFloat(); 
            }
            

            System.out.println("Input the duration: ");
            Float taskDuration = scanner.nextFloat();  

            while(taskDuration > 23.75 || taskDuration < 0.25 || taskDuration % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskDuration = scanner.nextFloat(); 
            }

            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setStartTime(taskStartTime);
            newTask.setDuration(taskDuration);
            newTask.setCategory(taskCategory);

            boolean found = false; 
            for(int i = 0; i < taskList.size(); i++){
                if((taskStartTime.equals(taskList.get(i).getStartTime())) && (taskStartDate.equals(taskList.get(i).getStartDate()))){

                    if(taskList.get(i).getCategory().equals("Recurring") || taskList.get(i).getCategory().equals("recurring")){ 
                        taskList.remove(i);  
                        found = true; 
                    }

                    else{
                        System.out.println("The task attempted to be deleted is not of type recurring"); 
                    }

                }
        }
        if(!found){
            System.out.println("No matching instance of a task was found."); 
        }
    }
        
        else{
            System.out.println("Not a valid task type!"); 
        }     


    }

    public static void deleteAntiTask(AntiTask newTask){

        boolean found = false; 
        for(int i = 0; i < taskList.size(); i++){
            if((newTask.getStartTime()==(taskList.get(i).getStartTime())) && (newTask.getStartDate().equals(taskList.get(i).getStartDate()))){

                if(taskList.get(i).getCategory().equals("Recurring") || taskList.get(i).getCategory().equals("recurring")){ 
                    taskList.remove(i);  
                    found = true; 
                }
                
                else{
                    System.out.println("The task attempted to be deleted is not of type recurring"); 
                }

            }
    }
        if(!found){
            System.out.println("No matching instance of a task was found."); 
        }
    }
    public static void createRecurringDaily(RecurringTask recurringTask){
        
        // storing days in the month
        int daysMonth = 0;

        String startMonth = recurringTask.getStartDate().substring(4,6); 
        int times = 0;
        if(startMonth.substring(0).equals("0")){
            startMonth = recurringTask.getStartDate().substring(5,6); 
        } 
        int startMonthInt = Integer.parseInt(startMonth);

        String startDate = recurringTask.getStartDate().substring(6,8); 
        if(startDate.substring(0).equals("0")){
            startDate = recurringTask.getStartDate().substring(7,8);
        }
        int startDateInt = Integer.parseInt(startDate);

        String endMonth = recurringTask.getEndDate().substring(4,6); 
        if(endMonth.substring(0).equals("0")){
            endMonth = recurringTask.getEndDate().substring(5,6); 
        } 
        int endMonthInt = Integer.parseInt(endMonth); 

        String endDate = recurringTask.getEndDate().substring(6,8); 
        if(endDate.substring(0).equals("0")){
            endDate = recurringTask.getEndDate().substring(7,8);
        }
        int endDateInt = Integer.parseInt(endDate);

        
        if(endMonthInt == startMonthInt){
            times = endDateInt - startDateInt;     
        }

        else{
            if(startMonthInt == 1 || startMonthInt == 3 || startMonthInt == 5 || startMonthInt == 7 || startMonthInt == 8 || startMonthInt == 10 || startMonthInt == 12){
                times = 31 - startDateInt;  
                times += endDateInt; 
                daysMonth = 31;
            }

            else if (startMonthInt == 4 || startMonthInt == 6 || startMonthInt == 9 || startMonthInt ==11){
                 times = 30 - startDateInt; 
                 times += endDateInt; 
                 daysMonth = 30;
            }

            // February
            else if (startMonthInt == 2){
                times = 28 - startDateInt; 
                times += endDateInt; 
                daysMonth = 28;
            }
        }

        createRecurringD(times, recurringTask, daysMonth);

    }
    public static void createRecurringD(int times, RecurringTask recurringTask, int daysMonth){
        String dateFormat = "";
        // combining the rest of the month 
        String combine = "";
        
        // fetch the dates and increment to the date
        String startDate = recurringTask.getStartDate().substring(6,8); 
        if(startDate.substring(0).equals("0")){
            startDate = recurringTask.getStartDate().substring(7,8);
        }
        int startDateInt = Integer.parseInt(startDate);

        String startMonth = recurringTask.getStartDate().substring(4,6); 
        if(startMonth.substring(0).equals("0")){
            startMonth = recurringTask.getStartDate().substring(5,6); 
        } 
        int startMonthInt = Integer.parseInt(startMonth);

        String endMonth = recurringTask.getEndDate().substring(4,6);
        if(endMonth.substring(0).equals("0")){
            endMonth = recurringTask.getStartDate().substring(5,6); 
        } 
        int endMonthInt = Integer.parseInt(endMonth);
        
         
        for(int i = 0; i <= times; i++){
            if( i == 0){

            }
            else if(startMonthInt == endMonthInt){
                startDateInt = startDateInt + 1;
            }
            else if(startDateInt == daysMonth){
               startDateInt = 0;
               startDateInt = startDateInt + 1;
               startMonthInt += 1;
               if(startMonthInt < 10){
                   startMonth = "0" + String.valueOf(startMonthInt);
               }
               else {
                   startMonth = String.valueOf(startMonthInt);
               }
            }
            else {
                startDateInt = startDateInt + 1;
            }
            
            if(startDateInt < 10){
                dateFormat = "0" + String.valueOf(startDateInt);
            }
            else {
                dateFormat = String.valueOf(startDateInt);
            }
            combine = recurringTask.getStartDate().substring(0,4) + startMonth + dateFormat;
            RecurringTask newTask = new RecurringTask();
            newTask.setCategory(recurringTask.getCategory());
            newTask.setName(recurringTask.getName());
            newTask.setDuration(recurringTask.getDuration());
            newTask.setEndDate(recurringTask.getEndDate());
            newTask.setType(recurringTask.getType());
            newTask.setStartTime(recurringTask.getStartTime());
            newTask.setStartDate(combine);
            taskList.add(newTask);
        }
    }

    public static void createRecurringWeekly(RecurringTask recurringTask){
                // storing days in the month
                int daysMonth = 0;
                int times = 0;
                String startMonth = recurringTask.getStartDate().substring(4,6); 
                if(startMonth.substring(0).equals("0")){
                    startMonth = recurringTask.getStartDate().substring(5,6); 
                } 
                int startMonthInt = Integer.parseInt(startMonth);
        
                String startDate = recurringTask.getStartDate().substring(6,8); 
                if(startDate.substring(0).equals("0")){
                    startDate = recurringTask.getStartDate().substring(7,8);
                }
                int startDateInt = Integer.parseInt(startDate);
        
                String endMonth = recurringTask.getEndDate().substring(4,6); 
                if(endMonth.substring(0).equals("0")){
                    endMonth = recurringTask.getEndDate().substring(5,6); 
                } 
                int endMonthInt = Integer.parseInt(endMonth); 
        
                String endDate = recurringTask.getEndDate().substring(6,8); 
                if(endDate.substring(0).equals("0")){
                    endDate = recurringTask.getEndDate().substring(7,8);
                }
                int endDateInt = Integer.parseInt(endDate);
                if(startMonthInt == 1 || startMonthInt == 3 || startMonthInt == 5 || startMonthInt == 7 || startMonthInt == 8 || startMonthInt == 10 || startMonthInt == 12){
                    daysMonth = 31;
                    times = 31 - startDateInt;  
                    times += endDateInt;    
        
                }
        
                else if (startMonthInt == 4 || startMonthInt == 6 || startMonthInt == 9 || startMonthInt ==11){
                    daysMonth = 30;
                    times = 30 - startDateInt;  
                    times += endDateInt;  
                }
        
                // February
                else if (startMonthInt == 2){
                    daysMonth = 28;
                    times = 28 - startDateInt;  
                    times += endDateInt;  
                }

                
                createRecurringW(times, recurringTask, daysMonth);
    }
    public static void createRecurringW(int times, RecurringTask recurringTask, int daysMonth){
        int count = 0; 
        String combine = " ";
        int temp = 0;
        String dateFormat = "";

        // fetch the dates and increment to the date
         String startDate = recurringTask.getStartDate().substring(6,8); 
         if(startDate.substring(0).equals("0")){
             startDate = recurringTask.getStartDate().substring(7,8);
         }
         int startDateInt = Integer.parseInt(startDate);
 
         String startMonth = recurringTask.getStartDate().substring(4,6); 
         if(startMonth.substring(0).equals("0")){
             startMonth = recurringTask.getStartDate().substring(5,6); 
         } 
         int startMonthInt = Integer.parseInt(startMonth);
 
         String endDateMonth = recurringTask.getEndDate().substring(6,8);
         if(endDateMonth.substring(0).equals("0")){
             endDateMonth = recurringTask.getStartDate().substring(7,8); 
         } 
         int endDateInt = Integer.parseInt(endDateMonth);

        String endMonth = recurringTask.getEndDate().substring(4,6); 
         if(endMonth.substring(0).equals("0")){
             endMonth = recurringTask.getStartDate().substring(5,6); 
         } 
         int endMonthInt = Integer.parseInt(endMonth);
         
         // checking how many days in month to mod to get how many more days till we add
        
         // bad conditon
         for(int i = 0; i <= times / 7; i++){
            if(startDateInt > daysMonth){
                startDateInt = startDateInt % daysMonth;
                startMonthInt += 1;
                if(startMonthInt < 10){
                    startMonth = "0" + String.valueOf(startMonthInt);
                }
                else {
                    startMonth = String.valueOf(startMonthInt);
                }
             }
                        
            if(startDateInt < 10){
                dateFormat = "0" + String.valueOf(startDateInt);
            }
            else if(startMonthInt == endMonthInt && startDateInt > endDateInt){
                break;
            }
            else{
                dateFormat = String.valueOf(startDateInt);
            }
            
             combine = recurringTask.getStartDate().substring(0,4) + startMonth + dateFormat;
             RecurringTask newTask = new RecurringTask();
             newTask.setCategory(recurringTask.getCategory());
             newTask.setName(recurringTask.getName());
             newTask.setDuration(recurringTask.getDuration());
             newTask.setEndDate(recurringTask.getEndDate());
             newTask.setType(recurringTask.getType());
             newTask.setStartTime(recurringTask.getStartTime());
             newTask.setStartDate(combine);
             taskList.add(newTask);
             startDateInt += 7; 
         }
    }


    public static void verifyDate(String taskStartDate, Scanner scanner){
            // check if start date is valid
            String month = taskStartDate.substring(4,6);
            if(month.substring(0).equals("0")){
                month = taskStartDate.substring(5,6); 
            } 
            
            int monthInt = Integer.parseInt(month);
            if(monthInt > 12 || monthInt < 1){
                System.out.println("Invalid Month."); 
            }

            String date = taskStartDate.substring(6,8); 
            int dateInt = Integer.parseInt(date); 
            
            if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 || monthInt == 8 || monthInt == 10 ||monthInt == 12){
                while(dateInt > 31 || dateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskStartDate = scanner.nextLine(); 
                    date = taskStartDate.substring(6,8);
                    dateInt = Integer.parseInt(date); 
                }
            }

            else if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt ==11){
                while (dateInt > 30 || dateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskStartDate = scanner.nextLine(); 
                    date = taskStartDate.substring(6,8);
                    dateInt = Integer.parseInt(date); 
                }
            }

            // February
            else if (monthInt == 2){
                while(dateInt > 29 || dateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskStartDate = scanner.nextLine(); 
                    date = taskStartDate.substring(6,8);
                    dateInt = Integer.parseInt(date); 
                }
            }
            
    }

    public static void verifyEndDate(String taskEndDate, String taskStartDate, Scanner scanner){

        String endmonth = taskEndDate.substring(4,5);
            if(endmonth.substring(0).equals("0")){
                endmonth = taskEndDate.substring(5,6); 
            } 
            
            String month = taskStartDate.substring(4,5);
            if(month.substring(0).equals("0")){
                month = taskStartDate.substring(5,6); 
            } 
            int monthInt = Integer.parseInt(month);
            if(monthInt > 12 || monthInt < 1){
                System.out.println("Invalid Month."); 
            }
            int endmonthInt = Integer.parseInt(endmonth);
            if(endmonthInt > 12 || endmonthInt < 1 || !(endmonthInt < monthInt)){
                System.out.println("Invalid Month."); 
            }

            String enddate = taskStartDate.substring(6,8); 
            int enddateInt = Integer.parseInt(enddate); 
            
            if(endmonthInt == 1 || endmonthInt == 3 || endmonthInt == 5 || endmonthInt == 7 || endmonthInt == 8 || endmonthInt == 10 || endmonthInt == 12){
                while(enddateInt > 31 || enddateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskEndDate = scanner.nextLine(); 
                    enddate = taskEndDate.substring(6,8);
                    enddateInt = Integer.parseInt(enddate); 
                }
            }

            else if (endmonthInt == 4 || endmonthInt == 6 || endmonthInt == 9 || endmonthInt == 11){
                while (enddateInt > 30 || enddateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskEndDate = scanner.nextLine(); 
                    enddate = taskStartDate.substring(6,8);
                    enddateInt = Integer.parseInt(enddate); 
                }
            }

            // February
            else if (endmonthInt == 2){
                while(enddateInt > 29 || enddateInt < 1){
                    System.out.println("Invalid date. Re-enter the start date."); 
                    taskEndDate = scanner.nextLine(); 
                    enddate = taskEndDate.substring(6,8);
                    enddateInt = Integer.parseInt(enddate); 
                }
            }
    }
    public static void verifyCollision(String taskStartDate, float taskStartTime, Scanner scanner){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).getStartDate().equals(taskStartDate)){
                while(taskStartTime >= taskList.get(i).getStartTime() && (taskList.get(i).getStartTime() + taskList.get(i).getDuration()) > taskStartTime){
                    System.out.println("Invalid time. Re-enter a new start time.");
                    taskStartTime = scanner.nextFloat();
                }
            }
        }
    }

    public static void verifyCollision(Float taskDuration, String taskStartDate, float taskStartTime, Scanner scanner){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).getStartDate().equals(taskStartDate)){
                while(taskStartTime < taskList.get(i).getStartTime() && (taskDuration + taskStartTime) > (taskList.get(i).getDuration() + taskList.get(i).getStartTime())) {
                    System.out.println("Invalid time. Re-enter a new duration.");
                    taskStartTime = scanner.nextFloat();
                }
                //while((taskStartTime + taskDuration) > taskList.get(i).getStartTime() && (taskList.get(i).getStartTime() + taskList.get(i).getDuration()) > (taskDuration + taskStartTime) && (taskStartTime < taskList.get(i).getStartTime()) && (taskStartTime + taskDuration) >= (taskList.get(i).getDuration() + taskList.get(i).getStartTime())){
                   
               // }
            }
        }
    }

    public static void sorting(){
        Collections.sort(taskList, new Comparator<Task>(){
           
            @Override
            public int compare(Task s1, Task s2){
                return s1.getStartDate().compareTo(s2.getStartDate());
            }
        });  
        
    }
    public static void editTask(String taskName, Scanner scanner){
        findTask(taskName);
        
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).getName().equals(taskName)){
                
                if(taskList.get(i).getCategory().equalsIgnoreCase("Recurring") || taskList.get(i).getCategory().equalsIgnoreCase("recurring")){
                    menuR(i, scanner);
                }
                else{
                    menuT(i, scanner);
                }
            }
        }

    }
    public static void displayMenuT(int i){
        System.out.println("-----------------------------");
        System.out.println("Enter correspond number to edit");
        System.out.println("1 - Name: " + taskList.get(i).getName());
        System.out.println("2 - Type: " + taskList.get(i).getType());
        System.out.println("3 - Start date: " + taskList.get(i).getStartDate());
        System.out.println("4 - Start time: " + taskList.get(i).getStartTime());
        System.out.println("5 - Duration: " + taskList.get(i).getDuration());
        System.out.println("6 - Exit to main");
        System.out.println("-----------------------------");
    }
    public static void menuT(int i, Scanner scanner){
        displayMenuT(i);
        int input = scanner.nextInt();

        while(input != 6){

            if(input == 1){
                scanner.nextLine();
                System.out.println("Input the name of your task: \n"); 
                String taskName = scanner.nextLine(); 
                for(int j = 0; j < taskList.size(); j++ ){
                    if(taskList.get(j).getName().equals(taskName)){
                        System.out.println("Task name is not unique. Re-enter a new name."); 
                        taskName = scanner.nextLine(); 
                    }
                }
                taskList.get(i).setName(taskName);
                displayMenuT(i);
                input = scanner.nextInt();
            }
            else if(input == 2){
                scanner.nextLine();
                String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"}; 
                System.out.println("Input the type of the task: \n");
                String taskType = scanner.nextLine();  
        
                // check if taskType is valid 
                while(!(Arrays.asList(validTypes).contains(taskType))){
                        System.out.println("Invalid task type! Input a new task type. \n");
                        taskType = scanner.nextLine();                
                }
                taskList.get(i).setType(taskType);
                displayMenuT(i);
                input = scanner.nextInt();
        
            }
            else if(input == 3){
                scanner.nextLine();
                System.out.println("Input the start date: \n"); 
                String taskStartDate = scanner.nextLine(); 
                // check if start date is valid
                verifyDate(taskStartDate, scanner);
                taskList.get(i).setStartDate(taskStartDate);
                displayMenuT(i);
                input = scanner.nextInt();
            }
            else if(input == 4){
                scanner.nextLine();
                  // entering input for start time
                System.out.println("Input the start time: \n");
                float taskStartTime = scanner.nextFloat();
                taskList.get(i).setStartTime(taskStartTime);
                displayMenuT(i);
                input = scanner.nextInt();
            }
            else if(input == 5){
                scanner.nextLine();
                System.out.println("Input the duration: \n");
        
                float taskDuration = scanner.nextFloat(); 
                
                while(taskDuration > 23.75 || taskDuration < 0.25){
                    System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                    taskDuration = scanner.nextFloat(); 
                }
                taskList.get(i).setDuration(taskDuration);
                displayMenuT(i);
                input = scanner.nextInt();
            }
        }
    }
    public static void displayMenuR(int i){
        System.out.println("-----------------------------");
        System.out.println("Enter correspond number to edit");
        System.out.println("1 - Name: " + taskList.get(i).getName());
        System.out.println("2 - Type: " + taskList.get(i).getType());
        System.out.println("3 - Start date: " + taskList.get(i).getStartDate());
        System.out.println("4 - Start time: " + taskList.get(i).getStartTime());
        System.out.println("5 - Duration: " + taskList.get(i).getDuration());
        System.out.println("6 - End date: " + taskList.get(i).getEndDate());
        System.out.println("7 - Frequency: " + taskList.get(i).getFrequency());
        System.out.println("8- Exit to main");
        System.out.println("-----------------------------");
    }
    public static void menuR(int i, Scanner scanner){

        displayMenuR(i);
        int input = scanner.nextInt();

        while(input != 8){

            if(input == 1){
                scanner.nextLine();
                System.out.println("Input the name of your task: \n"); 
                String taskName = scanner.nextLine(); 
                for(int j = 0; j < taskList.size(); j++ ){
                    if(taskList.get(j).getName().equals(taskName)){
                        System.out.println("Task name is not unique. Re-enter a new name."); 
                        taskName = scanner.nextLine(); 
                    }
                }
                taskList.get(i).setName(taskName);
                displayMenuR(i);
                input = scanner.nextInt();
                
            }
            else if(input == 2){
                scanner.nextLine();
                String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"}; 
                System.out.println("Input the type of the task: \n");
                String taskType = scanner.nextLine();  
        
                // check if taskType is valid 
                while(!(Arrays.asList(validTypes).contains(taskType))){
                        System.out.println("Invalid task type! Input a new task type. \n");
                        taskType = scanner.nextLine();                
                }
                taskList.get(i).setType(taskType);
                displayMenuR(i);
                input = scanner.nextInt();
        
            }
            else if(input == 3){
                scanner.nextLine();
                System.out.println("Input the start date: \n"); 
                String taskStartDate = scanner.nextLine(); 
                // check if start date is valid
                verifyDate(taskStartDate, scanner);
                taskList.get(i).setStartDate(taskStartDate);
                displayMenuR(i);
                input = scanner.nextInt();
            }
            else if(input == 4){
                scanner.nextLine();
                  // entering input for start time
                System.out.println("Input the start time: \n");
                float taskStartTime = scanner.nextFloat();
                taskList.get(i).setStartTime(taskStartTime);
                displayMenuR(i);
                input = scanner.nextInt();
            }
            else if(input == 5){
                scanner.nextLine();
                System.out.println("Input the duration: \n");
        
                float taskDuration = scanner.nextFloat(); 
                
                while(taskDuration > 23.75 || taskDuration < 0.25 || taskDuration % (.25) != 0){
                    System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                    taskDuration = scanner.nextFloat(); 
                }
                taskList.get(i).setDuration(taskDuration);
                displayMenuR(i);
                input = scanner.nextInt();

            }
            else if(input == 6){
                scanner.nextLine(); 
                System.out.println("Input the end date: \n");
                String taskEndDate  = scanner.nextLine(); 
                verifyDate(taskEndDate, scanner);
                taskList.get(i).setEndDate(taskEndDate);
                displayMenuR(i);
                input = scanner.nextInt();
            }
            else if(input == 7){
                scanner.nextLine();
                System.out.println("Input the frequency: \n"); 
                int taskFreq = scanner.nextInt(); 
                scanner.nextLine(); 
                taskList.get(i).setFrequency(taskFreq);
                displayMenuR(i);
                input = scanner.nextInt();
            }
        }
    }
    public static void findTask(String taskName) {
        boolean found = false; 
        for(int i = 0; i < taskList.size(); i++){
            if(taskName.equals(taskList.get(i).getName())){
                System.out.println("-----------------------------");
                display(i);
                found = true; 
            }
        }
        if (!found){
            System.out.println("Task not found."); 
        } 
    }

    public static void deleteTask(String taskName){
        for(int i = 0; i < taskList.size(); i++){
            if(taskName.equals(taskList.get(i).getName())){
                taskList.remove(i);
                System.out.println("Successful deletion");
            }
            else{
                System.out.println("Invalid Name");
            }
        }
    }

    public static void display(int i){
        if(taskList.get(i).getCategory().equalsIgnoreCase("Recurring") || taskList.get(i).getCategory().equalsIgnoreCase("recurring")){
            System.out.println("Name: " + taskList.get(i).getName());
            System.out.println("Type: " + taskList.get(i).getType());
            System.out.println("Start date: " + taskList.get(i).getStartDate());
            System.out.println("Start time: " + taskList.get(i).getStartTime());
            System.out.println("Duration: " + taskList.get(i).getDuration());
            //System.out.println("End date: " + taskList.get(i).getEndDate());
            //System.out.println("Frequency: " + taskList.get(i).getFrequency());
        }
        else if (taskList.get(i).getCategory().equalsIgnoreCase("Transient") || taskList.get(i).getCategory().equalsIgnoreCase("transient")) {
            System.out.println("Name: " + taskList.get(i).getName());
            System.out.println("Type: " + taskList.get(i).getType());
            System.out.println("Start date: " + taskList.get(i).getStartDate());
            System.out.println("Start time: " + taskList.get(i).getStartTime());
            System.out.println("Duration: " + taskList.get(i).getDuration());
        }
            
    }

    public static void readSchedule(String fileName) throws IOException, ParseException{
        
        JSONParser parser = new JSONParser();

        try{
            JSONArray a = (JSONArray) parser.parse(new FileReader(fileName));
            
            for(Object o : a){
                JSONObject task = (JSONObject) o;
                String taskName = (String) task.get("Name");
                String taskType = (String) task.get("Type"); 

                if(taskType.equalsIgnoreCase("Class") || taskType.equalsIgnoreCase("Study") || taskType.equalsIgnoreCase("Exercise") || taskType.equalsIgnoreCase("Sleep") ||
                taskType.equalsIgnoreCase("Work") || taskType.equalsIgnoreCase("Meal")){
                
                // Reading each key in the JSON object and casting it to the variable types we need for validating user input
                Long startDate = (Long)task.get("StartDate");
                String taskStartDate = String.valueOf(startDate); 

                Long startTime = (Long)task.get("StartTime");
                String startTime1 = String.valueOf(startTime);
                float taskStartTime = Float.parseFloat(startTime1); 

                double duration = (double)task.get("Duration");
                float duration1  = (float) duration;
                Float taskDuration = new Float(duration1);  

                Long endDate = (Long)task.get("EndDate");
                String taskEndDate = String.valueOf(endDate); 
                
                long freq = (long)task.get("Frequency");
                int taskFrequency = (int)freq; 
                 

                RecurringTask newTask = new RecurringTask(); 
                newTask.setName(taskName);
                newTask.setType(taskType); 
                newTask.setStartDate(taskStartDate);
                newTask.setStartTime(taskStartTime); 
                newTask.setDuration(taskDuration); 
                newTask.setEndDate(taskEndDate);
                newTask.setFrequency(taskFrequency);
                newTask.setCategory("Recurring");
                
                if(taskFrequency == 1){
                    createRecurringDaily(newTask);
                }
                else{
                    createRecurringWeekly(newTask);
                }
                
            }

            else if(taskType.equalsIgnoreCase("Visit") || taskType.equalsIgnoreCase("Shopping") || taskType.equalsIgnoreCase("Appointment")){

                Long startDate = (Long)task.get("Date");
                String taskStartDate = String.valueOf(startDate); 

                Long startTime = (Long)task.get("StartTime");
                String startTime1 = String.valueOf(startTime);
                float taskStartTime = Float.parseFloat(startTime1); 

                double duration = (double)task.get("Duration");
                float duration1  = (float) duration;
                Float taskDuration = new Float(duration1);
                String category = "Transient"; 

                TransientTask newTask = new TransientTask(); 

                newTask.setName(taskName);
                newTask.setType(taskType); 
                newTask.setStartDate(taskStartDate);
                newTask.setStartTime(taskStartTime); 
                newTask.setDuration(taskDuration); 
                newTask.setCategory(category);

                taskList.add(newTask); 
             }

             else if(taskType.equalsIgnoreCase("Cancellation")){
                Long startDate = (Long)task.get("Date");
                String taskStartDate = String.valueOf(startDate); 

                Long startTime = (Long)task.get("StartTime");
                String startTime1 = String.valueOf(startTime);
                float taskStartTime = Float.parseFloat(startTime1); 

                double duration = (double)task.get("Duration");
                float duration1  = (float) duration;
                Float taskDuration = new Float(duration1);
                String category = "Anti-Task"; 

                AntiTask newTask = new AntiTask(); 

                newTask.setName(taskName);
                newTask.setType(taskType); 
                newTask.setStartDate(taskStartDate);
                newTask.setStartTime(taskStartTime); 
                newTask.setDuration(taskDuration); 

                deleteAntiTask(newTask); 
             }
             else {
                    System.out.println("Schedule invalid. There's a task with an invalid type.");
                }
            }
        }
      
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
    }

    public static void writeSchedule(String userInput) throws IOException, ParseException{
        
        JSONArray jsonArray = new JSONArray();
        boolean found = false;

        try{
            FileWriter file = new FileWriter(userInput);

            for(int i = 0; i < taskList.size(); i++){
                JSONObject jsonObj = new JSONObject();
                if(taskList.get(i).getCategory().equalsIgnoreCase("recurring") || taskList.get(i).getCategory().equalsIgnoreCase("Recurring")){
                    jsonObj.put("Name", taskList.get(i).getName());
                    jsonObj.put("Type", taskList.get(i).getType());
                    Long startDate = Long.parseLong(taskList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((taskList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime",startTime);
                    jsonObj.put("Duration", taskList.get(i).getDuration());
                    Long endDate = Long.parseLong(taskList.get(i).getEndDate());
                    jsonObj.put("EndDate", endDate);
                    jsonObj.put("Frequency", taskList.get(i).getFrequency());
                    jsonArray.add(jsonObj);
                    
                    
                }
                else if(taskList.get(i).getCategory().equalsIgnoreCase("Transient") || taskList.get(i).getCategory().equalsIgnoreCase("transient")){
                    jsonObj.put("Name", taskList.get(i).getName());
                    jsonObj.put("Type", taskList.get(i).getType());
                    Long startDate = Long.parseLong(taskList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((taskList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime", startTime);
                    jsonObj.put("Duration", taskList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    
                
                }
                /*else if(taskList.get(i).getCategory().equalsIgnoreCase("Anti-Task") || taskList.get(i).getCategory().equalsIgnoreCase("anti-task")){
                    jsonObj.put("Name", requestList.get(i).getName());
                    jsonObj.put("Type", requestList.get(i).getType());
                    Long startDate = Long.parseLong(requestList.get(i).getStartDate());
                    jsonObj.put("Date",startDate);
                    jsonObj.put("StartTime", requestList.get(i).getStartTime());
                    jsonObj.put("Duration", requestList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    file.write(jsonObj.toJSONString());
                    
                } */
                else {
                    found = true;
                }
            }
            if(found){
                System.out.println("Invalid type.");
            }
            file.write(jsonArray.toJSONString());
            file.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

    }
    public static void writeScheduleDaily(int userDateInt, int userMonthInt, List<Task> dailyList){
        JSONArray jsonArray = new JSONArray();
        boolean found = false;

        try{
            FileWriter file = new FileWriter("D:/Daily.json");

            for(int i = 0; i < dailyList.size(); i++){
                JSONObject jsonObj = new JSONObject();
                if(dailyList.get(i).getCategory().equalsIgnoreCase("recurring") || dailyList.get(i).getCategory().equalsIgnoreCase("Recurring")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime",startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    Long endDate = Long.parseLong(dailyList.get(i).getEndDate());
                    jsonObj.put("EndDate", endDate);
                    jsonObj.put("Frequency", dailyList.get(i).getFrequency());
                    jsonArray.add(jsonObj);
                    
                    
                }
                else if(dailyList.get(i).getCategory().equalsIgnoreCase("Transient") || dailyList.get(i).getCategory().equalsIgnoreCase("transient")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime", startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    
                
                }
                /*else if(taskList.get(i).getCategory().equalsIgnoreCase("Anti-Task") || taskList.get(i).getCategory().equalsIgnoreCase("anti-task")){
                    jsonObj.put("Name", requestList.get(i).getName());
                    jsonObj.put("Type", requestList.get(i).getType());
                    Long startDate = Long.parseLong(requestList.get(i).getStartDate());
                    jsonObj.put("Date",startDate);
                    jsonObj.put("StartTime", requestList.get(i).getStartTime());
                    jsonObj.put("Duration", requestList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    file.write(jsonObj.toJSONString());
                    
                } */
                else {
                    found = true;
                }
            }
            if(found){
                System.out.println("Invalid type.");
            }
            file.write(jsonArray.toJSONString());
            file.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
    }
    public static void writeScheduleWeekly(int userDateInt, int userMonthInt, List<Task> dailyList){
        JSONArray jsonArray = new JSONArray();
        boolean found = false;

        try{
            FileWriter file = new FileWriter("D:/Weekly.json");

            for(int i = 0; i < dailyList.size(); i++){
                JSONObject jsonObj = new JSONObject();
                if(dailyList.get(i).getCategory().equalsIgnoreCase("recurring") || dailyList.get(i).getCategory().equalsIgnoreCase("Recurring")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime",startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    Long endDate = Long.parseLong(dailyList.get(i).getEndDate());
                    jsonObj.put("EndDate", endDate);
                    jsonObj.put("Frequency", dailyList.get(i).getFrequency());
                    jsonArray.add(jsonObj);
                    
                    
                }
                else if(dailyList.get(i).getCategory().equalsIgnoreCase("Transient") || dailyList.get(i).getCategory().equalsIgnoreCase("transient")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime", startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    
                
                }
                /*else if(taskList.get(i).getCategory().equalsIgnoreCase("Anti-Task") || taskList.get(i).getCategory().equalsIgnoreCase("anti-task")){
                    jsonObj.put("Name", requestList.get(i).getName());
                    jsonObj.put("Type", requestList.get(i).getType());
                    Long startDate = Long.parseLong(requestList.get(i).getStartDate());
                    jsonObj.put("Date",startDate);
                    jsonObj.put("StartTime", requestList.get(i).getStartTime());
                    jsonObj.put("Duration", requestList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    file.write(jsonObj.toJSONString());
                    
                } */
                else {
                    found = true;
                }
            }
            if(found){
                System.out.println("Invalid type.");
            }
            file.write(jsonArray.toJSONString());
            file.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
    }
    public static void writeScheduleMonthly(int userDateInt, int userMonthInt, List<Task> dailyList){
        JSONArray jsonArray = new JSONArray();
        boolean found = false;

        try{
            FileWriter file = new FileWriter("D:/Monthly.json");

            for(int i = 0; i < dailyList.size(); i++){
                JSONObject jsonObj = new JSONObject();
                if(dailyList.get(i).getCategory().equalsIgnoreCase("recurring") || dailyList.get(i).getCategory().equalsIgnoreCase("Recurring")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime",startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    Long endDate = Long.parseLong(dailyList.get(i).getEndDate());
                    jsonObj.put("EndDate", endDate);
                    jsonObj.put("Frequency", dailyList.get(i).getFrequency());
                    jsonArray.add(jsonObj);
                    
                    
                }
                else if(dailyList.get(i).getCategory().equalsIgnoreCase("Transient") || dailyList.get(i).getCategory().equalsIgnoreCase("transient")){
                    jsonObj.put("Name", dailyList.get(i).getName());
                    jsonObj.put("Type", dailyList.get(i).getType());
                    Long startDate = Long.parseLong(dailyList.get(i).getStartDate());
                    jsonObj.put("StartDate",startDate);
                    Long startTime = (new Double((dailyList.get(i).getStartTime()))).longValue();
                    jsonObj.put("StartTime", startTime);
                    jsonObj.put("Duration", dailyList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    
                
                }
                /*else if(taskList.get(i).getCategory().equalsIgnoreCase("Anti-Task") || taskList.get(i).getCategory().equalsIgnoreCase("anti-task")){
                    jsonObj.put("Name", requestList.get(i).getName());
                    jsonObj.put("Type", requestList.get(i).getType());
                    Long startDate = Long.parseLong(requestList.get(i).getStartDate());
                    jsonObj.put("Date",startDate);
                    jsonObj.put("StartTime", requestList.get(i).getStartTime());
                    jsonObj.put("Duration", requestList.get(i).getDuration());
                    jsonArray.add(jsonObj);
                    file.write(jsonObj.toJSONString());
                    
                } */
                else {
                    found = true;
                }
            }
            if(found){
                System.out.println("Invalid type.");
            }
            file.write(jsonArray.toJSONString());
            file.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
    }

               
}


