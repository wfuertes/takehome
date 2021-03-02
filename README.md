# takehome: Survey API Code Challenge

### Delivering
1. I have changed the package structure a little in order to make it more domain-driven. 
   In addition, I am adding swagger for API documentation, and the flyway for database migration.

2. For the missing requirements I created a new API `/api/survey/*` where it is possible to fetch the survey's
   questions and store the user answers. (It is not well finished, however, it can give an idea of what should be done).

3. I didn't implement a service layer (I am rushing here). The main business logic is under `SurveyController` level. 
   I am adding some unit tests for it.
   
### Running it Local for test purposes
```
mvn clean spring-boot:run
```

### Try it out
Api Documentation: http://localhost:8080/swagger-ui/index.html

### Database
I am still using H2 Database that can be accessed via http://localhost:8080/h2-console.
However, It could be easily be changed for MySQL in a production environment. The SQL database used
for this project is questionable, a document database looks do sense. Due to do the deadline I have done some chooses for keeping keep the JpaRepository. 
I am paying the price for not doing specialized queries. Many times I am using multiple Repository do some the problem. (It makes the solution more complex, and less efficient).

### Comments and More
* **Security**: I didn't add any security layer (key API, OAuth, JWT). 
  Considering that this is BE code running in a VPC the security layer could be added at the API Layer (close to the Cloud Front). 
  [I think about SpringSecurity, but I didn't use it here today]

* **Scaling:** The site questions seem doesn't change much. We could add a caching layer for the site questions,
  for reading and writing it must have a MASTER Database with at least one REPLICA. 
  Considering the volume of requests to store Survey can increase due to a peak we could add a queue in front our database in order to reduce the pressure on it.
  For sure, I am considering this API app running in a cluster with load balancing.

### Matrix Question
* It could be done better I know. Here, I only created the concept of `Option` and then an `Answer` can have a `list of options`.
  Options can be selectable or just like a label. In that type of question, each question is a column of the matrix.
  
E.g.
```
{
        "questionId": 4,
        "question": "Please tell us a bit about yourself?",
        "questionType": "MATRIX",
        "answers": [
            {
                "answerId": 15,
                "answer": "Age/Gender",
                "options": [
                    {
                        "optionId": 1,
                        "label": "< 18",
                        "pickable": false
                    },
                    {
                        "optionId": 2,
                        "label": "18 to 35",
                        "pickable": false
                    },
                    {
                        "optionId": 3,
                        "label": "35 to 55",
                        "pickable": false
                    },
                    {
                        "optionId": 4,
                        "label": "> 55",
                        "pickable": false
                    }
                ]
            },
            {
                "answerId": 16,
                "answer": "Male",
                "options": [
                    {
                        "optionId": 5,
                        "pickable": true
                    },
                    {
                        "optionId": 6,
                        "pickable": true
                    },
                    {
                        "optionId": 7,
                        "pickable": true
                    },
                    {
                        "optionId": 8,
                        "pickable": true
                    }
                ]
            },
            {
                "answerId": 17,
                "answer": "Female",
                "options": [
                    {
                        "optionId": 9,
                        "pickable": true
                    },
                    {
                        "optionId": 10,
                        "pickable": true
                    },
                    {
                        "optionId": 11,
                        "pickable": true
                    },
                    {
                        "optionId": 12,
                        "pickable": true
                    }
                ]
            }
        ]
    }
```

### Going Forward
* Use question type in order to add validation according to the number of answers and responses;
* Add a service layer in order to reduce controller complexity;
* Improve test coverage.