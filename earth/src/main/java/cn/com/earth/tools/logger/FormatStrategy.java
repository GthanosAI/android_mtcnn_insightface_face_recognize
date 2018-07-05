package cn.com.earth.tools.logger;

public interface FormatStrategy {

  void log(int priority, String tag, String message);
}
