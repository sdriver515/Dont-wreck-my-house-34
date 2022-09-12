# Dont-wreck-my-house-34: Project Plan

## Tasks 

## Schedule

## Approach

### Setup Start
1. Download files into project - 15 minutes
2. Import files into project - 15 minutes
3. Create IntelliJ organization - 15 minutes
    * Data layer
    * Domain layer
    * Model layer
    * UI layer
    * Test layer
    * App
### Code Start
1. Set up Pom - 10 minutes
2. Set up Annotations - 30 minutes
### Code Development
#### Models
1. Set up models with a basic idea of what might be in them
    * Host - 30 minutes
        * Fields
    * Guest - 30 minutes
        * Fields
    * Reservations - 30 minutes
        * Fields
#### Data Layer
1. Set up Data Exception - 30 minutes to double-check details
2. Set up basic Host File Repository
    * Read files - 1 hour
    * Set up methods for finding host file details 
        * Return host email from file - 2 hours
        * Return host ID from file - 1 hour 
        * Return host's standard rate from file - 1 hour 
        * Return host's weekend rate from file - 1 hour 
3. Set up basic Host Repository - 30 minutes
4. Set up basic Guest File Repository
    * Read files - 1 hour
    * Set up methods for finding guest details 
        * Return guest's email from file - 1 hour
        * Return guest's ID from file - 1 hour
5. Set up basic Guest Repository - 30 minutes
6. Set up basic Reservation File Repository
    * Set up methods to return file associated with host ID (the file name is the host's ID) - 1 hour
    * Set up methods to read host-ID-reservation file - 2 hours 
    * Set up methods to clean data - 1 hour 
    * Set up methods to return reservation details from host-ID-reservation files
      * Return free dates - 3 hours
      * Return occupied dates - 2 hours
      * Return guest ID from associated dates - 2 hours
    * Set up money-related methods 
      * Return the already-calculated cost by reservation-ID - 1 hour 
      * Return a calculated cost of stay by computing the time and host's rates - 4 hours 
    * Set up method to generate unique reservation ID for newly created reservation - 1 hour
    * Write method to update already-existing reservation - 2 hours
    * Set up methods to write to file within directory - 2 hours
      * Write the new user-input info to create new reservation
      * Write the new reservation to file
7. Set up basic Reservation Repository - 30 minutes
##### Test Layer for Data
1. Set up Data Tests
    * Host File Repository Test - 2 hours
    * Host Repository Double - 1 hour
    * Guest File Repository Test - 2 hours
    * Guest Repository Double - 1 hour
    * Reservation File Repository Test - 2 hours
    * Reservation Repository Double - 1 hour
2. Set up tests to use immutable test data - 1 hour
### Domain Layer 
1. Set up Host Service 
   * Pull in host repo and methods 
   * Validate 
     * Set up responses 
2. Set up Guest Service 
   * Pull in guest repo and methods 
   * Validate 
     * Set up responses
3. Set up Reservation Service 
   * Pull in host repo and methods
   * Pull in guest repo and methods
   * Pull in reservation repo and methods
   * Validate 
     * Set up responses 
#### Test Layer for Domain 


