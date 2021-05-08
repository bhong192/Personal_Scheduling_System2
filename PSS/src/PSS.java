import java.util.*;
import org.json.simple.*; 

public class PSS {
    
    ArrayList<Task> taskList = new ArrayList<Task>(); 

    public static void main(String[] args){
        createTask(); 
    }

    public static void createTask(){
        
        // create JSON object to store all fields in
        

        // for reading user input
        Scanner scanner = new Scanner(System.in); 


        System.out.println("Type of task? (Recurring/Transient)"); 
        String taskCategory = scanner.nextLine(); 

        if(taskCategory.equalsIgnoreCase("Recurring") || taskCategory.equalsIgnoreCase("recurring")){
            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 
            
            System.out.println("Input the type of the task: \n");
            String taskType = scanner.nextLine();  

            System.out.println("Input the start date: \n"); 
            int taskStartDate = scanner.nextInt(); 

            System.out.println("Input the duration: \n");
            int taskDuration = scanner.nextInt(); 

            System.out.println("Input the end date: \n");
            int taskEndDate  = scanner.nextInt(); 

            System.out.println("Input the frequency: \n"); 
            int taskFreq = scanner.nextInt(); 
        }
        
        else if(taskCategory.equalsIgnoreCase("Transient") || taskCategory.equalsIgnoreCase("transient")){

            System.out.println("Input the name of your task: \n"); 
            String taskName = scanner.nextLine(); 
        
            System.out.println("Input the type of the task: \n");
            String taskType = scanner.nextLine();  

            System.out.println("Input the start date: \n"); 
            int taskStartDate = scanner.nextInt(); 

            System.out.println("Input the duration: \n");
            int taskDuration = scanner.nextInt(); 

            System.out.println("Input the end date: \n");
            int taskEndDate = scanner.nextInt(); 
        } 
        else{
            System.out.println("Not a valid task type!"); 
        }     

        scanner.close(); 
    }

}

