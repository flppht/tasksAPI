## TASKS REST API

1. mvn clean install
2. navigate to TasksApplication.java class and run it with play button in IntelliJ
3. use Postman to call API on http://localhost:8080/tasks
4. on http://localhost:8080/h2-console use jdbc url: "jdbc:h2:file:./data/tasksDb", username: "sa", password: "" to log in and check the data
5. when app is started, you should first call the /config/statusNotification endpoint which you can find in Postman collection that is in ./postman/TasksCollection.postman_collection
6. after notification configuration is set, for all tasks that are overdue (due date passed) and status is the status you set, you will get emails (reminders) every 1min - just for testing
7. all other endpoints (CRUD) are also in the Postman collection

also sorting for get all tasks endpoint is optional, can be done without it and it will be default sorted by id

did not have time to implement other functionalities lik to make custom exception class in order to better handle exceptions and sending reminders frequently by higher priority