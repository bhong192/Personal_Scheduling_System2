public class RecurringTask extends Task {
    
    public int frequency; 
   
       public RecurringTask(){
   
       }
       public RecurringTask(String name, String type, String startDate, float startTime, float duration, String endDate, int frequency){
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
   