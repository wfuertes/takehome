# Getting Started

This is a sample project you can use to kick start your development or if you choose, 
you can build your own project using the framework of your choice.

## Definition
You can think of this proof of concept project as a something that was left by your predecessor, but now
you are taking ownership of it. You will want to get it ready for something mor than a production environment.
It works for some basic question administration use cases creating questions, reading questions but these 
are just some the basic administration.

We really need these questions to be served up in our custom JS Widget to show the questions to
customers.

## Missing Requirements
1) Enhance the question structure to be able to handle the matrix question as described in the Appendix.

2) Build an API to serve up the questions to users and store the results.  A widget located on a website will make a 
REST call which will include a user UUID, and a site UUID.  
    1) Question responses must be captured for each of the questions served from the API
    2) Assume a user will always see a unique question as long as one exists.  If a unique question does not exist you 
    are free to reset and start the list of questions a user has seen again over
    4) a USER UUID, is a unique value identifying the user that is generated on the page if one does not exist

## Deliverable
1) Reorganize and refactor the code to make it easier to navigate and maintain in the long term. 
    1) Important note: you can change any of the code as you desire, the code is here to help, if you don't like it 
    change it.
2) Enhance the code to satisfy the missing requirements, described in the "Missing Requirements" section.  
3) Pick one of the classes that exist or one of the ones you created and create a few test cases 
5) Include a README.md
    1) Give details on how to run your project and install the the DB if you have changed it
    2) Comment on any security considerations 
    3) Please add any notes and assumptions
    4) Include a small section on how you think this solution would scale and what you might want to think about to 
    scale it to the next level. 
6) Submission
    1) Do not include the instructions in your submission
    2) Use Git to manage your code, upload your code to a git repository and submit the link to your repository.


# Appendix

## POC Application Instructions
This is a spring boot application and can be run like any normal spring boot application. 

### Database
The application is currently using H2 DB for an in memory database, this is for testing purposes only and is not a production
ready solution.  The Database is initialized by using the Hibernate auto update, again this is not a 
production ready upgrade solution.

**NOTE:** If you find it easier to use another DB please feel free to change it out.

#### Access the Database
> localhost:8080/h2-console

Set the JDBC URL to *jdbc:h2:mem:testdb*, and press connect

#### Creating a Sample Trivia Question
Create a Site
>curl --header "Content-Type: application/json" \
  	 --request POST \
  	 --data '{"url": "www.bob.com"}' \
  	 http://localhost:8080/sites

Create the Trivia Question for the Site
>curl --header "Content-Type: application/json" \
  	 --request POST \
  	 --data '{"siteId":1, "question": "how many toes does a pig have?"}' \
  	 http://localhost:8080/questions

Create some responses for the question
>curl --header "Content-Type: application/json" \
  	 --request POST \
  	 --data '{"answer": "4 toes","isCorrectAnswer": true}' \
  	 http://localhost:8080/questions/2/answers
  	 
>curl --header "Content-Type: application/json" \
  	 --request POST \
  	 --data '{"answer": "3 toes","isCorrectAnswer": false}' \
  	 http://localhost:8080/questions/2/answers
  	 
>curl --header "Content-Type: application/json" \
 	 --request POST \
 	 --data '{"answer": "The do not have toes silly","isCorrectAnswer": false}' \
 	 http://localhost:8080/questions/2/answers
 	 
## Example Questions
Questions show some different types questions that exist

### Trivia Question
> Which team won the 2017 superbowl?

|||
| :---------- | :---------: |
| Falcons   | [ ] |
| Patriots  | [X] |

*Only one correct answer with two to four possible answers in this question type.*

### Poll Question
> What's your favorite car brand?

|||
| :---------- | :---------: |
| Nissan    | [ ] |
| Honda     | [ ] |
| Audi      | [X] |
| BMW       | [ ] |
*No correct answer with two to four possible answers in this question type.*

### CheckBox Question
> What colors do you like? 

|||
| :---------- | :---------: |
| Red           | [ ] |
| Blue          | [X] |
| Yellow        | [ ] |
| Green         | [ ] |
| Black         | [X] |
| Purple        | [ ] |
*Is an objective question with up to ten possible answers.  This style of question allows for multiple 
correct responses*

### Matrix Question
> Please tell us a bit about yourself? 

| Age/Gender        | Male | Female |
| :----------   | :---------: | :----------: |
| < 18          | [ ] |[ ] |
| 18 to 35      | [ ] |[ ] |
| 35 to 55      | [ ] |[ ] |
| \> 55          | [X] |[ ] |
*Is an objective question that shows options in a matrix. A visitor can only pick one of the available options, there is no right or wrong answer.*