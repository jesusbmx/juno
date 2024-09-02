package juno;

import java.util.concurrent.Executor;

public class Platform implements Executor {
  private static final Platform PLATFORM = findPlatform();

  public static Platform get() {
    return PLATFORM;
  }

  private static Platform findPlatform() {
    try {
      Class.forName("android.os.Build");
      if (android.os.Build.VERSION.SDK_INT != 0) {
        return new Android();
      }
    } catch (Exception ignored) {
    }
    try {
      Class.forName("javax.swing.SwingUtilities");
      return new JavaSwing();
    } catch (Exception ignored) {
    }
    return new Platform();
  }
  
  @Override public void execute(Runnable command) {
    command.run();
  }
  
  public static class Android extends Platform {
    final android.os.Handler mHandler = new android.os.Handler(
            android.os.Looper.getMainLooper());
    @Override public void execute(Runnable command) {
      mHandler.post(command);
    }
  }
  
  public static class JavaSwing extends Platform {
    @Override public void execute(Runnable command) {
      javax.swing.SwingUtilities.invokeLater(command);
    }
  }
  
//    public static void main(String[] args) {
//        System.out.println(Platform.get().getClass().getCanonicalName());
//    }
}
