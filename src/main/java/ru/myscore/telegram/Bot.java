package ru.myscore.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.myscore.nodes.Match;
import ru.myscore.nodes.Team;

import java.util.HashSet;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class Bot extends AbilityBot {

    private final HashSet<Long> chats = new HashSet<Long>();
    private final Logger logger = LogManager.getLogger(AbilityBot.class);


    public Bot(String token, String username, DefaultBotOptions options) {
        // bot token, bot username, bot options
        super(token, username);
    }

    @Override
    public int creatorId() {
        return 484464478;
    }

    @SuppressWarnings("Kek")
    public Ability help() {


        return Ability
                .builder()
                .name("help")
                .info("Помощь")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    sendHelp(ctx.chatId());
                })
                .build();
    }

    public Ability regChat() {
        return Ability
                .builder()
                .name("reg")
                .info("Добавить чат")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    if (chats.add(ctx.chatId())) {
                        silent.send("Added", ctx.chatId());
                        logger.info(MYAPP_MARKER, "Registered " + ctx.chatId());
                    } else {
                        silent.send("Already added", ctx.chatId());
                    }
                })
                .build();

    }

    public void sendToCreator(String msg) {
        try {
            silent.send(msg, (long) getMe().getId());
        } catch (TelegramApiException e) {
            logger.error(MYAPP_MARKER, "Can not send msg '" + msg + "' to creator\n" + e.getMessage());
        }
    }

    public void send(Match node) {
        Team home = node.getHome();
        Team away = node.getAway();

        String msg = String.format("*%s : %s*\n```" +
                        " home %2d %2d %2d\n" +
                        " away %2d %2d %2d```",
                home.getParticipant(), away.getParticipant(),
                home.getFirstQuarter(), home.getSecondQuarter(), home.getThirdQuarter(),
                away.getFirstQuarter(), away.getSecondQuarter(), away.getThirdQuarter());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(msg);
        sendMessage.enableMarkdown(true);

        for (Long chat : chats) {
            sendMessage.setChatId(chat);

            try {
                execute(sendMessage);
                logger.info(MYAPP_MARKER, "Sended\n" + msg);
            } catch (Exception e) {
                logger.error(MYAPP_MARKER, "Can not send to telegram!", e);
            }
        }

    }

    private void sendHelp(long chatId) {
        StringBuilder msgText = new StringBuilder();
        msgText.append("Just a test\n");

        if (admins().contains((int)chatId)) {
            msgText.append("\n\n<b>Для админов:</b>\n");
            msgText.append("/promote @username \n");
            msgText.append("/demote @username \n");
            msgText.append("/ban @username \n");
            msgText.append("/unban @username \n");
        }


        SendMessage msg = new SendMessage()
                .setChatId(chatId)
                .setText(msgText.toString())
                .enableHtml(true);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            logger.error(MYAPP_MARKER, "Can not send help message to " + chatId, e);
        }
    }
}
