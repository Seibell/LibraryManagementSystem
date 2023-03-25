# LibraryManagementSystem

## Background
The West Coast community centre is planning to establish a community library to improve community 
engagement by providing free access to books and study areas. In order to streamline the lending 
process of books and to reduce the workload of the staff, the community management has decided 
to incorporate a new LMS. The LMS will consist of i) a core backend to be developed with a 
component-based architecture, and ii) a rich frontend to be developed with JSF and Prime Faces iii) a
book lending application to support the workflow of the library. During the initial phase of this project, 
you are tasked to develop a pilot system at the library to demonstrate the solution. In a typical 
scenario, when a new member walks into the library, he/she will be assisted by an admin staff at the 
lending counter. Initially, the staff will register him/her with the system using his/her national identity 
number (NRIC/FIN) upon which the member can lend books. 

##
System specification
* Login
* Logout
* Register Member
* Lend Book
* View Fine Amount
* Return Book

1. View All Books (Homepage)
	
Shows a module that lists out all the books in the database along with its attributes.
This module is incorporated on the homepage and lend/return book in order to allow staff to better identify which
book is being lent/returned.

2. View All Members

Shows a module that lists out all the members in the database along with its attributes.

3. Edit Member Profile

Shows a module that allows staff to edit a specific member given his/her identity number.
Data is updated in the database once "Update" is pressed.

4. Add Book

Creates a new book object in database when staff inputs title, isbn and author.

5. View Member Lending History

Shows the book lending history of a specific member.

6. View Book Lending History

Shows the member lending history of a specific book.
