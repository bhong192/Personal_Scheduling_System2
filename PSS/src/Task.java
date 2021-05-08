public class Task{

    private String name;
    private String type;
    private int startDate;
    private float startTime;
    private float duration;
    private int endDate;

    public Task(String name, String type, int startDate, float startTime, float duration, int endDate){
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.endDate = endDate;
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

    public int getStartDate(){
        return this.startDate;
    }
    public void setStartDate(int startDate){
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

    public int getEndDate(){
        return this.endDate;
    }
    public void setEndDate(int endDate){
        this.endDate = endDate; 
    }
}
