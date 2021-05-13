public class Task{

    private String name;
    private String type;
    private String startDate;
    private float startTime;
    private float duration;
    

    public Task(){

    }
    public Task(String name, String type, String startDate, float startTime, float duration){
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getStartDate(){
        return this.startDate;
    }
    public void setStartDate(String startDate){
        this.startDate = startDate; 
    }

    public float getStartTime(){
        return this.startTime;
    }
    public void setStartTime(int startTime){
        this.startTime = startTime; 
    }

    public float getDuration(){
        return this.duration;
    }
    public void setDuration(float duration){
        this.duration = duration; 
    }

}
