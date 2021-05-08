import org.json.simple.JSONObject; 

public class App {
    public static void main(String[] args) throws Exception {
        JSONObject obj =  new JSONObject();
        obj.put("name", "Taha"); 
        obj.put("birth", "Sept-1st"); 
        System.out.println(obj);
    }
}
