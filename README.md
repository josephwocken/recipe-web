## recipe-web

### how to build
```
./gradlew clean build

> ls build/libs
Mode                LastWriteTime         Length Name
----                -------------         ------ ----
-a----       10/13/2020   9:33 PM       17516188 recipe-web-all.jar
-a----       10/13/2020   9:33 PM          34168 recipe-web.jar
```

### how to run
```
java -Dapp.config=</path/to/config> -jar recipe-web-all.jar
```

### application configuration
```
dbUrl=jdbc:postgresql://localhost:5432/postgres
dbUsername=
dbPassword=
```

### future features
* ability to update an existing recipe
    * change password field to plain text so dashlane doesn't autofill
* size image and recipe contents to same widths
    * attempted, but no such luck so far
* bold, italics, and underline formatting in create recipe
* form validation for javascript on create recipe
* favorited recipes
    * new table for user
    * new favorited recipe table tying user with recipe ids
    * user would provide username for lookup
* ordering recipes (new columns on recipe table)
    * by create time
    * by update time
    * by rank
* add authentication header to backend
    * auth handler
    * static uuid probably
    * depends on front end external config (above)
* ability to upload more than 1 image
