package dion.fyp.webapi.minecraft;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Modified version of the example StringAppender in Andrew Flower's blog post.
 * http://andrew-flower.com/blog/Create_Custom_Log4j_Plugins
 * 
 * @author andrew.flower
 */
public class ByteArrayAppender extends AbstractOutputStreamAppender {

    static final LoggerContext context = (LoggerContext) LogManager.getContext(false);
    static final Configuration configuration = context.getConfiguration();
    ByteArrayManager manager;

    private ByteArrayAppender(String name, Layout<? extends Serializable> layout, Filter filter, ByteArrayManager manager, boolean ignoreExceptions, boolean immediateFlush) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        this.manager = manager;
    }

    public static ByteArrayAppender createStringAppender() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PatternLayout layout = PatternLayout.createLayout("[%d{HH:mm:ss} %level]: %msg%n", configuration, null, null, null);

        return new ByteArrayAppender(
                "ByteArrayAppender",
                layout,
                null,
                new ByteArrayManager(outputStream, "ByteArrayStream", layout),
                false,
                true);
    }

    public void addToLogger(String loggerName, Level level) {
        LoggerConfig loggerConfig = configuration.getLoggerConfig(loggerName);
        loggerConfig.addAppender(this, level, null);
        context.updateLoggers();
    }

    public void removeFromLogger(String loggerName) {
        LoggerConfig loggerConfig = configuration.getLoggerConfig(loggerName);
        loggerConfig.removeAppender("ByteArrayAppender");
        context.updateLoggers();
    }

    public String getOutput() {
        manager.flush();
        return new String(manager.getStream().toByteArray());
    }

    /**
     * StringOutputStreamManager to manage an in memory bytestream representing
     * our stream
     */
    protected static class ByteArrayManager extends OutputStreamManager {

        ByteArrayOutputStream stream;

        protected ByteArrayManager(ByteArrayOutputStream os, String streamName, Layout<?> layout) {
            super(os, streamName, layout);
            stream = os;
        }

        public ByteArrayOutputStream getStream() {
            return stream;
        }
    }
}
