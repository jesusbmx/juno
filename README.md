Juno
========
Herramientas de desarrollo para java y Android

Descargar [juno.jar](https://github.com/jesusbmx/juno/raw/master/dist/juno.jar)

### Strings
Validación para cadenas.
```java
String txt = null;
if (Strings.isEmpty(txt)) {
  System.out.println("txt is empty");
}
    
txt = Validate.ifNull(txt, "hola mundo");
System.out.printf("txt = '%s'\n", txt);

txt = Strings.abbreviate(txt, 7);
System.out.printf("txt = '%s'\n", txt);

String name = "jesus   ";
name = Strings.trim(name);
name = Strings.capitalize(name);
System.out.printf("name = '%s'\n", name);

System.out.println(Strings.subStr("[Hola mundo]", "[", "]"));
```

```markdown
> txt is empty
> txt = 'hola mundo'
> txt = 'hola...'
> name = 'Jesus'
> Hola mundo
```

### Numbers
Conversión de valores numéricos
```java
String number = "-892768237.50";
if (Numbers.isNumber(number)) {
  System.out.printf("'%f' is number\n", Convert.toDouble(number));
} else {
  System.out.printf("'%s' not is number\n", number);
}
   
String str = "10.80";
    
int i = Convert.toInt(str);
System.out.printf("i = '%s'\n", i);
    
float f = Convert.toFloat(str);
System.out.printf("f = '%f'\n", f);

double round = Numbers.roundAvoid(948.856099955012, 2);
System.out.println(round);
```

```markdown
> '-892768237.500000' is number
> i = '0'
> f = '10.800000'
> 948.86
```

### Validate
```java

String a = null;
String b = "b";
System.out.println(Validate.eq(a, b));
System.out.println(Validate.isNull(null));
System.out.println(Validate.isNotNull(null));
```

```markdown
> false
> true
> false
```

### Collections

```java
List<String> strList = Collect.listOf("1", "2", "3", "7", "9");
List<Integer> intList = Collect.map(strList, (String it) -> Convert.toInt(it) );

if (Collect.hasIndex(strList, 2)) {
  System.out.printf("list[2] = '%s'\n", strList.get(2));
}

if (Collect.isEmpty(strList)) {
  System.out.println("list is empty");
}

System.out.println(Collect.get(strList, 50, "defaultVal"));

System.out.println(Collect.join(strList));
System.out.println(Collect.join(strList, (String it) -> "\"" + it.toString() + "\"" ));

boolean some = Collect.some(strList, (String it) -> t.equals("7") );
System.out.println(some);

boolean every = Collect.every(intList, (Integer it) -> it % 2 == 0 );
System.out.println(every);

String find = Collect.find(strList, (String it) -> t.equals("9") );
System.out.println(find);
    
List<Integer> filter = Collect.filter(intList, (Integer it) -> it > 5 );
System.out.println(Collect.join(filter, ","));

List<String> fill = Collect.fill(strList, "z");
System.out.println(Collect.join(fill, ","));
```

```markdown
> list[2] = '3'
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


### Date
```java
String sDate = "2023-04-30 19:10:02";
        
Calendar date = Dates.parseCalendar(sDate, "yyyy-MM-dd");
System.out.println(Dates.dateTimeFormat(date)); // 2023-04-30 00:00:00

Date dateTime = Dates.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
System.out.println(Dates.dateTimeFormat(dateTime)); // 2023-04-30 19:10:02


System.out.println(Dates.dateFormat(new Date())); // 2023-05-03
System.out.println(Dates.dateTimeFormat(new Date())); // 2023-05-03 12:31:47
System.out.println(Dates.format("yyyy-MM-dd HH:mm:ss", new Date())); // 2023-05-03 12:31:47


Calendar cDate = Dates.calendarWithoutTime(); // get date without time
System.out.println(Dates.dateTimeFormat(cDate)); // 2023-05-03 00:00:00

Calendar cDateTime = Dates.calendarWithTime(); // get date and time
System.out.println(Dates.dateTimeFormat(cDateTime)); // 2023-05-03 12:31:47
```



### Async Call

```java
AsyncCall<String> saludar() {
  return new AsyncCall<>(() -> {
    //throw new Exception("error");
    return "Hola Mundo";
  });
}
```

Async
```java
saludar().execute((String result) -> {
  System.out.println(result);

}, (Exception error) -> {
  System.err.println(error);

});
```

Sync
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

call.execute((File result) -> {   
  Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_SHORT).show();

}, (Exception e) {
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
});
```

### EventManager

```java
EventManager receiver = EventManager.get("MyHandler");
receiver.on("log", (String value) -> {
    System.out.println(value);
});

EventManager sender = EventManager.get("MyHandler");
sender.send("log", "Hola mundo");
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

