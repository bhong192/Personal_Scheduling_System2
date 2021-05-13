import java.lang.ProcessBuilder.Redirect.Type;
import java.util.*;
import org.json.simple.*; 

public class PSS {
    
    public static ArrayList<Task> taskList = new ArrayList<Task>(); 

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); 
        int input;
        String taskName;
        
        scanner.nextLine();
    
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
                System.out.println("Enter task's name: ");
                // clear buffer or else code breaks
                scanner.nextLine();
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


        System.out.println("Type of task? (Recurring/Transient)"); 
        String taskCategory = scanner.nextLine(); 

        if(taskCategory.equalsIgnoreCase("Recurring") || taskCategory.equalsIgnoreCase("recurring")){
            
            RecurringTask newTask = new RecurringTask(); 

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 
            for(int i = 0; i < taskList.size(); i++ ){
                if(taskList.get(i).getName().equals(taskName)){
                    System.out.println("Task name is not unique. Re-enter a new name."); 
                    taskName = scanner.nextLine(); 
                }
            }


            
            String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"}; 
            System.out.println("Input the type of the task: \n");
            String taskType = scanner.nextLine();  

            // check if taskType is valid 
            while(!(Arrays.asList(validTypes).contains(taskType))){
                    System.out.println("Invalid task type! Input a new task type. \n");
                    taskType = scanner.nextLine();                
            }

            System.out.println("Input the start date: \n"); 
            String taskStartDate = scanner.nextLine(); 
            // check if start date is valid
            verifyDate(taskStartDate, scanner);

            // entering input for start time
            System.out.println("Input the start time: \n");
            int taskStartTime = scanner.nextInt();

            
            System.out.println("Input the duration: \n");
            
            float taskDuration = scanner.nextFloat(); 
            
            while(taskDuration > 23.75 || taskDuration < 0.25){
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
            scanner.nextLine(); 

            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setDuration(taskDuration);
            newTask.setEndDate(taskEndDate);
            newTask.setFrequency(taskFreq); 
            newTask.setCategory(taskCategory);
            newTask.setStartTime(taskStartTime);
            taskList.add(newTask); 

        }
        
        else if(taskCategory.equalsIgnoreCase("Transient") || taskCategory.equalsIgnoreCase("transient")){

            TransientTask newTask = new TransientTask();

            System.out.println("Input the name of your task: "); 
            String taskName = scanner.nextLine(); 
        
            System.out.println("Input the type of the task: ");
            String taskType = scanner.nextLine();  

            System.out.println("Input the start date: "); 
            String taskStartDate = scanner.nextLine(); 

            System.out.println("Input the end date: ");
            String taskEndDate = scanner.nextLine(); 

            System.out.println("Input the duration: ");
            Float taskDuration = scanner.nextFloat();  

            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setDuration(taskDuration);
            newTask.setEndDate(taskEndDate);
            newTask.setCategory(taskCategory);
            taskList.add(newTask); 
        } 
        else{
            System.out.println("Not a valid task type!"); 
        }     


    }

    public static void verifyDate(String taskStartDate, Scanner scanner){
            //Scanner scanner2 = new Scanner(System.in);
            // check if start date is valid
            // 20200415
            String month = taskStartDate.substring(4,5);
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
    public void verifyDuration(){

    }
    public static void verifyCollision(float taskDuration, String taskStartDate, float taskStartTime, Scanner scanner){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).getStartDate() == taskStartDate ){
                if(taskStartTime > taskList.get(i).getStartTime() && (taskList.get(i).getStartTime() + taskList.get(i).getDuration()) > taskStartTime){
                    System.out.println("Invalid time. Re-enter a new time");
                }
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
        System.out.println("6 - Exit to main")
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
                int taskStartTime = scanner.nextInt();
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
                int taskStartTime = scanner.nextInt();
                taskList.get(i).setStartTime(taskStartTime);
                displayMenuR(i);
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
            System.out.println("End date: " + taskList.get(i).getEndDate());
            System.out.println("Frequency: " + taskList.get(i).getFrequency());
        }
        else {
            System.out.println("Name: " + taskList.get(i).getName());
            System.out.println("Type: " + taskList.get(i).getType());
            System.out.println("Start date: " + taskList.get(i).getStartDate());
            System.out.println("Start time: " + taskList.get(i).getStartTime());
            System.out.println("Duration: " + taskList.get(i).getDuration());
        }
            
    }
}

