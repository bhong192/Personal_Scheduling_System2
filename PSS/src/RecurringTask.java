public class RecurringTask extends Task {
    
    public int frequency; 
    public String endDate; 
   
       public RecurringTask(){
   
       }
       public RecurringTask(String name, String type, String startDate, float startTime, float duration, String endDate, int frequency){
           super(name, type, startDate, startTime, duration); 
           this.frequency = frequency; 
           this.endDate = endDate; 
   
       }
   
      public int getFrequency(){
          return this.frequency; 
      }
      public void setFrequency(int freq){
          this.frequency = freq; 
      }

      public String endDate(){
          return this.endDate(); 
      }
      public void setEndDate(String endDate){
        this.endDate = endDate; 
      }
   
   }
   