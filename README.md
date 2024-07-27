Juno
========
Development tools for Java and Android.

Download [juno.jar](https://github.com/jesusbmx/juno/raw/master/dist/juno.jar)

## Documentation

### Convert
```java
int i = Convert.toInt("1", -1); // 1
float f = Convert.toFloat("1.1"); // 1.1f
double d = Convert.toDouble("2.2"); // 2.2d
double l = Convert.toLong("1000"); // 1000L
String str = Convert.toString(f, ""); // "1.1"
boolean bool = Convert.toBool("true"); // true
```

### Base64
Encoding and decoding in Base64:
```java
String encodeBase64 = Convert.toBase64("Hello world");
System.out.println(encodeBase64); // SGVsbG8gd29ybGQ=
```

```java
String decodeBase64 = Convert.fromBase64(encodeBase64);
System.out.println(decodeBase64); // Hello world
```

### Strings
Validate if a string is empty:
```java
String txt = null;
if (Strings.isEmpty(txt)) {
  System.out.println("txt is empty");
}
```
    
Assign default value if null:
```java
txt = Validate.ifNull(txt, "Hello world");
System.out.printf("txt = '%s'\n", txt); // txt = 'hello world'
```

Abbreviate a string:
```java
txt = Strings.abbreviate(txt, 7);
System.out.printf("txt = '%s'\n", txt); // txt = 'hello...'
```

Trim and capitalize a string:
```java
String name = "jesus   ";
name = Strings.trim(name);
name = Strings.capitalize(name);
System.out.printf("name = '%s'\n", name);  // name = 'John'
```

Get a substring between two characters:
```java
System.out.println(Strings.subStr("[Hello world]", "[", "]")); // Hello world
```

### Numbers
Validate if a value is numeric and convert it:
```java
String number = "-892768237.50";
if (Numbers.isNumber(number)) {
  System.out.printf("'%f' is number\n", Convert.toDouble(number)); // '-892768237.500000' is number
} else {
  System.out.printf("'%s' not is number\n", number);
}
```

Convert numeric strings to integers and floats:
```java   
String str = "10.80";

int i = Convert.toInt(str);
System.out.printf("i = '%d'\n", i); // i = '10'

float f = Convert.toFloat(str);
System.out.printf("f = '%f'\n", f); // f = '10.800000'
```

Round a number to a specified number of decimal places:
```java
double round = Numbers.roundAvoid(948.856099955012, 2);
System.out.println(round); // 948.86
```


### Lists, Arrays
```java
List<String> strList = Lists.listOf("1", "2", "3", "7", "9");
List<Integer> intList = Lists.map(strList, (String it) -> Convert.toInt(it) );

if (Lists.hasIndex(strList, 2)) {
    System.out.printf("list[2] = '%s'\n", strList.get(2)); // list[2] = '3'
}

if (Lists.isEmpty(strList)) {
    System.out.println("list is empty");
}

System.out.println(Lists.getValueOrDefault(strList, 50, "defaultVal")); // defaultVal

System.out.println(Strings.join(strList)); // 1,2,3,7,9
System.out.println(Strings.join(strList, (String it) -> "\"" + it.toString() + "\"" )); // "1","2","3","7","9"

boolean some = Lists.some(strList, (String it) -> it.equals("7") );
System.out.println(some); // true

boolean every = Lists.every(intList, (Integer it) -> it % 2 == 0 );
System.out.println(every); // false

String find = Lists.find(strList, (String it) -> it.equals("9") );
System.out.println(find); // 9

List<Integer> filter = Lists.filter(intList, (Integer it) -> it > 5 );
System.out.println(Strings.join(filter, ",")); // 7,9

List<String> fill = Lists.fill(strList, "z");
System.out.println(Strings.join(fill, ",")); // z,z,z,z,z
```


### Maps
Create and manipulate maps:
```java
final Map<String, Integer> map = Maps.of(
    "one", 1, 
    "two", 2, 
    "three", 3
);

System.out.println(map); // {one=1, two=2, three=3}
System.out.println(Maps.getValueOrDefault(map, "one", -1)); // 1

Map<Integer, String> convertedMap2 = Maps.convert(
    map,
    entry -> entry.getValue(),
    entry -> entry.getKey() + "-" + entry.getValue()
);
System.out.println(convertedMap2); // {1=one-1, 2=two-2, 3=three-3}
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

// ISO_8601_24H_FULL_FORMAT
Date date_iso_8601 = Dates.parseDate("2023-06-20T19:18:11.000Z", 
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

System.out.println(Dates.dateTimeFormat(date_iso_8601)); // 2023-06-20 13:18:11
```


### Async Await
```java
Async<String> read(final File file) {
  return new AsyncCallable<>(() -> {
    //throw new Exception("error");
    return Files.readString(file);
  });
}
```

Run tasks asynchronously
```java
File file = new File("/home/user/a.txt");

read(file).execute((String result) -> {
  System.out.println(result);

}, (Exception error) -> {
  System.err.println(error);

});
```

Run tasks synchronously
```java
try {
  File file = new File("/home/user/a.txt");

  String result = read(file).await();
  System.out.println(result);

} catch(Exception error) {
  System.err.println(error);
}
```

### Async Sender
```java
Async<String> read(final File file) {
  return new AsyncSender<>((sender) -> {
    //sender.reject(throw new Exception("error"));
    String result = Files.readString(file);
    sender.resolve(result);
  });
}
```

### Abstract Async
```java
Async<String> read(final File file) {
  return new AbstractAsync<String>() {
    @Override
    public String call() throws Exception {
      //throw new Exception("error");
      return Files.readString(file);
    }
 };
}
```


### EventManager
```java
EventManager receiver = EventManager.get("MyHandler");
receiver.on("log", (EventMessage<String> evt) -> {
    System.out.println(evt.getValue());
});

EventManager sender = EventManager.get("MyHandler");
sender.send("log", "Hola mundo");
```

```java
EventManager receiver = EventManager.get("MyHandler");
receiver.once("status", (EventMessage<Integer> evt) -> {
    System.out.println(evt.getValue());
});

EventManager sender = EventManager.get("MyHandler");
sender.send("status", 200);
```

### Paths
```java
String basePath = "/home";

System.out.println(Paths.join('/', Arrays.of(
    basePath, "User/Desktop", "file.txt"
))); // /home/User/Desktop/file.txt

basePath = "/home/User/Documents/";

System.out.println(Paths.join('/', Arrays.of(
    basePath, "file.txt"
))); // /home/User/Documents/file.txt

basePath = "/home/User";

System.out.println(Paths.join('/', Arrays.of(
    basePath, "Downloads", "file.txt"
))); // /home/User/Downloads/file.txt

System.out.println(Paths.join('/', Arrays.of(
    "/home", "User2", "Downloads", "file.txt"
))); // /home/User2/Downloads/file.txt

System.out.println(Paths.join('/', Arrays.of(
    "/home", "User2", "Downloads"
))); // /home/User2/Downloads
```

C:\User\Desktop\file.txt

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

Copy
```java
Files.copy("/etc/hola.txt", "/etc/hola-copy.txt");
```

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

