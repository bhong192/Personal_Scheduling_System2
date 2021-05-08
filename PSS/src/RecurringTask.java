public class RecurringTask extends Task {
    
 public int frequency; 

    public RecurringTask(String name, String type, int startDate, float startTime, float duration, int endDate, int frequency){
        super(name, type, startDate, startTime, duration, endDate); 
        this.frequency = frequency; 

    }

   public int getFrequency(){
       return this.frequency; 
   }
   public void setFrequency(int freq){
       this.frequency = freq; 
   }

}
