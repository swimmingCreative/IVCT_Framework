package nato.ivct.commander;

import javax.jms.Message;
import javax.jms.MessageProducer;

import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class CmdSendLogMsg implements Command {

    public static final String LOG_MSG_TOPIC    = "LogEvent";
    public static final String LOG_MSG_LEVEL    = "level";
    public static final String LOG_MSG_TESTCASE = "testcase";
    public static final String LOG_MSG_SUT      = "sut";
    public static final String LOG_MSG_BADGE    = "badge";
    public static final String LOG_MSG_EVENT    = "event";
    public static final String LOG_MSG_TIME     = "time";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CmdSendLogMsg.class);
    private MessageProducer logProducer;
    private ILoggingEvent logMessage;

    public CmdSendLogMsg() {
        logProducer = Factory.createTopicProducer(LOG_MSG_TOPIC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute() throws Exception {
        JSONObject startCmd = new JSONObject();
        String loggerName = logMessage.getLoggerName();
        LoggerData loggerData = TcLoggerData.getLoggerData(loggerName);
        if (loggerData == null) {
        	return;
        }
        Level levelUser = TcLoggerData.getLogLevel();
        Level levelMsg = logMessage.getLevel();
        if (levelMsg.isGreaterOrEqual(levelUser)) {
        String tc = loggerData.tcName;
        String sut = loggerData.sutName;
        String badge = loggerData.badgeName;
        String level = logMessage.getLevel().toString();
        long ts = logMessage.getTimeStamp();
        startCmd.put(LOG_MSG_LEVEL, level);
        startCmd.put(LOG_MSG_TESTCASE, tc);
        startCmd.put(LOG_MSG_SUT, sut);
        startCmd.put(LOG_MSG_BADGE, badge);
        startCmd.put(LOG_MSG_TIME, ts);
        startCmd.put(LOG_MSG_EVENT, logMessage.getFormattedMessage());

        Message message = Factory.jmsHelper.createTextMessage(startCmd.toString());
        logProducer.send(message);
        }
    }

    public void send(ILoggingEvent newMsg) {
        logMessage = newMsg;
        try {
            execute();
        } catch (Exception ex) {
            LOGGER.error("could not send command: " + newMsg);
        }
    }
}
