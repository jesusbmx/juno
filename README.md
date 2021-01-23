Juno
========
Herramientas de desarrollo para java y Android

### Strings
Validación para cadenas.
```java
String txt = null;
if (Util.isEmpty(txt)) {
  System.out.println("txt is empty");
}
    
txt = Util.ifNull(txt, "hola mundo");
System.out.printf("txt = '%s'\n", txt);

txt = Texts.abbreviate(txt, 7);
System.out.printf("txt = '%s'\n", txt);

String name = "jesus   ";
name = Util.trim(name);
name = Texts.capitalize(name);
System.out.printf("name = '%s'\n", name);
```

```markdown
> txt is empty
> txt = 'hola mundo'
> txt = 'hola...'
> name = 'Jesus'
```

### Numbers
Conversión de valores numéricos
```java
String number = "-892768237.50";
if (Util.isNumber(number)) {
  System.out.printf("'%f' is number\n", Convert.toDouble(number));
} else {
  System.out.printf("'%s' not is number\n", number);
}
   
String str = "10.80";
    
int i = Convert.toInt(str);
System.out.printf("i = '%s'\n", i);
    
float f = Convert.toFloat(str);
System.out.printf("f = '%f'\n", f);
```

```markdown
> '-892768237.500000' is number
> i = '0'
> f = '10.800000'
```

### Arrays
Validación para arreglos.
```java
String[] array = {"a", "b", "c"};
if (Collect.arrayHasIndex(array, 2)) {
  System.out.printf("array[2] = '%s'\n", array[2]);
}
    
if (Collect.isEmpty(array)) {
  System.out.println("array is empty");
}

System.out.println(Collect.joinToStr(array));

System.out.println(Collect.joinToStr(array, new Fun<Object, String>() {
  @Override public String apply(Object t) {
    return "\"" + t.toString() + "\"";
  }
}));
```

```markdown
> array[2] = 'c'
> a, b, c
> "a", "b", "c"
```

### IO
FileInputStream a bytes.
```java
FileInputStream in = null;
try {
  in = new FileInputStream("/etc/hola.txt");
  byte[] bytes = IOUtils.toByteArray(in);

} finally {
  IOUtils.closeQuietly(in);
}
```

Copiar
```java
FileInputStream in = null;
FileOutputStream out = null;
try {
  in = new FileInputStream("/etc/hola.txt");
  out = new FileOutputStream("/etc/hola-copy.txt");
  IOUtils.copy(in, out);

} finally {
  IOUtils.closeQuietly(in);
  IOUtils.closeQuietly(out);
}
```

Copiar
```java
File in = new File("/etc/hola.txt");
FileOutputStream out = null;
try {
  out = new FileOutputStream("/etc/hola-copy.txt");
  IOUtils.copy(in, out);

} finally {
  IOUtils.closeQuietly(out);
}
```

### Formats
```java
    System.out.println(Formats.date());
    System.out.println(Formats.datetime());
    
    System.out.println(Formats.date("yyyy"));
    System.out.println(Formats.date("yyyy-MM", new Date()));
```

### Concurrent
```java
class CopyAsyncTask extends AsyncCall<File> {
  File in = new File("/etc/hola.txt");

  @Override public Void doInBackground() throws Exception {
    FileOutputStream out = null;
    try {
      File copy = new File("/etc/hola-copy.txt");
      out = new FileOutputStream(copy);
      IOUtils.copy(in, out);
      return copy;

    } finally {
      IOUtils.closeQuietly(out);
    }
  }
}
```

```java
Call<File> call = new CopyAsyncTask();

call.execute(new Callback<File>() {
  @Override 
  public void onResponse(File result) {   
    Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_SHORT).show();
  }    
  @Override 
  public void onFailure(Exception e) {
    new AlertDialog.Builder(ActivityMain.this)
        .setTitle("Error")
        .setMessage(e.getMessage())
        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            ....
          }
        })
        .create()
        .show();
    }
  });
```

License
=======

    Copyright 2018 JesusBetaX, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

