package cn.com.earth.tools.logger;

public interface LogStrategy {

  void log(int priority, String tag, String message);
}
