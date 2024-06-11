/**
 * 
 */
package com.phorm.qa.search_engines;

import java.io.File;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author artem_ryabov
 *
 */
public class HtmlFormatterSavinTest extends XMLFormatter {

    private static final String LINE_SEP = "\n";
    private String title;

    public HtmlFormatterSavinTest(String title) {
	super();
	this.title = title;
    }

    @Override
    public String format(LogRecord record) {
	
	if (record.getThrown() != null) {
	    throw new IllegalArgumentException("Argument shouldn't have getCause not null => use TestError class instance as LogRecord parameter to bring Throwable in this log!", record.getThrown());
	}
	
	StringBuilder sb = new StringBuilder();
	sb.append(LINE_SEP).append("<tr class=\"").append(record.getLevel()).append("\">").append(LINE_SEP);

        sb.append("<td>");
        sb.append(LoggerUtil.SIMPLE_TIME_FORMAT.format(new Date(record.getMillis())));
        sb.append("</td>").append(LINE_SEP);

//        String truncedLoggerName = ReportHelper.truncClassName(event.getLoggerName());
        String location = StringEscapeUtils.escapeHtml(record.getSourceMethodName()); // event.getLocationInformation().getLineNumber() + "(" + event.getLocationInformation().getMethodName() + ")";
        sb.append("<td title=\"").append(location).append("\" class=\"Level\">");
        sb.append(StringEscapeUtils.escapeHtml(String.valueOf(record.getLevel())));
        sb.append("</td>").append(LINE_SEP);

        sb.append("<td>").append(LINE_SEP);

        
        if (record.getParameters() != null){
            appendErrorInfo (sb, record);
//        if (event.getMessage() instanceof TestError) {
//            appendErrorInfo(sb, (TestError) event.getMessage());
//        } else if (event.getMessage() instanceof Artifact) {
//            appendArtifact(sb, (Artifact) event.getMessage());
//        } else if (event.getMessage() instanceof ScreenShot) {
//            appendScreenShot(sb, (ScreenShot) event.getMessage());
//        } else if (event.getMessage() instanceof HtmlSource) {
//            appendHtmlSource(sb, (HtmlSource) event.getMessage());
//        } else if (event.getMessage() instanceof Throwable) {
//            appendException(sb, (Throwable) event.getMessage()); TODO
//        } else if (event.getThrowableInformation() != null) {
//            appendException(sb, event.getMessage().toString(), event.getThrowableInformation().getThrowable());
        } else {
            sb.append(formatMessage(record.getMessage())); //  event.getRenderedMessage()));
        }
        sb.append("</td>").append(LINE_SEP);
        sb.append("</tr>").append(LINE_SEP);

//        if (record.  event.getNDC() != null) {
//            sb.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : xx-small;\" colspan=\"7\" title=\"Nested Diagnostic Context\">");
//            sb.append("NDC: ").append(StringEscapeUtils.escapeHtml(event.getNDC()));
//            sb.append("</td></tr>").append(LINE_SEP);
//        }

        return sb.toString();
    }

    private String formatMessage(String message) {
	return StringEscapeUtils.escapeHtml(message).replace("\n", " <BR/>\n");
    }

