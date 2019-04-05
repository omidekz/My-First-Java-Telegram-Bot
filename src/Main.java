import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        System.out.println();
//        String apiKey = in.next();
        System.out.println("Enter you'r bot token");
        String token = in.next();
        System.out.println("Enter you'r bot username[without @]");
        String username = in.next();
        FINAL_NEEDS.BOT_TOKEN = token.equals("")?FINAL_NEEDS.BOT_TOKEN:token;
        FINAL_NEEDS.BOT_ID = username.equals("")?FINAL_NEEDS.BOT_ID:username;
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MyFirstBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
