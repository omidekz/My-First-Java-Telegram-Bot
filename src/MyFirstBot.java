import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    }

    @Override
    public String getBotUsername() {
        return "Hmchi_bot";
    }

    @Override
    public String getBotToken() {
        return "198400972:AAGzImy9k_RTlMCTXk_2bcJNRCa-eNhTpOo";
    }
}
