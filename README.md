# Pay My Buddy (Simple Money Transfer)

## Installation

### Database (MySQL)

 - Install mysql server
 - Create database with name : `paymybuddy`
 - Configure Env Vars : 
    - `PMB_DB_USERNAME=____`
    - `PMB_DB_PASSWORD=____`
 - Run sql/create.sql

### Application (Spring boot)

 - `git clone https://github.com/Wardenfar/PayMuBuddy.git`
 - `cd PayMuBuddy`
 - `gradle build`
 - `gradle bootRun`

## Run Tests

### Unit tests

- `gradle build`
- `gradle test`

### Integration tests

- `gradle build`
- `gradle bootRunIt`
- `yarn`
- `yarn cypress run`