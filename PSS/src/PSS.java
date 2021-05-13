import java.util.*;
import org.json.simple.*; 

public class PSS {
    
    public static ArrayList<Task> taskList = new ArrayList<Task>(); 

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); 
        createTask(scanner);
        createTask(scanner);  
        scanner.close(); 
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
            
            System.out.println("Input the duration: \n");
            float taskDuration = scanner.nextFloat(); 
            while(taskDuration > 23.75 || taskDuration < 0.25){
                System.out.println("Invalid duration. Please input a valid duration between 0.25 and 23.75"); 
                taskDuration = scanner.nextFloat(); 
            }
            scanner.nextLine(); 
            
            System.out.println("Input the end date: \n");
            String taskEndDate  = scanner.nextLine(); 
            // check if end date is valid
            verifyDate(taskEndDate, scanner);
            verifyEndDate(taskEndDate, taskStartDate, scanner); 
            
            System.out.println("Input the frequency(1/7): \n"); 
            int taskFreq = scanner.nextInt(); 
            System.out.println(taskFreq); 
            while(taskFreq != 7 || taskFreq != 1){
                System.out.println("Invalid frequency. Please input a duration of 1 or 7."); 
                taskFreq = scanner.nextInt(); 
            }
            scanner.nextLine(); 

            newTask.setName(taskName); 
            newTask.setType(taskType); 
            newTask.setStartDate(taskStartDate);
            newTask.setDuration(taskDuration);
            //newTask.setEndDate(taskEndDate);
            newTask.setFrequency(taskFreq); 
            
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
            String month = taskStartDate.substring(4,6);
            if(month.substring(0,1).equals("0")){
                month = taskStartDate.substring(5,6); 
            } 
            
            
            int monthInt = Integer.parseInt(month);
            //System.out.println("Month int: "  + monthInt); 
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

            else{
                while(monthInt > 12 || monthInt < 1){
                    System.out.println("Your month is invalid. Re-enter the end date."); 
                    taskStartDate = scanner.nextLine(); 
                    date = taskStartDate.substring(6,8);
                    dateInt = Integer.parseInt(date); 
                }
            }
            
    }

    public static void verifyEndDate(String taskEndDate, String taskStartDate, Scanner scanner){

        // Step 1: Check if the ending month is between 1-12 and whether or not it precedes the start month.
            String endmonth = taskEndDate.substring(4,6);
            if(endmonth.substring(0,1).equals("0")){
                endmonth = taskEndDate.substring(5,6); 
            } 
            String startMonth = taskStartDate.substring(4,6);
            if(startMonth.substring(0,1).equals("0")){
                startMonth = taskStartDate.substring(5,6); 
            } 

            int startmonthInt = Integer.parseInt(startMonth);
            if(startmonthInt > 12 || startmonthInt < 1){
                System.out.println("Invalid Month."); 
            }
            int endmonthInt = Integer.parseInt(endmonth);
            if(endmonthInt > 12 || endmonthInt < 1 || endmonthInt < startmonthInt){ // checks if endMonth is before startMonth
                System.out.println("Invalid Month.");
                // NEED TO RE-ENTER DATE 
                
            }

            /*
                Step 2: Check if the date is within the bounds for the specified month
                - Special Case: If both months are the same, check if the end date precedes the start date.
            */
            String endDate = taskEndDate.substring(6,8); 
            int enddateInt = Integer.parseInt(endDate); 
            String startDate = taskStartDate.substring(6,8); 
            int startdateInt = Integer.parseInt(startDate); 
            
            // //ISSUE here with re-entering a new date. Will not accept later dates in the same month or low numbered dates in following months.
            // if(endmonthInt == startmonthInt){
            //     while(enddateInt < startdateInt){
            //         System.out.println("Invalid date. The entered date precedes the start date. Re-enter the end date."); 
            //         taskEndDate = scanner.nextLine();
            //         endDate = taskEndDate.substring(6,8);
            //         enddateInt = Integer.parseInt(endDate);
                    
            //         endmonth = taskEndDate.substring(5,6); 
            //         endmonthInt = Integer.parseInt(endmonth); 
            //         if(endmonthInt > startmonthInt){
            //             break; 
            //         }
            //         //endmonth = taskEndDate.substring(4,5);
            //     }

            //         taskEndDate = scanner.nextLine();
            //         endDate = taskEndDate.substring(6,8);
            //         enddateInt = Integer.parseInt(endDate);
                    
            //         endmonth = taskEndDate.substring(5,6); 
            //          endmonthInt = Integer.parseInt(endmonth); 

            // }

            if(endmonthInt == 1 || endmonthInt == 3 || endmonthInt == 5 || endmonthInt == 7 || endmonthInt == 8 || endmonthInt == 10 || endmonthInt == 12){
                while(enddateInt > 31 || enddateInt < 1){
                    System.out.println("Your date is not within the month. Re-enter the end date."); 
                    taskEndDate = scanner.nextLine(); 
                    endDate = taskEndDate.substring(6,8);
                    enddateInt = Integer.parseInt(endDate); 
                }
            }

            else if (endmonthInt == 4 || endmonthInt == 6 || endmonthInt == 9 || endmonthInt == 11){
                while (enddateInt > 30 || enddateInt < 1){
                    System.out.println("Your date is not within the month. Re-enter the end date."); 
                    taskEndDate = scanner.nextLine(); 
                    endDate = taskStartDate.substring(6,8);
                    enddateInt = Integer.parseInt(endDate); 
                }
            }

            // February
            else if (endmonthInt == 2){
                while(enddateInt > 29 || enddateInt < 1){
                    System.out.println("Your date is not within the month. Re-enter the end date."); 
                    taskEndDate = scanner.nextLine(); 
                    endDate = taskEndDate.substring(6,8);
                    enddateInt = Integer.parseInt(endDate); 
                }
            }

            else{
                while(endmonthInt > 12 || endmonthInt <1){
                    System.out.println("Your month is invalid. Re-enter the end date."); 
                    taskEndDate = scanner.nextLine(); 
                    endDate = taskEndDate.substring(6,8);
                    enddateInt = Integer.parseInt(endDate); 
                }
            }
    }
}

