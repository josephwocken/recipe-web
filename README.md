## recipe-web

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
* make it obvious that recipe was created
    * redirect to list recipes page?
    * green banner saying created?

### infra
* ec2 instance for backend
* ec2 instance for frontend [DONE]
    * see recipe-app README.md for server setup instructions
* alb for frontend [DONE]
* TLS certificate [DONE]
* postgres db [DONE]
* domain [DONE]
    * created CNAME pointing to alb [DONE]