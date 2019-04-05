# Telegram bot tutorial
1. we need to sign up a bot in [Bot Father](https://t.me/botfather)
2. next step is to download API from [This page](https://github.com/rubenlagus/TelegramBots)
3. then we need to code :)

    +   each of you'r bots have to extend from Telegram Long Polling Bot
    +   each bot has `Username` and `Token` from step=>`1.`
    +   Telegram Long Polling bot is an abstract class that we hae to override 3 methods
        -   `void` onUpdateReceived(Update update)
        -   `String` getBotToken()**`return Username(without '@') of the bot`**
        -   `String` getBotUsername() **`return token of the bot`**
    +   Update object has important stuff such as **message,user,....**
    +   every chat in telegram has an id => that called chatId
    +   so for send message to a chat(PV or GP) you need the chat id
    +   for send every thing to telegram should use `sendApiMethod(Method method)`
    
### a simple robot that only returning your message is provided in below
```java
    public class Mirror_bot extends TelegramLongPollingBot{
        private String token;
        private String username;
        
        Mirror_bot(String Token,String Username){
            token = Token;
            username = Username;
        }
        
        @Override
        void onUpdateReceived(Update update){
            Message message = update.getMessage();
            String messageText = message.getText();
            long chatId = message.getChatId();
            
            SendMessage sendMessage = new SendMessage(chatId, messageText);
            try {
                sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        
        @Override
        String getToken(){
            return token;
            //"bot-token";
        }
        
        @Override
        String getBotUsername(){
            return username;
            // "bot-username-without @";
        }
    }
```

### To activate our robot we write the following code
```java
    public class Main{
        public static void main(String[] args){
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                Mirror_bot myBot = new Mirror_bot("198400972:AAGzI------------","-------");
                telegramBotsApi.registerBot(myBot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
```