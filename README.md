# *Redisearch RecipeService*

A Starter Kit Project for Redisearch 

## *Introduction*

The aim of this project is basically to show how to create,update,delete and query the documents on redisearch. Redisearch implements a search engine on top of Redis and provide these functionalities
-   Full-Text indexing of multiple fields in documents.
-   Incremental indexing without performance loss.
-   Document ranking (provided manually by the user at index time).
-   Complex boolean queries with AND, OR, NOT operators between sub-queries.
-   Optional query clauses.
-   Prefix based searches.
-   Field weights.
-   Auto-complete suggestions (with fuzzy prefix suggestions).
-   Stemming based query expansion 

## *Getting Started*

### *Prerequisities*

 - [ ] Java 8+
 - [ ] Spring Framework 5+
 - [ ] Spring Boot 2.2.6+
 - [ ] Docker 19.3+
 - [ ] Redisearch 1.6.13
 - [ ] Maven jib Plugin for dockerize the services
 

## *Running the Project*

recipe-service

    mvn jib:dockerBuild
    
running  Redisearch and Recipe Service @Docker environment  

    docker-compose up

Before running the docker-compose up command inside of the docker-compose.yml file the volume mount mapping should be done. This volume will be used to load xml recipes to service in advance. *//c/data/upload:/data/upload* the left side of ":" It points folder under C->data->upload; the right side of ":" point the folder inside of the container itself.  It can be changed according to OS or indivudual choice.

## *Modeling the Entities and Search Fields*

Redisearch can be use a key-value noSql db. Therefore;

For recipe entity, the whole recipe entity is not mapped to redisearch document. Instead of this approach just the searchable fields are kept as seperate keys which belongs to Head entity and the whole recipe object is kept in body field as text. Within this approach recipes can be filtered according to title,yield and categories. Categories are kepts in a "tag field" as a coma seperated string .

For category entity,  there is just one string field. So, it is kept as text and id. Therefore it is prevented the duplication. 

## *Load Xml Data*

After running the recipe service, just drag and drop the xml file into the folder which is open for mount. With Spring integration, it gets a "file added" event. this event basically sends the filename to system then according to transformation xml file being parsed and save into the recipe-db.  

## *Api*

**REST API**

 - [ ] /recipes endpoint to manage Recipe data. 
 - POST /recipes to save a new recipe
 - GET /recipes/{recipeId} to get one specific recipe by Id
 - GET /recipes lists all recipes or query according to filter or keyword
 
**Filtering by a part of category , sorting by title (asc/desc),paging applicable**

 - [ ] /categories endpoint to get all categories 
 **  Sorting by description (asc/desc),paging applicable**
 
