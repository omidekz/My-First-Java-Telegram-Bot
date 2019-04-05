import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MyFirstBot extends TelegramLongPollingBot {

    boolean send_Message(String mes,long chatId,Integer replyTo){
        SendMessage sendMessage = sendMessage(mes,chatId,replyTo);
        try {
            sendApiMethod(sendMessage);
            return true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    SendMessage sendMessage(String mes,long chatId,Integer replyTo){
        SendMessage message = new SendMessage(chatId,mes);
        if(replyTo!=null)
            message.setReplyToMessageId(replyTo);
        return message;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
        if (update.hasMessage())
            User.CASave(update);

        if (update.hasMessage()) {
            String[] command = update.getMessage().getText().split(" ");
            if(command.length != 2) {
                System.out.println("Len != 2");
                return;
            }

            if (command[0].equals(COMMANDS._SHORT) // _SHORT = "/shortlink" for pv
                    || command[0].toLowerCase().equals(COMMANDS.__SHORT)) { // __SHORT = _SHORT+"@hmchi_bot" for gp

                String url = command[1];
                Integer messageId = update.getMessage().getMessageId();
                Long chatId = update.getMessage().getChatId();

                ShortLink(chatId, messageId, url);
            }
        }
    }

    private void ShortLink(Long chatId, Integer messageId, String url) {
        String ms = URL.chekURL(url);
        if(!ms.equals(URL._OK)){
            send_Message(ms,chatId,messageId);
            return;
        }
        Map<String,String> heards = new HashMap<>();
        heards.put(ReBrand_shortLinks.Content_type_key,
                ReBrand_shortLinks.Content_type_value);
        heards.put(ReBrand_shortLinks.APIKEY_key,
                ReBrand_shortLinks.APIKEY_value);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ReBrand_shortLinks.Destination_key, url);
        jsonObject.put(ReBrand_shortLinks.Domain_key,
                new JSONObject().put(ReBrand_shortLinks.FullName_key,ReBrand_shortLinks.FullName_value));

        Unirest.post("https://api.rebrandly.com/v1/links")
                .headers(heards)
                .body(jsonObject)
                .asJsonAsync(new Callback<>() {

                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        String res = httpResponse.getBody()
                                .getObject()
                                .get(ReBrand_shortLinks.SHORTEN_KEY)
                                .toString();

                        System.out.println(res);
                        send_Message(res,chatId,messageId);
                    }

                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("Failed");
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("Cancelled");
                    }
                });
    }

    @Override
    public String getBotUsername() {
        return FINAL_NEEDS.BOT_ID;
    }

    @Override
    public String getBotToken() {
        return FINAL_NEEDS.BOT_TOKEN;
    }
}
