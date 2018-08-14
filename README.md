# Java Database Class work
These classes were built around a hypothetical Equipment Database, using layers of abstraction for data security.

- MySQLDatabase.java
  - Load Driver
  - Connect/Disconnect from Database
  - Execute prepared statements
  - Start, end and rollback transactions

- Equipment.java
  - Sends prepared statements to MySQLDatabase object
  - Select equipment information 
  - Update equipment information
  - Insert new equipment information
  - Remove equipment information

- DLUser.java
  - Sends prepared statements to MYSQLDatabase object
  - Checks if input login information exists in database and retrieves access level

- DLException.java
  - Exception class that catches all errors, preventing the user from seeing any thrown exceptions.
  - Logs exceptions on ErrorLog.txt
  
