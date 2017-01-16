package wangyuwei.me.clicktracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private Button mBtnHook1, mBtnHook2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBtnHook1 = (Button) findViewById(R.id.btn_hook1);
    mBtnHook2 = (Button) findViewById(R.id.btn_hook2);
    hook(mBtnHook1, mBtnHook2);
  }

  public void onClick(View view) {
    Map map = new HashMap();
    switch (view.getId()) {
      case R.id.btn_hook1:
        map.put("巴", "掌");
        map.put("菜", "比");
        break;
      case R.id.btn_hook2:
        map.put("TF-Boys", "嘿嘿嘿");
        map.put("id", "111");
        break;
    }
    view.setTag(R.id.id_hook, map);
  }

  private void hook(View view) {
    try {
      Class clazzView = Class.forName("android.view.View");
      Method method = clazzView.getDeclaredMethod("getListenerInfo");
      method.setAccessible(true);
      Object listenerInfo = method.invoke(view);
      Class clazzInfo = Class.forName("android.view.View$ListenerInfo");
      Field field = clazzInfo.getDeclaredField("mOnClickListener");
      field.set(listenerInfo, new HookListener((View.OnClickListener) field.get(listenerInfo)));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  private void hook(View... views) {
    for (View view : views) {
      hook(view);
    }
  }

  public static class HookListener implements View.OnClickListener {

    private View.OnClickListener mOriginalListener;

    public HookListener(View.OnClickListener originalListener) {
      mOriginalListener = originalListener;
    }

    @Override public void onClick(View v) {
      if (mOriginalListener != null) {
        mOriginalListener.onClick(v);
      }
      StringBuilder sb = new StringBuilder();
      sb.append("hook succeed.\n");
      Object obj = v.getTag(R.id.id_hook);
      if (obj != null && obj instanceof HashMap && !((Map) obj).isEmpty()) {
        for (Map.Entry<String, String> entry : ((Map<String, String>) obj).entrySet()) {
          sb.append("key => ")
              .append(entry.getKey())
              .append(" ")
              .append("value => ")
              .append(entry.getValue())
              .append("\n");
        }
      } else {
        sb.append("params => null\n");
      }

      Toast.makeText(v.getContext(), sb.toString(), Toast.LENGTH_LONG).show();
    }
  }
}
