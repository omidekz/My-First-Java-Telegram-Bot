package short_tutorial;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Test_Bot extends TelegramLongPollingBot { // each of your bots should extend from Telegram LongPolling Bot

    private String token;
    private String username;
    Test_Bot(String Token,String Username){
        token = Token;
        username = Username;
    }

    // and you have to override 3 method
    //      -onUpdateReceived
    //      -getBotUsername => it's you'r bot username without @
    //      -getBotToken => get from botfather and copy-paste here
    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()) {
            Message message = update.getMessage();
            String messageText = message.getText();
            long chatId = message.getChatId(); //this number is need for send message

            User user = message.getFrom();
            String name = user.getFirstName();
            String lastName = user.getLastName();
            String user_username = user.getUserName();

            //for send message we creat SendMessage Obj
            SendMessage sendMessage = new SendMessage(chatId,String.format("You'r name = %s, last name = %s, user name = @%s,\n"
                        +"you'r message : \n%s"
                    ,name,lastName,user_username,messageText));

            //and for send each obj use of this piece of code
            try {
                sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
        //"you'r token blahblahblabla";
    }
}