    private void appendErrorInfo(StringBuilder sb, LogRecord record) {
	
        sb.append("<table class=\"errorDetails\">").append(LINE_SEP);

        if (record.getMessage() != null) {
            sb.append("<tr>").append(LINE_SEP);
            sb.append("<td colspan=\"8\"><span>Message: </span>").append(formatMessage((record.getMessage()))).append("</td>").append(LINE_SEP);
            sb.append("</tr>").append(LINE_SEP);
        }

        TestError testError;
        if (record.getParameters() ==null ) {
            testError = null;
        } else if (record.getParameters().length > 1) {
            throw new IllegalStateException("LogRecord has " + record.getParameters().length + " parameters, expected - not more than 1!");
        } else if (record.getParameters()[0] instanceof TestError){
            testError = (TestError) record.getParameters()[0];
        } else {
            throw new IllegalStateException("LogRecord parameter is not instance of TestError: " + record.getParameters()[0].getClass());
        }
        
        sb.append("<tr>").append(LINE_SEP);
        sb.append("<td class=\"screenshot link\"><img src=\"../../velocity_report/pics/screenshot.png\" title=\"Screenshot\"").append(LINE_SEP);
        final File screenshot = testError.getScreenshot();
        if (screenshot != null) {
            //todo: Konstantin Savin: update hardcoded dir to ReportDirManager method
            sb.append("alt=\"").append(screenshot.getName()).append("\"");
        } else {
            sb.append("alt=\"screenshot\" class=\"faded\"");
        }
        sb.append("/></td>").append(LINE_SEP);

        sb.append("<td class=\"htmlsource link\"><img src=\"../../velocity_report/pics/html.png\" title=\"HtmlSource\"").append(LINE_SEP);
        final File htmlSource = testError.getHtmlSource();
        if (htmlSource != null) {
            //todo: Konstantin Savin: update hardcoded dir to ReportDirManager method
            sb.append("alt=\"")/*.append("../htmlSources/")*/.append(htmlSource.getName()).append("\"");
        } else {
            sb.append("alt=\"htmlsource\" class=\"faded\"");
        }
        sb.append("/></td>").append(LINE_SEP);

        sb.append("<td class=\"diff link\"><img alt=\"diff\" ").append((testError.getDiff() == null) ? "class=\"faded\" " : "")
                .append("src=\"../../velocity_report/pics/diff.png\" title=\"DIFF\"/></td>").append(LINE_SEP);
        sb.append("<td class=\"cookies link\"><img alt=\"cookies\" ").append((testError.getCookies() == null) ? "class=\"faded\" " : "")
                .append("src=\"../../velocity_report/pics/cookies.png\" title=\"Cookies\"/></td>").append(LINE_SEP);
        sb.append("<td class=\"stacktrace link\"><img alt=\"stacktrace\" ").append((testError.getCause() == null) ? "class=\"faded\" " : "")
                .append("src=\"../../velocity_report/pics/stacktrace.png\" title=\"StackTrace\"/></td>").append(LINE_SEP);
        sb.append("<td class=\"errortype\"><span>ErrorType: </span>").append(testError.getErrorType()).append("</td>").append(LINE_SEP);

        final String bug = testError.getRelatedBug();
        String bugId = (bug != null ? "https://jira.corp.phorm.com/browse/" + bug : "NONE");
        sb.append("<td class=\"bugid\"><span>Bug ID: </span>").append(bugId).append("</td>").append(LINE_SEP);

        String url = testError.getUrl() != null ? "<a href=\"" + testError.getUrl() + "\">" + testError.getUrl() + "</a>" : "undefined";
        sb.append("<td><span>URL: </span>").append(url).append("</td>").append(LINE_SEP);

        sb.append("</tr>").append(LINE_SEP);

        if (testError.getDiff() != null) {
            sb.append("<tr class=\"diff\"><td colspan=\"8\"><span>DIFF: </span>").append(testError.getDiff()).append("</td></tr>").append(LINE_SEP);
        }
        if (testError.getCookies() != null) {
            sb.append("<tr class=\"cookies\"><td colspan=\"8\"><span>Cookies: </span>").append(StringEscapeUtils.escapeHtml(testError.getCookies())).append("</td></tr>").append(LINE_SEP);
        }
        if (testError.getCause() != null) {
            sb.append("<tr class=\"stacktrace\"><td colspan=\"8\"><span>StackTrace: </span>");
            sb.append("<div>");
            appendStackTrace(sb, testError.getCause());
            sb.append("</div>");
            sb.append("</td></tr>").append(LINE_SEP);
        }

        sb.append("</table>").append(LINE_SEP);
	
	// TODO Auto-generated method stub
	
    }

