public class TransientTask extends Task{
    
    private String name;
    private String type;
    private String startDate;
    private float startTime;
    private float duration; 
    private String category;

    public TransientTask(){

    }

    public TransientTask(String name, String type, String startDate, float startTime, float duration, String category){
         this.name = name; 
         this.type = type; 
         this.startDate = startDate; 
         this.startTime = startTime; 
         this.duration = duration; 
         this.category = category; 
    }

}

