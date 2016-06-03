Jammit

Created by: Nabil Fadili & Riley Gratzer
Android Min SDK: 15

Features Implemented:
  -Registration, 
  -Log In (with User Authentication and Shared Preferences), 
  -View Profile, 
  -Edit Profile, 
  -Search Musicians (SQLite functionality);,
  -Post Band Opening,
  -Search Band Openings,
  -Post Event,
  -Search Events (with email functionality),
  -Log Out
  
100% of Use Cases Implemented (From Proposal):
  -Use Case 1: Register an Account (required), 
  -Use Case 2: Log into Account (required), 
  -Use Case 3: Edit Account, 
  -Use Case 4: Find Musicians 
  -Use Case 5: Post Band Opening
  -Use Case 6: Find Band Openings
  -Use Case 7: Post Event
  -Use Case 8: Find Events
  
Data Storage:
  -Shared preferences data storage implemented. If a user is logged in and exits the app to do other things, when returning
  to the app the user will still be logged in.
  -SQLite data storage implemented. When a user searches for musicians, the results are saved via a local SQLite database so
  that if the user loses network connectivity, the results will still be viewable.
  
Web Services:
  -All users, profiles, band openings, and events entered in the app are inserted into a remote database on the insttech
  server. All searches for profiles, band openings, and events done on the app will query this database and return results to
  the app to format into an easy to use list. User sign-in authentication is also checked via this type of database query.
  
Content Sharing:
  -When a user views a musician's profile, a band opening post, or a posted event they will have the option to email that
  poster or musician. The button displayed will take the user to the phone's native email app but will autofill the recipient
  information with the correct email address. NOTE: the 'from' will be set to the email address logged in on the phone, not
  the Jammit app.
  
Sign-in/Registration:
  -Both implemented. Authentication is checked via database query. Registration will insert a new UserAccount into the
  database.
  
Images:
  -Custom logo is displayed for the app in each device size. Logo is also displayed for the main menu. Profile pictures were
  not implemented, but a default profile picture is provided to each user.
  
Testing:
  -UserAccount model class is tested thoroughly. Robotium tests cover the login functionality and edit profile functionality.
  
Meeting Notes:
  -https://docs.google.com/document/d/1CApVkf-rvGZFO6MRsX91A49LrQMU53CKJfwjO2K1C98/edit
  
Notes:
  -Robotium tests will only pass if the app is logged out completely. This is because it starts testing from this state.
  
