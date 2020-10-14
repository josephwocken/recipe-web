## recipe-web

### build
```
./gradlew clean build

> ls build/libs
Mode                LastWriteTime         Length Name
----                -------------         ------ ----
-a----       10/13/2020   9:33 PM       17516188 recipe-web-all.jar
-a----       10/13/2020   9:33 PM          34168 recipe-web.jar
```

### run
```
java -Dapp.config=</path/to/config> -jar recipe-web-all.jar
```

### future features
* ability to update an existing recipe
* form validation for javascript on create recipe
* favorited recipes
    * new table for user
    * new favorited recipe table tying user with recipe ids
    * user would provide username for lookup
* ordering recipes (new columns on recipe table)
    * by create time
    * by update time
    * by rank
* password requirement when creating recipe
    * password table with one entry
    * do simple string match with provided input
    * if fail, http 403
    * if success, create recipe, http 201
    * all in the web project
* add authentication header to backend
    * auth handler
    * static uuid probably
    * depends on front end external config (above)
