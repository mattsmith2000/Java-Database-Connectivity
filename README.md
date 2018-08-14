# Java Database Class work

These classes were built around a hypothetical Equipment Database, using a 3 layered architecture. 
  1. Database Layer- The database and classes that interface with it.
  2. Data Layer(DL)- Classes that mirror tables in the database so information can be loaded and altered.  
  3. Business Layer(BL)- An abstraction of data layer classes for simple "business" functions. 

- MySQLDatabase.java
  - Load Driver
  - Connect/Disconnect from Database
  - Execute prepared statements
  - Start, commit and rollback transactions

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
  
