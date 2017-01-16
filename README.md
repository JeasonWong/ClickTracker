##**ClickTracker**

easy to hook onClick event.

###**USAGE**

#### step1. Write the onClick event code as usual

```java
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
```
If you want to track some data about this click event, you can new a map and put some data in it, at last call setTag().

#### step2. customize HookListener

```java
public static class HookListener implements View.OnClickListener {

    private View.OnClickListener mOriginalListener;

    public HookListener(View.OnClickListener originalListener) {
      mOriginalListener = originalListener;
    }

    @Override public void onClick(View v) {
      mOriginalListener.onClick(v);
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
```
In this class, you can call getTag to get the map which you transmit ever before and track!

###**License**
```license
Copyright [2016] [JeasonWong of copyright owner]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```