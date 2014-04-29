Lab4 
Brandan haertel
Erick House

1.  To our best knowledge everything is working as intended.

2.  we have 3 hash tables:
    a.  ItemFactory: hashes a Recordables getType() Char and subtracts ‘A’  to make type A = 0 to keep the array size minimal. (Char - ‘A’). ItemFactory returns a new instance of Fiction (F), Patron (A), Periodical (P), or Youth (Y). if the hash is not A,F,P, or Y ItemFactory will return an instance of ErrorCommand that is NULL, causing the calling command to abort.
    b.  CommandFactory hashes a Commands getType() Char and subtracts ‘A’ to make type A = 0 to keep the array size minimal. (Char - ‘A’). CommandFactory returns a new instance of BuildCommand(F,P,Y,#), CheckinCommand (R), CheckoutCommand (C), HistoryCommand (H), PrintCommand (D). if the hash is not C,F,F,H,P, Y OR # CommandFactory will return an instance of ErrorCommand that is NULL, thats does nothing when takeCareofBuisness() is called on it.
    c.  Library has a hash table called forest. It is comprised of binary trees. For each item type library can take, it has a pointer to a root node of a tree. The hash method uses the same techniques as described above where the hash is given a char and converts it to an int. The int hashes to the tree the object will be stored in.

3.  Process for reading and building items
    -   The infile must be built in main
    -   system.addFile(infile) takes in the whole file
    -   system then parses the file, line by line and passes it to command factory as a single line
    -   the first character in the line hashes to the build command. If given A(patron), Y(youth), P(periodical), F(fiction) the build command will call item factory and build an instance of that object
    -   build command calls set data and member variables in the concrete classe’s member variables are given values
    -   the new object is added to library

4.  Process for reading and executing commands
    -   the infile must be bult in main
    -   system.addFile(infile) takes in the whole file.
    -   system the parses the file, line by line and passes it to CommandFactory as a single line.
    -   the first character either hashes to CheckinCommand (R), CheckoutCommand (C), HistoryCommand (H),  PrintCommand (D), or ErrorCommand. the CommandFactory will then call takeCareofBusiness(Library, string)
    -   each command then does actions on the Library, depending on what Command class was called.

5.
    a.  In CheckoutCommand and CheckinCommand we hardcode the check for hardcopy (H). If the Char is H then we continue, but if the Char != ‘H’ then we print an error and abort the CheckinCommand/CheckoutCommand.
    b.  We don’t believe these are ‘dirty little secrets’ with this program, but the following few items could possibly be considered scandalous (dependent on interpretation)
        -   hashing a number in ItemFactory assumes a new Patron. 
        -   we treat almost all commands as real commands, but they are set to ErrorCommand that doesn’t do anything to handle bad data.

6.  We are proud of:
    -   Brandan is proud of: 
        -   The creation and use of ErrorCommand, it handles most of our errors and really reduced the amount of error checking that was needed throughout the program.
        -   Also working with Erick was joy, we collabarated very well and they entire project went smoothly.
        -   Some of out nameing conventions...
    -   Erick is proud of: How smoothly the project went. We started on it early and there wasn’t much pressure to finish everything at the end. I’m shocked how much the pair programming helped in defeating bugs. In the testing phase I a difficult time finding something to break the program. Unlike other project very little time was needed to find and respond to bugs. 
