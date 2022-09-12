# Dont-wreck-my-house-34: Project Plan

## Tasks 

### View Reservations for Host

  * Display all reservations for a host. 

    * The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list. 
    * If the host is not found, display a message. 
    * If the host has no reservations, display a message. 
    * Show all reservations for that host. 
    * Show useful information for each reservation: the guest, dates, totals, etc. 
    * Sort reservations in a meaningful way.

### Make a Reservation

* Books accommodations for a guest at a host.

   * The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list. 
   * The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list. 
   * Show all future reservations for that host so the administrator can choose available dates. 
   * Enter a start and end date for the reservation. 
   * Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday (Sunday, Monday, Tuesday, Wednesday, or Thursday) or a weekend (Friday or Saturday). If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies. 
   * On confirmation, save the reservation.
  
   #### Validation

   * Guest, host, and start and end dates are required. 
   * The guest and host must already exist in the "database". Guests and hosts cannot be created. 
   * The start date must come before the end date. 
   * The reservation may never overlap existing reservation dates. 
   * The start date must be in the future.

### Edit a Reservation

* Edits an existing reservation.

   * Find a reservation. 
   * Start and end date can be edited. No other data can be edited. 
   * Recalculate the total, display a summary, and ask the user to confirm.

  #### Validation

   * Guest, host, and start and end dates are required. 
   * The guest and host must already exist in the "database". Guests and hosts cannot be created. 
   * The start date must come before the end date. 
   * The reservation may never overlap existing reservation dates.

### Cancel a Reservation

* Cancel a future reservation.

   * Find a reservation. 
   * Only future reservations are shown. 
   * On success, display a message.

  #### Validation

   * You cannot cancel a reservation that's in the past.

## Schedule

* Day 1 (Monday) : Make plans, start coding 
* Days 2 - 3 (Tuesday, Wednesday): Get data layer and testing done 
* Days 4 - 5 (Thursday, Friday): Get domain layer and testing done 
* Day 6 (Friday): Get UI done 
* Days 7 - 8 (Saturday, Sunday): Fix problems and write everything extra

## Plan of Approach

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
      
### Data Layer
1. Set up Data Exception - 30 minutes to double-check details
2. Set up basic Host File Repository
    * Read files - 1 hour
    * Set up method for listing all hosts - 1 hour
    * Set up methods for returning host details
        * Return host email from file - 2 hours
        * Return host ID from file - 1 hour 
        * Return host's standard rate from file - 1 hour 
        * Return host's weekend rate from file - 1 hour 
3. Set up basic Host Repository - 30 minutes
4. Set up basic Guest File Repository
    * Read files - 1 hour
    * Set up method for listing all guests - 2 hours
    * Set up methods for returning guest identifiers 
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
    * Write method to update already-existing reservation (ONLY the times) - 2 hours
    * Set up methods to write to file within directory - 2 hours
      * Write the new user-input info to create new reservation
      * Write the new reservation to file
    * Set up method to delete reservation - 1 hour
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
   * Pull in host repo and methods - 5 minutes
   * Validate - 1.5 hours
     * Set up responses to different kinds of errors 
2. Set up Guest Service 
   * Pull in guest repo and methods - 15 minutes
   * Validate - 1.5 hours
     * Set up responses
3. Set up Reservation Service 
   * Pull in host repo and methods - 5 minutes
   * Pull in guest repo and methods - 5 minutes
   * Pull in reservation repo and methods - 5 minutes
   * Set up method to sort reservations in meaningful way for viewing - 1 hour
   * Validate - 4 hours
     * Set up responses to different kinds of errors 
     * Ensure the date must first be changed to the future before reservation can be changed 
     * Ensure the new reservation start date can only be made in the future 
     * Ensure guest, host, and dates are input 
     * Ensure there is no overlap in reservation dates 
   * Set up boolean for whether the reservation dates overlap - 2 hours
   * Set up boolean for ensuring all the info is entered - 1.5 hours
   * Set up boolean for verifying whether guest and host already exist - 1.5 hours
   * Set up boolean for whether reservation is in the past - 1.5 hours
   
#### Test Layer for Domain 
1. Set up Domain Tests - 4 hours
2. Set up tests to use immutable test data - 1 hour

### UI Layer 
1. Set up View
   * Set up methods for what is printed to the console - 2 hours 
   * Set up methods to return data (Strings, ints, etc.) from user input - 2 hours
2. Set up Controller
   * Set up method to read menu and write title - 30 minutes
   * Set up main menu - 30 minutes
     * 0: Exit
     * 1: View Reservations for Host
     * 2: Edit a Reservation
     * 3: Cancel a Reservation
   * Set up methods to run in menu - 4 hours
     * Method to view reservations 
     * Method to edit reservation 
     * Method to edit reservation 
     * Method to cancel a reservation 


