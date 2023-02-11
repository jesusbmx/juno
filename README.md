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

### Util
```java
double round = Util.roundAvoid(948.856099955012, 2);
System.out.println(round);

String a = null;
String b = "b";
System.out.println(Util.eq(a, b));
```

```markdown
> 948.86
> false
```

### Arrays
Validación para arreglos.
```java
String[] strArray = {"1", "2", "3", "7", "9"};
Integer[] intArray = Collect.map(strArray, Integer.class, 
    (String it) -> Integer.parseInt(it) );

if (Collect.hasIndex(strArray, 2)) {
  System.out.printf("strArray[2] = '%s'\n", strArray[2]);
}

if (Collect.isEmpty(strArray)) {
  System.out.println("array is empty");
}

System.out.println(Collect.get(strArray, 50, "defaultVal"));

System.out.println(Collect.join(strArray));
System.out.println(Collect.join(strArray, (String it) -> "\"" + it.toString() + "\"" ));

boolean some = Collect.some(strArray, (String it) -> t.equals("7") );
System.out.println(some);

boolean every = Collect.every(intArray, (Integer it) -> it % 2 == 0 );
System.out.println(every);

String find = Collect.find(strArray, (String it) -> t.equals("9") );
System.out.println(find);
    
Integer[] filter = Collect.filter(intArray, (Integer it) -> it > 5 );
System.out.println(Collect.join(filter, ","));

String[] fill = Collect.fill(strArray, "z");
System.out.println(Collect.join(fill, ","));
```

```markdown
> array[2] = '3'
> defaultVal
> 1,2,3,7,9
> "1", "2", "3", "7", "9"
> true
> false
> 9
> 7,9
> z,z,z,z,z
```


### Base64
```java
String encodeBase64 = Convert.toBase64("Hola mundo");
System.out.println(encodeBase64);

String decodeBase64 = Convert.fromBase64(encodeBase64);
System.out.println(decodeBase64);
```

```markdown
> SG9sYSBtdW5kbw==
> Hola mundo
```

### IO
```java
String path = "/etc/hola.txt";
File f = new File(path);

Files.write(f, "Hola mundo\n", /*append*/true, "UTF-8");

String str = Files.readString(f);
System.out.println(str);

System.out.printf("parent: %s\n", Files.getParent(path));
System.out.printf("name: %s\n", Files.getName(path));
System.out.printf("extension: %s\n", Files.getExtension(path));
System.out.printf("base name: %s\n", Files.getBaseName(path));
```

```markdown
> Hola mundo
>
> parent: /etc/
> name: hola.txt
> extension: txt
> base name: hola
```

Read bytes.
```java
byte[] bytes = Files.readByteArray(new File("/etc/hola.txt"));
```

Copiar
```java
Files.copy("/etc/hola.txt", "/etc/hola-copy.txt");
```

Copiar
```java
FileInputStream in = null;
FileOutputStream out = null;
try {
  in = new FileInputStream("/etc/hola.txt");
  out = new FileOutputStream("/etc/hola-copy.txt");
  Files.copy(in, out);

} finally {
  Files.closeQuietly(in);
  Files.closeQuietly(out);
}
```

### Formats
```java
  System.out.println(Formats.date());
  System.out.println(Formats.datetime());
    
  System.out.println(Formats.date("yyyy"));
  System.out.println(Formats.date("yyyy-MM", new Date()));
```

```markdown
> 2021-01-23
> 2021-01-23 11:29:58
> 2021
> 2021-01
```


### Promise

```java
Promise<String> saludar() {
  return new Promise<>((Sender<String> sender) -> {
    //sender.reject(new Exception("error"));
    sender.resolve("Hola Mundo");
  });
}
```

```java
saludar().then((String result) -> {
  System.out.println(result);

}).error((Exception error) -> {
  System.err.println(error);

}).enqueue();
```

```java
try {
  String str = saludar().await();
  System.out.println(str);

} catch(Exception error) {
  System.err.println(error);
}
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

```java
Dispatcher dispatcher = Dispatcher.get();
dispatcher.setExecutorService(/*no Threads*/4)
```

License
=======

    Copyright 2018 juno, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

