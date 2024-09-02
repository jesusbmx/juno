package juno;

import java.util.concurrent.Executor;

public class Platform implements Executor {
  private static final Platform PLATFORM = findPlatform();

  public static Platform get() {
    return PLATFORM;
  }

  private static Platform findPlatform() {
    if (isAndroid()) {
      return new Android();
    } else if (isJavaSwing()) {
      return new JavaSwing();
    }
    return new Platform();
  }

  private static boolean isAndroid() {
    try {
      // Verifica si la clase android.os.Build está presente
      Class<?> buildClass = Class.forName("android.os.Build");
      return buildClass.getField("VERSION.SDK_INT") != null;
    } catch (ClassNotFoundException | NoSuchFieldException e) {
      return false;
    }
  }

  private static boolean isJavaSwing() {
    try {
      // Verifica si la clase javax.swing.SwingUtilities está presente
      Class.forName("javax.swing.SwingUtilities");
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  @Override public void execute(Runnable command) {
    command.run();
  }

  public static class Android extends Platform {
    // Aquí puedes manejar la ejecución específica para Android si es necesario
    @Override public void execute(Runnable command) {
      // Handler Android
      new Thread(command).start();  // Simplificación para evitar Handler de Android
    }
  }

  public static class JavaSwing extends Platform {
    @Override public void execute(Runnable command) {
      // SwingUtilities.invokeLater en Swing
      new Thread(command).start();  // Simplificación para evitar dependencias de Swing
    }
  }
}
