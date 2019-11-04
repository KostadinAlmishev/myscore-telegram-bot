package ru.myscore.telegram;

import com.google.inject.internal.cglib.core.$CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.myscore.nodes.BasketballMatch;
import ru.myscore.nodes.BasketballQuarter;
import ru.myscore.nodes.BasketballTeam;
import ru.myscore.service.BasketballService;

import java.util.HashSet;
import java.util.List;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class Bot extends AbilityBot {

    private final HashSet<Long> chats = new HashSet<Long>();
    private final Logger logger = LogManager.getLogger(AbilityBot.class);
    private BasketballService basketballService = null;


    public Bot(String token, String username, DefaultBotOptions options, BasketballService basketballService) {
        // bot token, bot username, bot options
        super(token, username, options);
        this.basketballService = basketballService;
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

    public Ability unregChat() {
        return Ability
                .builder()
                .name("unreg")
                .info("Удалить чат")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    if (chats.remove(ctx.chatId())) {
                        silent.send("Removed", ctx.chatId());
                        logger.info(MYAPP_MARKER, "Removed " + ctx.chatId());
                    } else {
                        silent.send("You are not in registered ", ctx.chatId());
                    }
                })
                .build();
    }

    public Ability getAllParsed() {
        return Ability
                .builder()
                .name("getallparsed")
                .info("Спарсить все")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    List<BasketballMatch> matches = basketballService.getWonFirstLostSecondAndThird();

                    if (matches.isEmpty()) {
                        silent.send("Can not find", ctx.chatId());
                    } else {
                        for (BasketballMatch match : matches) {
                            silent.sendMd(formatMatch(match), ctx.chatId());
                        }
                    }
                })
                .build();
    }

    public Ability reload() {
        return Ability
                .builder()
                .name("reload")
                .info("перезагрузить страницу")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    basketballService.reload();
                    silent.send("Reloaded", ctx.chatId());
                })
                .build();
    }

    public void sendToCreator(String msg) {
        silent.send(msg, creatorId());
    }

    public void send(BasketballMatch node) {
        String msg = formatMatch(node);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(msg);
        sendMessage.enableMarkdown(true);

        for (Long chat : chats) {
            sendMessage.setChatId(chat);

            try {
                execute(sendMessage);
                logger.info(MYAPP_MARKER, "Sended\n" + msg);
            } catch (Exception e) {
                logger.error(MYAPP_MARKER, "Can not send to telegram! chat: " + chat);
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

    private String formatMatch(BasketballMatch match) {
        BasketballTeam home = match.getHome();
        BasketballTeam away = match.getAway();


        return String.format("*%s : %s*\n```" +
                        " home %2d %2d %2d %2d %2d\n" +
                        " away %2d %2d %2d %2d %2d\n" +
                        "%20s\n" +
                        "%16d min```",
                home.getParticipant(), away.getParticipant(),

                home.getFirstQuarter(), home.getSecondQuarter(), home.getThirdQuarter(),
                home.getFourthQuarter(), home.getFifthQuarter(),

                away.getFirstQuarter(), away.getSecondQuarter(), away.getThirdQuarter(),
                away.getFourthQuarter(), away.getFifthQuarter(),

                match.getQuarter(), match.getCurrMinute());
    }
}
