import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.Scanner;

public class User {
    private String uniq_id;
    private String name;
    private String id;
    private static final String USERS_ADDRESS = "USERS.XXX";

    public static String getUsersAddress() {
        return USERS_ADDRESS;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public User(String uniq_id, String name, String id) {
        this.uniq_id = uniq_id;
        this.name = name.replaceAll(" ","-");
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",uniq_id,name,id);
    }

    static synchronized void save(User user){
        String str = user.toString();
        try {
            FileOutputStream fos = new FileOutputStream(getUsersAddress(),true);
            fos.write(str.concat("\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static synchronized boolean contain(User user){
        try {
            InputStream is = new FileInputStream(getUsersAddress());
            Scanner sc = new Scanner(is);
            while (sc.hasNext()){
                String line = sc.nextLine();
                String[] attrs = line.split(" ");
                if(attrs[0].equals(user.getUniq_id()))
                    return true;
            }
            is.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    static synchronized void CASave(User user){
        if(!contain(user))
            save(user);
    }
    static synchronized void CASave(Update update){
        User user = mkUser(update);
        CASave(user);
    }
    static synchronized User mkUser(Update update){
        String name = update.getMessage().getFrom().getFirstName();
        String uniqId = update.getMessage().getFrom().getId()+"";
        String id = update.getMessage().getFrom().getUserName();
        return new User(uniqId,name,id);
    }

}
