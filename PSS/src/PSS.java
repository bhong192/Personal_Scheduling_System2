import java.lang.ProcessBuilder.Redirect.Type;
import java.util.*;
import org.json.simple.*; 

public class PSS {
    
    public static ArrayList<Task> taskList = new ArrayList<Task>(); 

    public static void main(String[] args){
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
                taskName = scanner.next();
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


        System.out.println("Type of task? (Recurring/Transient/Anti-Task)"); 
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
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
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
            taskList.add(newTask); 

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
            if(taskList.get(i).getStartDate().equals(taskStartDate) && (taskList.get(i).getStartTime() + taskList.get(i).getDuration()) > taskStartTime){
                while((taskStartTime + taskDuration) > taskList.get(i).getStartTime()){
                    System.out.println("Invalid time. Re-enter a new duration.");
                    taskStartTime = scanner.nextFloat();
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
            System.out.println("End date: " + taskList.get(i).getEndDate());
            System.out.println("Frequency: " + taskList.get(i).getFrequency());
        }
        else if (taskList.get(i).getCategory().equalsIgnoreCase("Transient") || taskList.get(i).getCategory().equalsIgnoreCase("transient")) {
            System.out.println("Name: " + taskList.get(i).getName());
            System.out.println("Type: " + taskList.get(i).getType());
            System.out.println("Start date: " + taskList.get(i).getStartDate());
            System.out.println("Start time: " + taskList.get(i).getStartTime());
            System.out.println("Duration: " + taskList.get(i).getDuration());
        }
            
    }
}