    private void appendStackTrace(StringBuilder sb, Throwable exception) {
        sb.append(StringEscapeUtils.escapeHtml(TestError.convertStackTraceToString(exception)).replaceAll(System.getProperty("line.separator"), "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));

        Throwable cause = exception.getCause();
        while (cause != null) {
            sb.append("<span>&nbsp;&nbsp;cause: </span>").append(StringEscapeUtils.escapeHtml(TestError.convertStackTraceToString(cause)).replaceAll(System.getProperty("line.separator"), "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));
            cause = cause.getCause();
        }
    }

    @Override
    public String getHead(Handler h) {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>").append(LINE_SEP);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">").append(LINE_SEP);
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">").append(LINE_SEP);
        sb.append("<head>").append(LINE_SEP);
        sb.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>").append(LINE_SEP);
        sb.append("    <meta name=\"description\" content=\"").append(getTitle()).append("\"/>").append(LINE_SEP);
        sb.append("    <title>").append(getTitle()).append("</title>").append(LINE_SEP);
        sb.append("    <link href=\"../../velocity_report/css/main.css\" rel=\"stylesheet\" type=\"text/css\"/>").append(LINE_SEP);
        sb.append("    <script src=\"../../velocity_report/js/jquery-1.3.1.min.js\" type=\"text/javascript\"></script>").append(LINE_SEP);
        sb.append("    <script src=\"../../velocity_report/js/jquery.cookie.js\" type=\"text/javascript\"></script>").append(LINE_SEP);
        sb.append("    <script src=\"../../velocity_report/js/script.js\" type=\"text/javascript\"></script>").append(LINE_SEP);
        sb.append("</head>").append(LINE_SEP);
        sb.append("<body class=\"log\">").append(LINE_SEP);

        sb.append("<div class=\"link\" onclick=\"historyBack();\"> &lt;&lt; back to the previous page </div>");

        sb.append("<fieldset id=\"logLevel\">").append(LINE_SEP);
        sb.append("<legend>Log level</legend>").append(LINE_SEP);

        String[] logLevels = new String[]{"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"};
        for (String logLevel : logLevels) {
            sb.append("<label").append(" class=\"").append(logLevel).append("\"><input type=\"checkbox\" id=\"")
                    .append(logLevel).append("\" checked=\"checked\"/>").append(logLevel).append("</label>").append(LINE_SEP);
        }

        sb.append("</fieldset>").append(LINE_SEP);
        sb.append("<hr/>").append(LINE_SEP);
        sb.append("<div class=\"details\">Log session start time: ").append(new Date()).append("</div>").append(LINE_SEP);
        sb.append("<div class=\"header\" style=\"clear: both\">").append(LINE_SEP);
        sb.append("   <div class=\"testCaseName\">").append(LINE_SEP);
        sb.append("       <span>TestCase: </span><div class=\"value\"></div>").append(LINE_SEP);
        sb.append("   </div>").append(LINE_SEP);
        sb.append("   <div class=\"parameters\">").append(LINE_SEP);
        sb.append("       <span>Parameters: </span><div class=\"value\"></div>").append(LINE_SEP);
        sb.append("   </div>").append(LINE_SEP);
        sb.append("   <div class=\"currentPath\">").append(LINE_SEP);
        sb.append("       <script type=\"text/javascript\">").append(LINE_SEP);
        sb.append("           document.write('<span>PermLink: </span><div class=\"value\">' + document.location + '</div>');").append(LINE_SEP);
        sb.append("       </script>").append(LINE_SEP);
        sb.append("   </div>").append(LINE_SEP);
        sb.append("</div>").append(LINE_SEP);
        sb.append("<table cellspacing=\"0\" border=\"1\" class=\"colorize\" id=\"logTable\">").append(LINE_SEP);
        sb.append("<tr>").append(LINE_SEP);
        sb.append("<th>Time</th>").append(LINE_SEP);
        sb.append("<th>Level</th>").append(LINE_SEP);
//        if (getLocationInfo()) {
//            sb.append("<th>File:Line</th>").append(LINE_SEP);
//        }
        sb.append("<th>Message</th>").append(LINE_SEP);
        sb.append("</tr>").append(LINE_SEP);
        return sb.toString();
    }

    private String getTitle() {
	return title;
    }

    @Override
    public String getTail(Handler h) {
        StringBuilder sb = new StringBuilder();
        sb.append("</table>").append(LINE_SEP);
//        sb.append("<script type=\"text/javascript\">").append(LINE_SEP);
//        sb.append("$().ready(function() {").append(LINE_SEP);
//        sb.append("  $('.testCaseName div.value').text('").append(getLogTitle()).append("');").append(LINE_SEP);
//
//        String paramHtml = ReportHelper.testParamsToHtmlString(LogHelper.getParams());
//        if (!paramHtml.isEmpty()) {
//            sb.append("  $('.parameters div.value').html('").append(StringEscapeUtils.escapeJavaScript(paramHtml)).append("');").append(LINE_SEP);
//            sb.append("  $('.parameters').show();");
//        }
//
//        sb.append("});");
//        sb.append("</script>").append(LINE_SEP);
        sb.append("</body></html>");
        return sb.toString();
    }


}
