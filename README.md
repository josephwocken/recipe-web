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
* externalize config for front end
    * backend host
* how to point front end react app to url for backend?
    * right now on the same box
    * front end is pointing to localhost
    * but localhost is being hit when rendered in browser, so won't work
    * point to ec2 ipv4 address?
    * setup new alb for backend?
* add authentication header to backend
    * auth handler
    * static uuid probably
    * depends on front end external config (above)

### infra
* same ec2 instance for frontend and backend [DONE]
    * see recipe-app README.md for server setup instructions
* alb for frontend [DONE]
* TLS certificate [DONE]
* postgres db [DONE]
* domain [DONE]
    * created A Record in Route 53 going from domain to alb
    * moved dns name servers from godaddy to Route 53
    