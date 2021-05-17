import java.io.FileNotFoundException;
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
        String taskName;
    
        displayMenu();
        input = scanner.nextInt();
        
        
        while(input != 7){

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
                
            }
            else if(input == 6){
                System.out.println("Enter the schedule file's name: "); 
                scanner.nextLine(); 
                String fileName = scanner.nextLine(); 
                readSchedule(fileName);
                displayMenu(); 
                input = scanner.nextInt(); 
            }
        }
        scanner.close();
    }
    
    public static void displayMenu(){
        System.out.println("-----------------------------");
        System.out.println("1 - Enter 1 to create Task: ");
        System.out.println("2 - Enter 2 to view Task: ");
        System.out.println("3 - Enter 3 to delete Task: ");
        System.out.println("4 - Enter 4 to edit Task: ");
        System.out.println("5 - Enter 5 to write Task: ");
        System.out.println("6 - Enter 6 to read Task: ");
        System.out.println("7 - exit");
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
        else if(taskCategory.equalsIgnoreCase("Transient") || taskCategory.equalsIgnoreCase("transient") || taskCategory.equalsIgnoreCase("Anti-task") || 
        taskCategory.equalsIgnoreCase("anti-task")){

            TransientTask newTask = new TransientTask();

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 

            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(taskName) && !(taskCategory.equalsIgnoreCase("anti-task"))){
                    System.out.println("Task name is not unique. Re-enter a new name."); 
                    taskName = scanner.nextLine(); 
                }
            }
            
            if(taskCategory.equalsIgnoreCase("Anti-task")){
                
            }
            String[] validTypes = {"Visit", "Shopping", "Appointment", "Cancellation"}; 
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
            verifyCollision(taskStartDate, taskStartTime, scanner);

            while(taskStartTime > 23.75 || taskStartTime < 0.25 || taskStartTime % (.25) != 0){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskStartTime= scanner.nextFloat(); 
            }
            

            System.out.println("Input the duration: ");
            Float taskDuration = scanner.nextFloat();  
            verifyCollision(taskDuration, taskStartDate, taskStartTime, scanner);
        
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
            
            if (newTask.getType().equalsIgnoreCase("Cancellation") || newTask.getType().equalsIgnoreCase("cancellation")){
                
                for(int i = 0; i < taskList.size(); i++){
                    if( (taskName.equals(taskList.get(i).getName())) && (taskStartTime.equals(taskList.get(i).getStartTime())) && (taskStartDate.equals(taskList.get(i).getStartDate()) )){

                        if(taskList.get(i).getCategory().equals("Recurring") || taskList.get(i).getCategory().equals("recurring")){
                            deleteTask(taskList.get(i).getName()); 
                        }
                        
                        else{
                            System.out.println("The task attempted to be deleted is not of type recurring"); 
                        }

                    }
                    else {
                        System.out.println("No matching instance of a task was found"); 
                    }
                }
            }

            if(!(newTask.getType().equalsIgnoreCase("Cancellation") || newTask.getType().equalsIgnoreCase("cancellation"))){
                taskList.add(newTask); 
            }
        
        } 
        else{
            System.out.println("Not a valid task type!"); 
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

    public void sorting(){
        ArrayList<Task> a = new ArrayList<>();
        
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
        for(int i = 0; i < taskList.size(); i++){
            if(taskName.equals(taskList.get(i).getName())){
                System.out.println("-----------------------------");
                display(i);
            }
            // not printing this after searching for a deleted task
            else {
                System.out.println("Task not found");
            }
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

                System.out.println(taskType); 

                if(taskType.equalsIgnoreCase("Class") || taskType.equalsIgnoreCase("Study") || taskType.equalsIgnoreCase("Exercise") || taskType.equalsIgnoreCase("Sleep") ||
                taskType.equalsIgnoreCase("Work") || taskType.equalsIgnoreCase("Meal")){

                //Object[] recurringAttributes = new Object[7];
                
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
                //Integer taskFrequency = new Integer(freq1); 

                RecurringTask newTask = new RecurringTask(taskName, taskType, taskStartDate, taskStartTime, taskDuration, taskEndDate, taskFrequency); 
                taskList.add(newTask); 

                // recurringAttributes[0] = taskName; 
                // recurringAttributes[1] = taskType;
                // recurringAttributes[2] = taskStartDate;
                // recurringAttributes[3] = taskStartTime;
                // recurringAttributes[4] = taskEndDate;
                // recurringAttributes[5] = taskDuration;
                // recurringAttributes[6] = taskFrequnecy;
                 
                // createTaskFromFile(recurringAttributes);
            }

            else if(taskType.equalsIgnoreCase("Visit") || taskType.equalsIgnoreCase("Shopping") || taskType.equalsIgnoreCase("Appointment") ||
            taskType.equalsIgnoreCase("Cancellation")){
                // Object[] transientAttributes = new Object[5];

                Long startDate = (Long)task.get("StartDate");
                String taskStartDate = String.valueOf(startDate); 

                Long startTime = (Long)task.get("StartTime");
                String startTime1 = String.valueOf(startTime);
                float taskStartTime = Float.parseFloat(startTime1); 

                double duration = (double)task.get("Duration");
                float duration1  = (float) duration;
                Float taskDuration = new Float(duration1);
                String category = "Transient"; 

                TransientTask newTask = new TransientTask(taskName, taskType, taskStartDate, taskStartTime, taskDuration, category); 
                taskList.add(newTask); 


                // transientAttributes[0] = taskName; 
                // transientAttributes[1] = taskType;
                // transientAttributes[2] = taskStartDate;
                // transientAttributes[3] = taskStartTime;
                // transientAttributes[4] = taskDuration;

                //createTaskFromFile(transientAttributes);
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

    public static void createTaskFromFile(Object[] attributes){

        if(attributes.length == 7){

            // check if task's name is unique 
            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(attributes[0])){
                    System.out.println("Task name is not unique."); 
                    return ; 
                }
            }

            
            // check if start date is valid 
            String taskStartDate = String.valueOf(attributes[2]); 

            String month = taskStartDate.substring(4,6);
            if(month.substring(0).equals("0")){
                month = taskStartDate.substring(5,6); 
            } 
            
            int monthInt = Integer.parseInt(month);
            if(monthInt > 12 || monthInt < 1){
                return ;  
            }

            String date = taskStartDate.substring(6,8); 
            int dateInt = Integer.parseInt(date); 
            
            if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 || monthInt == 8 || monthInt == 10 ||monthInt == 12){
                if (dateInt > 31 || dateInt < 1){
                    System.out.println("Invalid date."); 
                    return ; 
                }
            }

            else if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt ==11){
                if (dateInt > 30 || dateInt < 1){
                    System.out.println("Invalid date."); 
                    return ;
                }
            }

            // February
            else if (monthInt == 2){
                if (dateInt > 29 || dateInt < 1){
                    System.out.println("Invalid date."); 
                    return ;
                }
            }

            
            // check start time 
            float taskStartTime = Float.parseFloat((String) attributes[3]); 
            for(int i = 0; i < taskList.size(); i++){
                if(taskList.get(i).getStartDate().equals(taskStartDate)){
                    while(taskStartTime >= taskList.get(i).getStartTime() && (taskList.get(i).getStartTime() + taskList.get(i).getDuration()) > taskStartTime){
                        System.out.println("Invalid start time. ");
                        return ; 
                    }
                }
            }
            while(taskStartTime > 23.75 || taskStartTime < 0.25 || taskStartTime % (.25) != 0){
                System.out.println("Invalid duration."); 
                return ;
            }

            // check end date

            String taskEndDate = (String) attributes[4];

            String endmonth = taskEndDate.substring(4,5);
            if(endmonth.substring(0).equals("0")){
                endmonth = taskEndDate.substring(5,6); 
            } 
            
            month = taskStartDate.substring(4,5);
            if(month.substring(0).equals("0")){
                month = taskStartDate.substring(5,6); 
            } 
            monthInt = Integer.parseInt(month);
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
                if(enddateInt > 31 || enddateInt < 1){
                    System.out.println("Invalid end date."); 
                    return ;  
                }
            }

            else if (endmonthInt == 4 || endmonthInt == 6 || endmonthInt == 9 || endmonthInt == 11){
                if(enddateInt > 30 || enddateInt < 1){
                    System.out.println("Invalid end date."); 
                    return ; 
                }
            }

            // February
            else if (endmonthInt == 2){
                if(enddateInt > 29 || enddateInt < 1){
                    System.out.println("Invalid end date."); 
                    return ;  
                }
            }

             // check duration
            Float value = (Float) attributes[4]; 
            float taskDuration  =  value.floatValue(); 
        
        
            for(int i = 0; i < taskList.size(); i++){
                if(taskList.get(i).getStartDate().equals(taskStartDate)){
                    if(taskDuration < taskList.get(i).getStartTime() && (taskDuration + taskStartTime) > (taskList.get(i).getDuration() + taskList.get(i).getStartTime())) {
                        System.out.println("Invalid duration.");
                        return ; 
                }
            }
        }
            
            if(taskDuration > 23.75 || taskDuration < 0.25 || taskDuration % (.25) != 0){
                System.out.println("Invalid duration."); 
                return ; 
            }

            int taskFrequency = (int) attributes[6]; 
            String taskName = (String) attributes[0]; 
            String taskType = (String) attributes[1]; 


            RecurringTask newTask = new RecurringTask(taskName, taskType, taskStartDate, taskStartTime, taskDuration, taskEndDate, taskFrequency); 
            taskList.add(newTask);     
    }
        }

        


       
}


