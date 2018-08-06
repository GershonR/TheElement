## Coding Standards | Fifth Element

#### General

* Single blank lines to seperate code.
* Comment things that aren't obvious or intuitive
* Comment blocks of code if the code is not obvious.
* Use reasonable variable names, camelCase.

#### Code
* Try to follow the following:

        class Something {
                 //Instance variables
                 //Static variables
                 //Class-level constants
                 //Constructors
                 //static and instance methods
        }

* Use single tabs (converted to 4 spaces) to indicate depth lines in methods.
* Use consistent indentation to clarify control structures (e.g., loops and if constructs). Levels of indentation should clearly indicate the depth of nesting. For example:

        class MyClass {
            public static String someMethod() {
				String returnString;
				
                statement_that_is-related_to_loop;
                while (...) {
                    statement;
                }
                statement;
				
				return returnString;
            }
			
            public static int anotherMethod() {
                ...
            }
        }

* Use good judgement in method length and break up methods when you feel the functionality may be useful elsewhere, or you have used the same code twice.
* Make sure code is readable. 
* Default to private on static variables
