package cn.com.earth.tools.logger;

import android.util.Log;

public class LogcatLogStrategy implements LogStrategy {

  @Override public void log(int priority, String tag, String message) {
    Log.println(priority, tag, message);
  }

}